-- MySQL dump 10.13  Distrib 5.5.46, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: KnowledgeBase
-- ------------------------------------------------------
-- Server version	5.5.46-0ubuntu0.14.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_description`
--

DROP TABLE IF EXISTS `app_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_description` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_solution_id` int(11) DEFAULT NULL,
  `fk_qosspec_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_solution_id_idx` (`fk_solution_id`),
  KEY `fk_qosspec_id_idx` (`fk_qosspec_id`),
  CONSTRAINT `fk_qosspec_id` FOREIGN KEY (`fk_qosspec_id`) REFERENCES `qos_specification` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_solution_id` FOREIGN KEY (`fk_solution_id`) REFERENCES `viable_topology_distribution` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_description`
--

LOCK TABLES `app_description` WRITE;
/*!40000 ALTER TABLE `app_description` DISABLE KEYS */;
INSERT INTO `app_description` VALUES (10,1,1),(4,1,5),(9,6,10),(7,8,11),(75,2,2),(79,3,3),(116,5,3);
/*!40000 ALTER TABLE `app_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hard_constraints`
--

DROP TABLE IF EXISTS `hard_constraints`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hard_constraints` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `value` varchar(45) DEFAULT NULL,
  `fk_app_description_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_hard_constraints_1_idx` (`fk_app_description_id`),
  CONSTRAINT `fk_hard_constraints_1` FOREIGN KEY (`fk_app_description_id`) REFERENCES `app_description` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hard_constraints`
--

LOCK TABLES `hard_constraints` WRITE;
/*!40000 ALTER TABLE `hard_constraints` DISABLE KEYS */;
/*!40000 ALTER TABLE `hard_constraints` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `performance`
--

DROP TABLE IF EXISTS `performance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `performance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `performance`
--

LOCK TABLES `performance` WRITE;
/*!40000 ALTER TABLE `performance` DISABLE KEYS */;
INSERT INTO `performance` VALUES (1),(2),(3),(8),(9),(10),(11);
/*!40000 ALTER TABLE `performance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `performance_metric`
--

DROP TABLE IF EXISTS `performance_metric`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `performance_metric` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `fk_category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category_id_idx` (`fk_category_id`),
  CONSTRAINT `fk_category_id` FOREIGN KEY (`fk_category_id`) REFERENCES `performance_metric_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `performance_metric`
--

LOCK TABLES `performance_metric` WRITE;
/*!40000 ALTER TABLE `performance_metric` DISABLE KEYS */;
INSERT INTO `performance_metric` VALUES (1,'Response Time',1),(2,'Throughput',1),(3,'Processing Time',1),(4,'Average Read Speed',1),(5,'Average Write Speed',1),(6,'Average Migration Time',1),(7,'Latency',1),(8,'Backup Time',1),(9,'Bandwidth',2),(10,'Processor Speed',2),(11,'Storage Size',2),(12,'Memory Allocation to VM',2),(13,'Nuber of VM',2),(14,'Number of Processors',2),(15,'I/O Operations per Second',2),(16,'Network Utilization',3),(17,'Memory Utilization',3),(18,'Disk Utilization',3),(19,'CPU Utilization',3),(20,'VM Utilization',3),(21,'VM per Physical Server',3),(22,'Resource Acquisition Time',4),(23,'Resource Provisioning TIme',4),(24,'Deployment Time',4),(25,'Resource Release Time',4),(26,'VM Startup Time',4),(27,'Cloud Service Uptime',5),(28,'Cloud Resources Uptime',5),(29,'Mean Time Between Failures',5),(30,'Mean Time to Repair',5);
/*!40000 ALTER TABLE `performance_metric` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `performance_metric_category`
--

DROP TABLE IF EXISTS `performance_metric_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `performance_metric_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `performance_metric_category`
--

LOCK TABLES `performance_metric_category` WRITE;
/*!40000 ALTER TABLE `performance_metric_category` DISABLE KEYS */;
INSERT INTO `performance_metric_category` VALUES (1,'Time Behaviour'),(2,'Capacity'),(3,'Resource Utilization'),(4,'Scalability'),(5,'Availability');
/*!40000 ALTER TABLE `performance_metric_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `performance_metric_descriptor`
--

DROP TABLE IF EXISTS `performance_metric_descriptor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `performance_metric_descriptor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `min` float DEFAULT NULL,
  `max` float DEFAULT NULL,
  `mean` float DEFAULT NULL,
  `st_deviation` float DEFAULT NULL,
  `fk_performance_id` int(11) DEFAULT NULL,
  `fk_metric_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_performance_id_idx` (`fk_performance_id`),
  KEY `fk_metric_id_idx` (`fk_metric_id`),
  CONSTRAINT `fk_metric_id` FOREIGN KEY (`fk_metric_id`) REFERENCES `performance_metric` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_performance_metric_descriptor_1` FOREIGN KEY (`fk_performance_id`) REFERENCES `performance` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `performance_metric_descriptor`
--

LOCK TABLES `performance_metric_descriptor` WRITE;
/*!40000 ALTER TABLE `performance_metric_descriptor` DISABLE KEYS */;
INSERT INTO `performance_metric_descriptor` VALUES (1,100,1000,550,2.3,1,1),(2,3,4,3.5,0.71,1,2),(4,300,2000,1150,1202.08,2,1),(5,5,8,6.5,2.12,2,2),(6,120,900,510,551.54,1,3),(7,8000,8000,8000,0,1,4),(8,8000,8000,8000,0,1,5),(9,70,130,100,42.43,1,6),(10,95,460,277.5,258.09,1,7),(11,110,280,195,120.21,1,8),(12,150,650,400,353.55,1,9),(13,7,13,10,4.24,1,10),(14,520,520,520,0,1,11),(15,5,5,5,0,1,12),(16,1,5,3,2.83,1,13),(17,4,4,4,4,1,14),(18,6000,8000,7000,1414.21,1,15),(19,500,3000,1750,1767.77,3,1),(20,0,2,1,1.41,3,2),(21,300,4000,2150,2616.3,3,3),(22,2000,2000,2000,0,3,4),(23,2000,2000,2000,0,3,5),(24,200,1000,600,565.69,3,6),(25,95,460,277.5,258.09,3,7),(26,500,1000,750,353.5,3,8),(43,20,0,20,20,8,1),(44,202,2,2,0,8,2),(45,2,2,30,0,8,3),(46,65,120,92.5,38.89,1,16),(75,65,120,92.5,38.89,1,17),(76,70,130,100,42.43,1,22),(77,65,120,92.5,38.89,1,18),(78,65,120,92.5,38.89,1,19),(79,65,120,92.5,38.89,1,20),(80,1,5,3,2.83,1,21),(81,70,130,100,42.43,1,23),(82,40,130,85,63.64,1,24),(83,40,70,55,21.21,1,25),(84,40,70,55,21.21,1,26),(85,95,100,97.5,3.54,1,27),(86,95,100,97.5,3.54,1,28),(87,32,32,32,0,1,29),(88,2,2,2,0,1,30),(89,200,1200,700,707.11,2,3),(90,10000,10000,10000,0,2,4),(91,10000,10000,10000,0,2,5),(92,100,230,165,91.92,2,6),(93,120,800,460,480.83,2,7),(94,140,450,295,219.2,2,8),(95,300,1000,650,494.97,2,9),(96,10,20,15,7.07,2,10),(97,3,8,5.5,3.54,2,13),(98,6,6,6,0,2,14),(99,8000,9000,8500,707.11,2,15),(100,80,200,140,84.85,2,16),(101,80,200,140,84.85,2,17),(102,400,400,400,0,2,11),(103,10,10,15,7.07,2,12),(104,80,200,140,84.85,2,18),(105,80,200,140,84.85,2,19),(106,80,200,140,24.85,2,20),(107,1,5,3,2.83,2,21),(108,70,130,100,42.43,2,22),(109,70,130,100,42.43,2,23),(110,40,130,85,63.64,2,24),(111,40,70,55,21.21,2,25),(112,40,70,55,21.21,2,26),(113,80,100,90,14.14,2,27),(114,80,100,90,14.14,2,28),(115,72,72,72,0,2,29),(116,5,5,5,0,2,30),(117,100,800,450,494.97,11,1),(118,2,3,2.5,0.71,11,2),(119,100,800,450,494.97,11,3),(120,7200,7200,7200,0,11,4),(121,7200,7200,7200,0,11,5),(122,60,120,90,42.43,11,6),(123,100,480,290,268.7,11,7),(124,120,300,210,127.28,11,8),(125,100,600,350,353.55,11,9),(126,8.8,12.8,10.8,2.83,11,10),(127,500,500,500,0,11,11),(128,5,5,5,0,11,12),(129,1,5,3,2.83,11,13),(130,4,4,4,0,11,14),(131,5000,8000,6500,2121.32,11,15),(132,60,100,80,28.28,11,16),(133,60,100,80,28.28,11,17),(134,40,100,70,42.43,11,18),(135,60,100,80,28.28,11,19),(136,60,100,80,28.28,11,20),(137,1,5,3,2.83,11,21),(138,60,120,90,42.43,11,22),(139,60,120,90,42.43,11,23),(140,30,120,75,63.64,11,24),(141,30,60,45,21.21,11,25),(142,30,60,45,21.21,11,26),(143,99,100,99.5,0.71,11,27),(144,99,100,99.5,0.71,11,28),(145,48,48,48,0,11,29),(146,1,1,1,0,11,30);
/*!40000 ALTER TABLE `performance_metric_descriptor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qos_specification`
--

DROP TABLE IF EXISTS `qos_specification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qos_specification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_workload_id` int(11) DEFAULT NULL,
  `fk_performance_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_qos_specification_1_idx` (`fk_workload_id`),
  KEY `fk_qos_specification_2_idx` (`fk_performance_id`),
  CONSTRAINT `fk_qos_specification_1` FOREIGN KEY (`fk_workload_id`) REFERENCES `workload` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_qos_specification_2` FOREIGN KEY (`fk_performance_id`) REFERENCES `performance` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qos_specification`
--

LOCK TABLES `qos_specification` WRITE;
/*!40000 ALTER TABLE `qos_specification` DISABLE KEYS */;
INSERT INTO `qos_specification` VALUES (1,1,1),(2,2,2),(3,3,3),(5,3,3),(10,10,8),(11,11,11);
/*!40000 ALTER TABLE `qos_specification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `similarity_table`
--

DROP TABLE IF EXISTS `similarity_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `similarity_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `similarity_table`
--

LOCK TABLES `similarity_table` WRITE;
/*!40000 ALTER TABLE `similarity_table` DISABLE KEYS */;
INSERT INTO `similarity_table` VALUES (1,'pattern'),(2,'arrival_rate'),(3,'behavioral_model'),(4,'node_type');
/*!40000 ALTER TABLE `similarity_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_entry`
--

DROP TABLE IF EXISTS `table_entry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_entry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `column_name` varchar(45) DEFAULT NULL,
  `row_name` varchar(45) DEFAULT NULL,
  `similarity_measure` float DEFAULT NULL,
  `fk_table_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_table_id_idx` (`fk_table_id`),
  CONSTRAINT `fk_table_id` FOREIGN KEY (`fk_table_id`) REFERENCES `similarity_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_entry`
--

LOCK TABLES `table_entry` WRITE;
/*!40000 ALTER TABLE `table_entry` DISABLE KEYS */;
INSERT INTO `table_entry` VALUES (1,'continuosly changing','continuously changing',1,1),(2,'continuosly changing','once in a lifetime',0,1),(3,'continuosly changing','periodic',0,1),(4,'continuosly changing','static',0,1),(5,'continuosly changing','unpredictable',0,1),(6,'once in a lifetime','continuosly changing',0,1),(7,'once in a lifetime','once in a lifetime',1,1),(8,'once in a lifetime','periodic',0,1),(9,'once in a lifetime','static',0,1),(10,'once in a lifetime','unpredictable',0,1),(11,'periodic','continuosly changing',0,1),(12,'periodic','once in a lifetime',0,1),(13,'periodic','periodic',1,1),(14,'periodic','static',0,1),(15,'periodic','unpredictable',0,1),(16,'static','continuosly changing',0,1),(17,'static','once in a lifetime',0,1),(18,'static','periodic',0,1),(19,'static','static',1,1),(20,'static','unpredictable',0,1),(21,'unpredictable','continuosly changing',0,1),(22,'unpredictable','once in a lifetime',0,1),(23,'unpredictable','periodic',0,1),(24,'unpredictable','static',0,1),(25,'unpredictable','unpredictable',1,1),(26,'normal','normal',1,2),(27,'normal','logarithmic',0,2),(28,'normal','gamma',0,2),(29,'normal','uniform',0,2),(30,'logarithmic','normal',0,2),(31,'logarithmic','logarithmic',1,2),(32,'logarithmic','gamma',0,2),(33,'logarithmic','uniform',0,2),(34,'gamma','normal',0,2),(35,'gamma','logarithmic',0,2),(36,'gamma','gamma',1,2),(37,'gamma','uniform',0,2),(38,'uniform','normal',0,2),(39,'uniform','logarithmic',0,2),(40,'uniform','gama',0,2),(41,'uniform','uniform',1,2),(42,'normal','normal',1,3),(43,'normal','logarithmic',0,3),(44,'normal','gamma',0,3),(45,'normal','uniform',0,3),(46,'logarithmic','normal',0,3),(47,'logarithmic','logarithmic',1,3),(48,'logarithmic','gamma',0,3),(49,'logarithmic','uniform',0,3),(50,'gamma','normal',0,3),(51,'gamma','logarithmic',0,3),(52,'gamma','gamma',1,3),(53,'gamma','uniform',0,3),(54,'uniform','normal',0,3),(55,'uniform','logarithmic',0,3),(56,'uniform','gamma',0,3),(57,'uniform','uniform',1,3);
/*!40000 ALTER TABLE `table_entry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viable_topology_distribution`
--

DROP TABLE IF EXISTS `viable_topology_distribution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `viable_topology_distribution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text,
  `url` text,
  `dist_url` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viable_topology_distribution`
--

LOCK TABLES `viable_topology_distribution` WRITE;
/*!40000 ALTER TABLE `viable_topology_distribution` DISABLE KEYS */;
INSERT INTO `viable_topology_distribution` VALUES (1,'Media Wiki App','/winery/servicetemplates/http%253A%252F%252Fwww.eclipse.org%252Fwinery%252Fmodel%252Fselfservice/Media_Wiki/topologytemplate/?view','http%3A%2F%2Flocalhost%3A8080%2Fwinery%2F&nsmu=http%3A%2F%2Fwww.eclipse.org%2Fwinery%2Fmodel%2Fselfservice&muid=Media_Wiki'),(2,'Media Wiki V2','/winery/servicetemplates/http%253A%252F%252Fwww.eclipse.org%252Fwinery%252Fmodel%252Fselfservice/Media_Wiki_V2/topologytemplate/?view','http%3A%2F%2Flocalhost%3A8080%2Fwinery%2F&nsmu=http%3A%2F%2Fwww.eclipse.org%2Fwinery%2Fmodel%2Fselfservice&muid=Media_Wiki_V2'),(3,'Media Wiki V3','/winery/servicetemplates/http%253A%252F%252Fwww.eclipse.org%252Fwinery%252Fmodel%252Fselfservice/Media_Wiki_V3/topologytemplate/?view','http%3A%2F%2Flocalhost%3A8080%2Fwinery%2F&nsmu=http%3A%2F%2Fwww.eclipse.org%2Fwinery%2Fmodel%2Fselfservice&muid=Media_Wiki_V3'),(4,'Media Wiki V4','/winery/servicetemplates/http%253A%252F%252Fwww.eclipse.org%252Fwinery%252Fmodel%252Fselfservice/Media_Wiki_V4/topologytemplate/?view','http%3A%2F%2Flocalhost%3A8080%2Fwinery%2F&nsmu=http%3A%2F%2Fwww.eclipse.org%2Fwinery%2Fmodel%2Fselfservice&muid=Media_Wiki_V4'),(5,'Web Shop','/winery/servicetemplates/http%253A%252F%252Fwww.eclipse.org%252Fwinery%252Fmodel%252Fselfservice/WebShop/topologytemplate/?view','http%3A%2F%2Flocalhost%3A8080%2Fwinery%2F&nsmu=http%3A%2F%2Fwww.eclipse.org%2Fwinery%2Fmodel%2Fselfservice&muid=WebShop'),(6,'myNewWiki','/winery/servicetemplates/http%253A%252F%252Fwww.eclipse.org%252Fwinery%252Fmodel%252Fselfservice/myNewWiki/topologytemplate/?view','http%3A%2F%2Flocalhost%3A8080%2Fwinery%2F&nsmu=http%3A%2F%2Fwww.eclipse.org%2Fwinery%2Fmodel%2Fselfservice&muid=myNewWiki'),(8,'NewWiki','/winery/servicetemplates/http%253A%252F%252Fwww.eclipse.org%252Fwinery%252Fmodel%252Fselfservice/NewWiki/topologytemplate/?view','http%3A%2F%2Flocalhost%3A8080%2Fwinery%2F&nsmu=http%3A%2F%2Fwww.eclipse.org%2Fwinery%2Fmodel%2Fselfservice&muid=NewWiki'),(9,'newDistribution','/winery/servicetemplates/http%253A%252F%252Fwww.eclipse.org%252Fwinery%252Fmodel%252Fselfservice/newDistribution/topologytemplate/?view','http%3A%2F%2Flocalhost%3A8080%2Fwinery%2F&nsmu=http://www.eclipse.org/winery/model/selfservice&muid=newDistribution');
/*!40000 ALTER TABLE `viable_topology_distribution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workload`
--

DROP TABLE IF EXISTS `workload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workload` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pattern` varchar(45) DEFAULT NULL,
  `arrival_rate` varchar(45) DEFAULT NULL,
  `behavioral_model` varchar(45) DEFAULT NULL,
  `avg_usr_number` float DEFAULT NULL,
  `avg_transactions_second` float DEFAULT NULL,
  `time_interval` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workload`
--

LOCK TABLES `workload` WRITE;
/*!40000 ALTER TABLE `workload` DISABLE KEYS */;
INSERT INTO `workload` VALUES (1,'continuosly changing','normal','normal',2500,11000,'monthly'),(2,'periodic','normal','logarithmic',5000,15000,'monthly'),(3,'once in a lifetime','logarithmic','normal',200,2000,'monthly'),(5,'once in a lifetime','logarithmic','normal',200,300,'monthly'),(9,'unpredictable','logarithmic','logarithmic',200,5000,'monthly'),(10,'unpredictable','logarithmic','logarithmic',200,5000,'monthly'),(11,'continouously changing','normal','normal',2000,10000,'monthly');
/*!40000 ALTER TABLE `workload` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-27 18:31:56
