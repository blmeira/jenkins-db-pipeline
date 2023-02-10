# Welcome

## Overview

This archive contains files for our basic Jenkins pipeline to automatically create MySQL database instance - running on Docker, so our developers can have their very own environment to use.

This archive contains all the files being used by the current version of the pipeline.

```
|-- Dockerfile
|-- build-dev-environment.groovy
`-- include
    `-- create_developer.template
```

## Preparation

- Please upload your solution to GitHub into a public repository and share the URL in reply e-mail

## Expected behaviour

DONE - Developers can trigger the pipeline with parameters (Environment name, MySQL password and MySQL port)
DONE - A docker image built from latest MySQL image
DONE - Spin up a container from the image built above, exposing the requested port on the Docker host
DONE - Prepare the environment by creating an account for the developer (username: developer, password: based on input parameter)

## Issues

- The pipeline fails randomly in Jenkins
- If it works, developers are still not able to login into the running MySQL container because of an unknown issue. Please fix these issue before moving to the next part.

## Further improvement requests

DONE - Pipeline should fail if the MySQL port parameter is not a valid number within port range
DONE - Developers would also need a table called "departments" under the "DEVAPP" database with the following columns and with some demo data populated
    - DEPT - number 4 digit
    - DEPT_NAME - varchar 250
- Please share any observation / recommendation you might have on this process

## Optional
- Extend the pipeline with ability to switch database engine and implement the same scope using either:
DONE    - [OracleXE](https://container-registry.oracle.com/ords/f?p=113:4:3559407972469:::4:P4_REPOSITORY,AI_REPOSITORY,AI_REPOSITORY_NAME,P4_REPOSITORY_NAME,P4_EULA_ID,P4_BUSINESS_AREA_ID:803,803,Oracle%20Database%20Express%20Edition,Oracle%20Database%20Express%20Edition,1,0&cs=3DRUVeYjFotraARk1_SIQT-gpXHdclgNeRODkR0y5bUs8pMZHRZgRESapOWM2F4DJVgxuFhP_eLjQZFewWuqYRw)
    OR
DONE    - [PostgreSQL](https://hub.docker.com/_/postgres/)

## Notes
- The data generation continues to follow the template format, even though it is unnecessary, because the additional abstraction layer will permit easier future improvements (see [recomendations](#Recomendations))
- Oracle flow not complete - scripts need to be rewritten to sqlplus

## Recomendations
- Make the data generation variable (i.e. let the user choose how many rows to insert)
- Add variable registries to dockerfile
- Allow user to choose image tag
- Put cross script info in environment variables (like database name)
- Reduce switch cases in pipeline
