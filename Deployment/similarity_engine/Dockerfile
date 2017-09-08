FROM tomcat:7
MAINTAINER Santiago Gomez - sgomezsaez@gmail.com

COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY SimilarityEngine.war /usr/local/tomcat/webapps/
#COPY winery-topologymodeler.war /usr/local/tomcat/webapps/
#COPY winery.war /usr/local/tomcat/webapps/

EXPOSE 8080
CMD ["catalina.sh", "run"]