#!/bin/bash

sed "s/<PASSWORD>/$ORACLE_PWD/g" /tmp/create_developer.template > /tmp/create_developer.sql

sqlplus /tmp/create_developer.sql