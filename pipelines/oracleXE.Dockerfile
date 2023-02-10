
ARG DB_IMAGE=mysql
ARG DB_TAG=latest

FROM ${DB_IMAGE}:${DB_TAG}

ARG DB_ENGINE=mysql
COPY include/${DB_ENGINE}/*.sh /docker-entrypoint-initdb.d/
COPY include/${DB_ENGINE}/*.template /tmp/

ENTRYPOINT ["docker-entrypoint.sh"]

EXPOSE 3306 33060

CMD ["mysqld"]
