# Docker Guide

## What is Docker? 🐳

Docker is a platform that allows developers to package applications and their dependencies into a standardized unit called a **container**. This solves the classic problem of "It works on my machine but not on the server!" by ensuring that applications run consistently across different environments.

### Why Use Docker? 🤔

Before Docker, developers faced issues like:

- **Different environments**: An app works on one system but fails on another due to dependency conflicts.
- **Manual setup**: Installing software dependencies manually on every machine was time-consuming.
- **Resource inefficiency**: Running multiple applications on a server often led to conflicts and resource management issues.

Docker solves these issues by containerizing applications, ensuring they run the same way everywhere!

---

## Docker Concepts

### 1️⃣ Docker Image

A **Docker Image** is a lightweight, standalone package that contains everything needed to run an application (code, runtime, libraries, dependencies...). It acts as a **blueprint** for creating containers.

#### Commands:

```sh
docker pull imageName          # Pull an image from Docker Hub
docker pull imageName:version  # Pull a specific version of an image
docker images                  # List all downloaded images
docker rmi image_id            # Remove an image (only if no container is using it)
```

To remove an image in use, remove the container first:

```sh
docker rm container_id  # Remove the container
docker rmi image_id     # Now remove the image
```

---

### 2️⃣ Docker Container

A **Container** is a running instance of a Docker Image. Containers are isolated environments that run applications.

#### Commands:

```sh
docker run imageName                    # Create and start a container
docker run -d imageName                 # Run container in detached mode (background)
docker run --name containerName imageName # Assign a name to the container
docker ps                                # List running containers
docker ps -a                             # List all containers (including stopped ones)
docker stop container_id                 # Stop a running container
docker start container_id                 # Start a stopped container
docker rm container_id                   # Remove a container (must be stopped first)
```

🔹 Example:

```sh
docker run --name haneen-postgres -e POSTGRES_PASSWORD=password postgres
```

Creates a PostgreSQL container named `haneen-postgres`.

---

### 3️⃣ Docker Logs

Docker allows you to check container logs for debugging purposes.

#### Commands:

```sh
docker logs container_name        # View logs
docker logs -f container_name     # View logs in real-time (follow mode)
```

Follow Mode (`-f`): Keeps the logs updating in real-time, similar to watching a live feed.

---

### 4️⃣ Docker Exec - Running Commands Inside Containers

You may need to execute commands inside a running container.

#### Commands:

```sh
docker exec -it container_name bash   # Open a terminal inside the container
docker exec -it container_name database_client -U username -p # Connect to database inside a container
```

🔹 Example:

```sh
docker exec -it postgres-sql-anonymous psql -U postgres
```


## Docker Volumes - Solving Data Persistence Issues

### ❌ Problem:

By default, when a container is deleted, all its data is lost. This is a major issue for databases or any application that needs persistent data.

### Solution: **Docker Volumes**

A **Volume** is a way to persist data outside of a container, ensuring data is not lost when a container is removed or restarted.

### Types of Volumes:

1. **Named Volumes** – Managed by Docker, stored in `/var/lib/docker/volumes/`
2. **Anonymous Volumes** – Created when you don't specify a name
3. **Bind Mounts** – Maps a host directory to a container directory

#### Commands:

```sh
docker volume create volume_name  # Create a volume
docker volume ls                  # List volumes
docker run --name postgres-sql -e POSTGRES_PASSWORD=password -v volume_name:/var/lib/postgresql/data postgres
```

🔹 Example:

```sh
docker run --name postgres-sql -e POSTGRES_PASSWORD=password -v /users/HANEEN/docker/volumes/postgres/data:/var/lib/postgres/data postgres
```


## Building a Custom Docker Image for a Project

### ❌ Problem:
You might need to add dependencies, set up configurations, or include your own code. Doing this manually every time is tiring and can lead to mistakes.
So,sometimes, you create a custom image instead of using existing ones.
### Solution: Dockerfile

A **Dockerfile** is a script that defines how to build an image.

For a complete example of a `Dockerfile ` file, check out this [Dockerfile](demo/Dockerfile)

#### Commands:

```sh
# Build an image from a Dockerfile
docker build -t my-app .

# Run a container using the built image
docker run my-app
```

> **Example:** You can now share `my-app` with others or push it to Docker Hub!
---

## Docker Networking - How Containers Communicate

### ❌ Problem:

By default, containers are isolated from each other. If multiple containers,(like a web app and a database), need to communicate,
they won’t be able to unless they are properly connected. 

### Solution: Docker Networks

Docker provides different types of networks to control container communication.

### Types of Networks:

1. **Bridge (Default)** - Containers in the same bridge network can communicate using container names.
2. **Host** - Removes network isolation and uses the host machine's network.
3. **Overlay** - Used in **Docker Swarm** for multi-host networking.
4. **None** - Completely isolates a container with no network access.

#### Commands:

```sh
docker network ls                 # List all networks
docker network create my_network  # Create a custom network
docker run --network my_network --name my_container imageName  # Run a container in a network
docker network connect my_network containerName  # Connect an existing container to a network
```

🔹 Example:

```sh
docker network create my_app_network
docker run --name my-db --network my_app_network -e POSTGRES_PASSWORD=password postgres
docker run --name my-webapp --network my_app_network my-webapp-image
```

Now, `my-webapp` can communicate with `my-db` using `my-db` as the hostname.

---

## Docker Compose - Managing Multi-Container Applications

### ❌ Problem:

When working with multiple containers like a backend, database, and frontend, starting and linking them manually is complex and time-consuming.
You need to start each container separately and manually configure their connections, which can be tedious and error-prone.

### Solution: Docker Compose

Docker Compose allows you to define multiple containers in a single **docker-compose.yml** file.

For a complete example of a `docker-compose.yml` file, check out this [docker-compose.yml](demo/docker-compose.yml)

#### Commands:

```sh
docker-compose up -d  # Start all services defined in docker-compose.yml
docker-compose down   # Stop all services
```
> **Note:** `depends_on` ensures the database starts before the backend.







