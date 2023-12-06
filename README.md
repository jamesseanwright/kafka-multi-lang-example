# Kafka Multi-Language Example

Example Spring Boot and Python integrations with shared Kafka topics.



## Running the project locally

To build and run the service, you'll need to install:

* [Docker](https://www.docker.com/)
* [Pack CLI](https://buildpacks.io/docs/tools/pack/)

One these are installed, run `make build` to compile the various service containers. You can then run `docker compose up` to run these producers & consumers alongside a Kafka container within a self-contained Docker network.

The Compose config also includes [AKHQ](https://github.com/tchiotludo/akhq), which can be accessed at [http://localhost:8080](http://localhost:8080).

## Local development

If you plan to make changes to the codebase, it's recommended to install:

* JDK 17
* Python 3

Running `make setup_dev_env` will install the required Maven and pip dependencies, required for autocompletion and any pertinent dev tooling.
