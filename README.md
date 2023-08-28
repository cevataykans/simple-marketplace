## Installation

- You need to have a Docker Container environment running [Docker](https://docs.docker.com/engine/install/).
- Docker Compose required for local deployment [Docker Compose](https://docs.docker.com/compose/install/).
- Makefile required for simplified command execution.
    - Makefile is supported by macOS & Linux by default. For Windows, please check the installation suggestions.
- You need JDK 17.
- App developed with Micronaut framework & Kotlin. All dependencies are automatically handled by Maven & Docker.

---

## How to Run (CLI)

Change working directory to the project root.

* For running tests, use:

```bash
make test
```

* For deployment, use:

```bash
make deploy
```

* For shutting down the deployed app, use:

```bash
make clean
```

> Makefile, Docker file, Docker Compose file can be edited for additional customization.