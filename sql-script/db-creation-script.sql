-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: fortune
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `puzzles`
--

DROP TABLE IF EXISTS `puzzles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `puzzles` (
  `id_puzzle` int NOT NULL,
  `category` enum('KRAJ','ZWIERZE','MIEJSCE','ZAWOD','SPORT','TRANSPORT','ELEKTRONIKA','EPOKA','FILM','INSTRUMENT','JEZYK') NOT NULL,
  `puzzle` varchar(10) NOT NULL,
  PRIMARY KEY (`id_puzzle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `puzzles`
--

LOCK TABLES `puzzles` WRITE;
/*!40000 ALTER TABLE `puzzles` DISABLE KEYS */;
INSERT INTO `puzzles` VALUES (1,'KRAJ','MEKSYK'),(2,'ZWIERZE','PAPUGA'),(3,'MIEJSCE','PLAZA'),(4,'ZAWOD','DOKTOR'),(5,'SPORT','TENIS'),(6,'TRANSPORT','POCIAG'),(9,'EPOKA','RENESANS'),(10,'FILM','AVATAR'),(11,'INSTRUMENT','FLET'),(12,'JEZYK','POLSKI'),(13,'KRAJ','ANGLIA'),(14,'KRAJ','KAZACHSTAN'),(15,'KRAJ','SALWADOR'),(16,'KRAJ','POLSKA'),(17,'KRAJ','NIEMCY'),(18,'ZWIERZE','PANDA'),(19,'ZWIERZE','CHOMIK'),(20,'ZWIERZE','NIEDZWIEDZ'),(21,'ZWIERZE','SARNA'),(22,'ZWIERZE','HIENA'),(23,'MIEJSCE','MIASTO'),(24,'MIEJSCE','JEZIORO'),(25,'ZAWOD','ARCHITEKT'),(26,'ZAWOD','BARMAN'),(27,'ZAWOD','FRYZJER'),(28,'ZAWOD','GRAFIK'),(29,'ZAWOD','KIEROWCA'),(30,'SPORT','HOKEJ'),(31,'SPORT','RUGBY'),(32,'SPORT','KRYKIET'),(33,'SPORT','GOLF'),(34,'TRANSPORT','SAMOCHOD'),(35,'TRANSPORT','SAMOLOT'),(36,'TRANSPORT','AUTOBUS'),(37,'TRANSPORT','TRAMWAJ'),(38,'TRANSPORT','MOTOCYKL'),(39,'TRANSPORT','ROWER'),(40,'ELEKTRONIKA','LAPTOP'),(41,'ELEKTRONIKA','TABLET'),(42,'ELEKTRONIKA','TELEFON'),(43,'ELEKTRONIKA','ZEGAREK'),(44,'ELEKTRONIKA','TELEWIZOR'),(45,'ELEKTRONIKA','KOMPUTER'),(46,'ELEKTRONIKA','PRALKA'),(47,'ELEKTRONIKA','ZMYWARKA'),(48,'EPOKA','KLASYCYZM'),(49,'EPOKA','ROMANTYZM'),(50,'FILM','THEROOM'),(51,'FILM','NIETYKALNI'),(52,'FILM','JOKER'),(53,'FILM','PIANISTA'),(54,'FILM','INCEPCJA'),(55,'FILM','GLADIATOR'),(56,'FILM','SHREK'),(57,'INSTRUMENT','GITARA'),(58,'INSTRUMENT','PIANINO'),(59,'INSTRUMENT','FORTEPIAN'),(60,'INSTRUMENT','TROJKAT'),(61,'INSTRUMENT','KONTRABAS'),(62,'INSTRUMENT','LUTNIA'),(63,'INSTRUMENT','BANJO'),(64,'JEZYK','ANGIELSKI'),(65,'JEZYK','ARABSKI'),(66,'JEZYK','NIEMIECKI'),(67,'JEZYK','FRANCUSKI'),(68,'JEZYK','SZWEDZKI');
/*!40000 ALTER TABLE `puzzles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ranking`
--

DROP TABLE IF EXISTS `ranking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ranking` (
  `id_player` int NOT NULL,
  `player_name` varchar(45) NOT NULL,
  `score` int DEFAULT NULL,
  PRIMARY KEY (`id_player`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ranking`
--

LOCK TABLES `ranking` WRITE;
/*!40000 ALTER TABLE `ranking` DISABLE KEYS */;
/*!40000 ALTER TABLE `ranking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'fortune'
--
/*!50003 DROP PROCEDURE IF EXISTS `addWordPuzzle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addWordPuzzle`( category enum("KRAJ","ZWIERZE","MIEJSCE","ZAWOD","SPORT","TRANSPORT","ELEKTRONIKA","EPOKA","FILM","INSTRUMENT","JEZYK"),
								puzzle varchar(10))
BEGIN
	SET @idPuzzle = (SELECT MAX(id_puzzle) FROM puzzles) + 1;
    IF(@idPuzzle is not null) then
			INSERT INTO puzzles(id_puzzle,category,puzzle) values(@idPuzzle,category,puzzle);
    ELSE
		INSERT INTO puzzles(id_puzzle,category,puzzle) values(1,category,puzzle);
	END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-22 21:08:29
