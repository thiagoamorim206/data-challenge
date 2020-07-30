#!/bin/bash

output=$(gsutil cp ../datasets/movies.csv gs://iccde-datalake)
echo $output
