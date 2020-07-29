variable "project" {
  type = string
  description = "Project ID."
  default = "data-challenge-2020"
}

variable "region" {
  type = string
  description = "Region GCP."
  default = "us-east-1"
}

variable "credentials" {
  type = string
  description = "Account Key."
  default = "../account-key/data-challenge-2020-movie-account.json"
}

variable "bucket-iccde-dataflow" {
  type = string
  description = "Bucket iccde-dataflow."
  default = "iccde-dataflow"
}

variable "bucket-iccde-analytics" {
  type = string
  description = "Bucket iccde-analytics."
  default = "iccde-analytics"
}

variable "bucket-iccde-datalake" {
  type = string
  description = "Bucket iccde-datalake."
  default = "iccde-datalake"
}

variable "bigquery-dataset" {
  type = string
  description = "BigQuery Dataset iccde."
  default = "iccde"
}

variable "bigquery-table" {
  type = string
  description = "BigQuery Table genre_count."
  default = "genre_count"
}