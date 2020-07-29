provider "google" {
  credentials = file(var.credentials)
  project = var.project
  region = var.region
}

resource "google_storage_bucket" "dataflow" {
  name = var.bucket-iccde-dataflow
}

resource "google_storage_bucket" "datalake" {
  name = var.bucket-iccde-datalake
}

resource "google_storage_bucket" "analytics" {
  name = var.bucket-iccde-analytics
}

resource "google_bigquery_dataset" "dataset" {
  dataset_id = var.bigquery-dataset
}

resource "google_bigquery_table" "default" {
  dataset_id = google_bigquery_dataset.dataset.dataset_id
  table_id = var.bigquery-table
  schema = <<EOF
[
  {
    "name": "NAME",
    "type": "STRING",
    "mode": "NULLABLE",
    "description": "Movie genres"
  },
  {
    "name": "COUNT",
    "type": "INTEGER",
    "mode": "NULLABLE",
    "description": "Counting genres of films"
  }
]
EOF

}