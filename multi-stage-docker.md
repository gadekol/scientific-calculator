# 1. Clone the repository
FROM alpine/git AS repo
WORKDIR /repo
RUN git clone https://github.com/rondusTechnology/maven-web-application.git .
# RUN git clone https://github.com/rondusTechnology/maven-web-application.git .

# 2. Build with Maven (using Java 11)
FROM maven:3.8.8-openjdk-11 AS build
WORKDIR /app
# Copy the cloned source code
COPY --from=repo /repo /app
# Build the project and skip tests for faster builds
RUN mvn clean package -DskipTests

# 3. Deploy to Tomcat
FROM tomcat:8.5-jre8
# Remove default apps (optional)
RUN rm -rf /usr/local/tomcat/webapps/*
# Copy the WAR and name it ROOT.war so it serves at "/"
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose the default Tomcat port
EXPOSE 8080

# Run Tomcat in the foreground
ENTRYPOINT ["catalina.sh", "run"]
