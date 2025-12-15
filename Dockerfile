FROM eclipse-temurin:25-jdk

ENV CATALINA_HOME=/opt/tomcat \
	TOMCAT_VERSION=10.1.28

# Install Tomcat 10.1 on JDK 25
RUN apt-get update -y \
	&& apt-get install -y --no-install-recommends curl ca-certificates \
	&& rm -rf /var/lib/apt/lists/* \
	&& curl -fsSL https://archive.apache.org/dist/tomcat/tomcat-10/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz \
	   | tar -xz -C /opt \
	&& mv /opt/apache-tomcat-${TOMCAT_VERSION} ${CATALINA_HOME} \
	&& rm -rf ${CATALINA_HOME}/webapps/* \
	&& sed -i 's/<Server port="8005"/<Server port="-1"/' ${CATALINA_HOME}/conf/server.xml

# Deploy the WAR as ROOT
COPY target/demo.war ${CATALINA_HOME}/webapps/ROOT.war

EXPOSE 8080

CMD ["bash", "-lc", "$CATALINA_HOME/bin/catalina.sh run"]
