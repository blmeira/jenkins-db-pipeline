#!/bin/bash

sed "s/<PASSWORD>/$MYSQL_ROOT_PASSWORD/g" /tmp/create_developer.template > /tmp/create_developer.sql

mysql --user="root" --password="$MYSQL_ROOT_PASSWORD" < /tmp/create_developer.sql