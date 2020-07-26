package com.data.dataeng.pipelines;

import com.data.dataeng.options.IngestionOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

public class InterviewExercise {

    public static void main(String[] args) {
        PipelineOptionsFactory.register(IngestionOptions.class);
        IngestionOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(IngestionOptions.class);

        runIngestion(options);
    }

    private static void runIngestion(IngestionOptions options) {
        Pipeline p = Pipeline.create(options);

        /* Read movies.csv from storage
         Get movie genres string using regex "([A-Za-z|-]+$)|(no genres listed)"
         Split movies genres by delimiter
         Count genres
         Parse to JSON
         Parse to BigQuery rows
         Write JSON to Google Cloud Storage
         Write rows to BigQuery (WriteDiposition.WRITE_TRUNCATE e CreateDisposition.CREATE_NEVER)*/

        p.run().waitUntilFinish();
    }
}
