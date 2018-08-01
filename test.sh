#!/bin/bash
 
# to call the service
# ./test.sh http://localhost:8080/numbers/available 100
 
initial_time=$(date +%s)
 
for ((i=1; i<=$2; i++))
do
  curl -s $1 && printf "\ndone $i\n" &
done
 
wait
 
final_time=$(date +%s)
time_elapsed=$(($final_time-$initial_time))
 
echo $2 "requests processed in" $time_elapsed "s"