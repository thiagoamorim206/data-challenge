package com.data.dataeng.options;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.Validation;
import org.apache.beam.sdk.options.ValueProvider;

public interface IngestionOptions extends PipelineOptions {
    @Description("Path of movies file")
    @Validation.Required
    ValueProvider<String> getMoviesInputPath();
    void setMoviesInputPath(ValueProvider<String> value);

    @Description("Dataset/Table of genre count")
    @Validation.Required
    ValueProvider<String> getGenresCountOutputTable();
    void setGenresCountOutputTable(ValueProvider<String> value);

    //TODO: ADD ANOTHER PATHS FOR BONUS CASES (getRatingsInputPath/getRatingsCountOutputTable)
}