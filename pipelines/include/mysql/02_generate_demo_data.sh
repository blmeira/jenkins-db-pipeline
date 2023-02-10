#!/bin/bash

cp /tmp/generate_demo_data.template /tmp/generate_demo_data.sql

mysql --user="root" --password="$MYSQL_ROOT_PASSWORD" < /tmp/generate_demo_data.sql