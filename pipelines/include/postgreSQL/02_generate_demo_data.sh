#!/bin/bash

cp /tmp/generate_demo_data.template /tmp/generate_demo_data.sql

psql -U "postgres" -d devapp -f /tmp/generate_demo_data.sql