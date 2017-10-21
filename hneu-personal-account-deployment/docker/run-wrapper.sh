#!/bin/sh

until $(nc -zv storage 3306);do
  echo "The storage is unavailable"
  sleep 3
done

java -Djava.security.egd=file:/dev/./urandom $1 -jar /app.jar