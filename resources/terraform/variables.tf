variable "project" {
  type = string
  description = "Project ID."
  default = "data-challenge-2020"
}

variable "region" {
  type = string
  description = "Regiao no GCP onde nossos recursos estarao."
  default = "us-east-1"
}

variable "credentials" {
  type = string
  description = "account key"
  default = "../account-key/data-challenge-2020-movie-account.json"
}

