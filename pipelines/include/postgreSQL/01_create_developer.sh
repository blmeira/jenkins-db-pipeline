#!/bin/bash

sed "s/<PASSWORD>/$POSTGRES_PASSWORD/g" /tmp/create_developer.template > /tmp/create_developer.sql

psql -U "postgres" -f /tmp/create_developer.sql
