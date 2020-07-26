import apache_beam as beam
from apache_beam.options.pipeline_options import PipelineOptions


class IngestionOptions(PipelineOptions):

    @classmethod
    def _add_argparse_args(cls, parser):
        parser.add_argument('--moviesInputPath', help='Input for the pipeline')
        parser.add_argument('--genresCountOutputTable', help='Output for the pipeline')
        # TODO: ADD ANOTHER PATHS FOR BONUS CASES (getRatingsInputPath/getRatingsCountOutputTable)


options = PipelineOptions()
ingestion_options = options.view_as(IngestionOptions)

p = beam.Pipeline(options=options)

# Read movies.csv from storage
# Get movie genres string using regex r"[A-Za-z|-]+$|no genres listed"
# Split movies genres by delimiter
# Count genres
# Parse to JSON
# Parse to BigQuery rows
# Write JSON to Google Cloud Storage
# Write rows to BigQuery

result = p.run()
result.wait_until_finish()
