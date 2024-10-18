FROM bitnami/wildfly:latest

EXPOSE 8080

COPY target/my-web-app.war /opt/bitnami/wildfly/standalone/deployments

