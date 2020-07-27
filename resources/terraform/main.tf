provider "google" {
  credentials = file(var.credentials)
  project = var.project
  region = var.region
}

resource "google_storage_bucket" "dataflow" {
  name = "iccde-dataflow"
}

resource "google_storage_bucket" "datalake" {
  name = "iccde-datalake"
}

resource "google_storage_bucket" "analytics" {
  name = "iccde-analytics"
}

resource "google_bigquery_dataset" "dataset" {
  dataset_id = "iccde"
}

resource "google_bigquery_table" "default" {
  dataset_id = google_bigquery_dataset.dataset.dataset_id
  table_id = "genre_count"
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