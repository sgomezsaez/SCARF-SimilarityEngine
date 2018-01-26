FROM mysql/mysql-server:5.7
MAINTAINER Santiago Gomez - sgomezsaez@gmail.com

ENV MYSQL_ROOT_PASSWORD password
ENV MYSQL_DATABASE KnowledgeBase

COPY data/CaseBase.sql /docker-entrypoint-initdb.d/CaseBase.sql

#VOLUME ["/var/lib/mysql"]

ENTRYPOINT ["/entrypoint.sh"]
HEALTHCHECK CMD /healthcheck.sh
EXPOSE 3306 33060
CMD ["mysqld"]
