output=$(gcloud auth activate-service-account --key-file ../account-key/data-challenge-2020-movie-account.json)
echo $output

output=$(gsutil cp ../datasets/movies.csv gs://iccde-datalake)
echo $output
