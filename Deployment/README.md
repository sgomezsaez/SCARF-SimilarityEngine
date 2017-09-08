- Start knowledge_db container: docker build -t knowledge-base . && docker run -d -p 3306:3306 -e MYSQL_USER=user -e MYSQL_PASSWORD=password -e MYSQL_DATABASE=KnowledgeBase -e MYSQL_ROOT_HOST=172.17.0.1 -t knowledge-base
- Similarity Engine: docker build -t similarity-engine .  && docker run -i -p 8080:8080 -t similarity-engine

1. Clone the repository to your favourite location
2. Go to the directory ../Prototype/Binaries2
3. Enter super user mode
4. Execute chmod u+x ./scriptWineryCBR.sh
5. Execute ./scriptWineryCBR.sh
6. Set database credentials to user root and password root. If this needs to be changed, the corresponding configuration fie
   needs to be changed, please check the development manual for references.
