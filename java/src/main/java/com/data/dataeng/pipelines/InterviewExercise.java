package com.data.dataeng.pipelines;

import com.data.dataeng.enums.GenreCount;
import com.data.dataeng.json.Genre;
import com.data.dataeng.options.IngestionOptions;
import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Regex;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InterviewExercise {

    public static void main(String[] args) {
        PipelineOptionsFactory.register(IngestionOptions.class);
        IngestionOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(IngestionOptions.class);
        runIngestion(options);
    }

    private static void runIngestion(IngestionOptions options) {
        Pipeline p = Pipeline.create(options);

        final PCollection<KV<String, Long>> genresCount = p.apply(TextIO.read().from("gs://iccde-datalake/movies.csv"))
                .apply(Regex.find("([A-Za-z|-]+$)|(no genres listed)"))
                .apply(Regex.split("[=|]"))
                .apply(Count.perElement());

        // Parse to Json
        final PCollection<String> json = genresCount.apply(ParDo.of(new DoFn<KV<String, Long>, String>() {
            @ProcessElement
            public void processElement(ProcessContext context) {
                KV<String, Long> element = context.element();
                try {
                    context.output(new ObjectMapper().writeValueAsString(new Genre(element.getKey(), element.getValue())));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }));

        //Write JSON to Google Cloud Storage
        json.apply(TextIO.write().to("gs://iccde-analytics/genres_count/movies.json"));

        // Parse to BigQuery row
        final PCollection<TableRow> bq = genresCount.apply(ParDo.of(new DoFn<KV<String, Long>, TableRow>() {
            @ProcessElement
            public void processElement(ProcessContext context) {
                KV<String, Long> element = context.element();
                TableRow tableRow = new TableRow();
                tableRow.set(GenreCount.NAME.name(), element.getKey());
                tableRow.set(GenreCount.COUNT.name(), element.getValue());
                context.output(tableRow);
            }
        }));

        // Write rows to BigQuery (WriteDiposition.WRITE_TRUNCATE e CreateDisposition.CREATE_NEVER)
        List<TableFieldSchema> fields = new ArrayList<>();
        fields.add(new TableFieldSchema().setName(GenreCount.NAME.name()).setType("STRING"));
        fields.add(new TableFieldSchema().setName(GenreCount.COUNT.name()).setType("INTEGER"));
        TableSchema schema = new TableSchema().setFields(fields);

        bq.apply(BigQueryIO.writeTableRows()
                .to("data-challenge-2020:iccde.genre_count")
                .withSchema(schema)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE)
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER));

        p.run().waitUntilFinish();

    }
}
