Here’s a step-by-step guide to containerize your Spring Boot–based scientific calculator, build and run it locally with Docker, and publish it to Docker Hub so others can pull and run it.

---

## 1. Write the Dockerfile

Create a file named `Dockerfile` in the root of your project (next to `pom.xml`):

```dockerfile
# Use a lightweight JDK base image
FROM eclipse-temurin:17-jdk-jammy

# Metadata
LABEL maintainer="Your Name <you@example.com>"
LABEL version="1.0"
LABEL description="Scientific Calculator Spring Boot Application"

# Expose the port your app runs on
EXPOSE 8080

# Copy the built jar into the container
ARG JAR_FILE=target/scientific-calculator-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Run the jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

> **Notes:**
>
> * We’re using the Eclipse Temurin JDK 17 image; adjust if you’re on a different Java version.
> * Make sure your Spring Boot build produces a fat jar at `target/scientific-calculator-0.0.1-SNAPSHOT.jar`.

---

## 2. Build the Docker Image

1. **Build your Spring Boot jar**

   ```bash
   mvn clean package
   ```
2. **Build the Docker image**
   From your project root (where `Dockerfile` lives):

   ```bash
   docker build -t your-dockerhub-username/scientific-calculator:1.0 .
   ```

   * `-t` tags the image as `your-dockerhub-username/scientific-calculator:1.0`
   * The final `.` tells Docker to use the current directory for context.

---

## 3. Run a Container Locally

```bash
docker run -d \
  --name sci-calc \
  -p 8080:8080 \
  your-dockerhub-username/scientific-calculator:1.0
```

* `-d` runs in detached mode.
* `--name` gives the container a friendly name.
* `-p 8080:8080` maps host port 8080 to container port 8080.

Once up, open your browser at:

```
http://localhost:8080/
```

You should see your calculator UI.

---

## 4. Push the Image to Docker Hub

1. **Log in to Docker Hub**

   ```bash
   docker login
   ```

   Enter your Docker Hub credentials when prompted.

2. **Push the image**

   ```bash
   docker push your-dockerhub-username/scientific-calculator:1.0
   ```

Your image is now available on Docker Hub at:

```
https://hub.docker.com/r/your-dockerhub-username/scientific-calculator
```

---

## 5. Pull and Run from Docker Hub

On any machine with Docker installed, you can now:

1. **Pull the image**

   ```bash
   docker pull your-dockerhub-username/scientific-calculator:1.0
   ```

2. **Run it**

   ```bash
   docker run -d \
     --name sci-calc \
     -p 8080:8080 \
     your-dockerhub-username/scientific-calculator:1.0
   ```

3. **Access the app**
   Navigate to `http://<host-ip>:8080/` in your browser.

---

### Summary of Commands

```bash
# 1. Build jar
mvn clean package

# 2. Build Docker image
docker build -t your-dockerhub-username/scientific-calculator:1.0 .

# 3. Run locally
docker run -d --name sci-calc -p 8080:8080 your-dockerhub-username/scientific-calculator:1.0

# 4. Push to Docker Hub
docker login
docker push your-dockerhub-username/scientific-calculator:1.0

# 5. Pull & run elsewhere
docker pull your-dockerhub-username/scientific-calculator:1.0
docker run -d --name sci-calc -p 8080:8080 your-dockerhub-username/scientific-calculator:1.0
```

That’s it—you now have a Dockerized scientific calculator that you can build, run, share, and deploy anywhere!
