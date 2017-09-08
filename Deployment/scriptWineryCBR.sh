#!/bin/bash

set -e

###########JAVA
JDK_NAME="openjdk-7-jdk"
sudo apt-get install openjdk-7-jdk
#CHANGE THE PATH IF NOT INSTALLED THERE
export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64
export PATH=$PATH:/usr/lib/jvm/java-7-openjdk-amd64/bin

# Affirm completion, optionally delete archive, and exit
echo "Java Development Kit version $JDK_VER successfully installed!"



#TOMCAT
TOMCAT_VERSION="7.0.54"
TOMCAT_ARCHIVE=$TOMCAT_VERSION.tar.gz
TOMCAT_URL="http://archive.apache.org/dist/tomcat/tomcat-7/v$TOMCAT_VERSION/bin/apache-tomcat-$TOMCAT_VERSION.tar.gz"
TOMCAT_USER="winery"
TOMCAT_PASSWORD="winery"

WAR_FILE_SE="SimilarityEngine.war"
WAR_WINERYCBR="winery.war"
WAR_WINERYCBR_MODELER="winery-topologymodeler.war"

CATALINA_HOME="/opt/apache-tomcat-${TOMCAT_VERSION}"

KNOWLEDGE_BASE_DB_NAME="KnowledgeBase"

wget ${TOMCAT_URL} -O /tmp/catalina.tar.gz && \
        tar -zxf /tmp/catalina.tar.gz -C /opt && \
        rm /tmp/catalina.tar.gz


if [ ! -r $WAR_FILE_SE -o ! -r $WAR_WINERYCBR -o ! -r $WAR_WINERYCBR_MODELER ]; then
    echo "War files are missing. Download it and run this again to deploy it." 1>&2
else
    cp $WAR_FILE_SE /opt/apache-tomcat-${TOMCAT_VERSION}/webapps
    cp $WAR_WINERYCBR /opt/apache-tomcat-${TOMCAT_VERSION}/webapps
    cp $WAR_WINERYCBR_MODELER /opt/apache-tomcat-${TOMCAT_VERSION}/webapps
fi

cp tomcat-users.xml $CATALINA_HOME/conf/tomcat-users.xml
echo "Configured access for user $TOMCAT_USER and password $TOMCAT_PASSWORD ..."

#Knowledge Base

sudo apt-get install mysql-server

if [ ! -d /var/lib/mysql/$KNOWLEDGE_BASE_DB_NAME ] ; then

	echo "Knowledge Based $KNOWLEDGE_BASE_DB_NAME does not exist. Creating..."
	echo "CREATE DATABASE $KNOWLEDGE_BASE_DB_NAME" | mysql -u root -p 
	mysql -u root -p $KNOWLEDGE_BASE_DB_NAME < CaseBase.sql

fi

echo "Knowledge Base created"


/opt/apache-tomcat-${TOMCAT_VERSION}/bin/catalina.sh run

echo "Process finished go to http://localost:8080/winery"
echo "Process finished go to http://localost:8080/SimilarityEngine/application-knowledge/7"
