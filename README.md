# SCARF-SimilarityEngine
SCARF-Similarity is the similarity engine to compute similarity among reusable cloud application topologies

# Build Source & Run
1. Build *Similarity Engine* application: `cd SimilarityEngine && mvn install && cd ..`
2. Copy the WAR application to the Deployment directory: `cp ./SimilarityEngine/target/SimilarityEngine-0.0.1-SNAPSHOT.war ./Deployment/similarity_engine/SimilarityEngine.war`
3. Build the docker container: `cd Deployment && docker-compose build && cd ..`
4. Run: `cd Deployment && docker-compose up`

# Running the SCARF toolchain
If you want to run the complete tool chain, please go to [SCARF](https://github.com/sgomezsaez/SCARF)
