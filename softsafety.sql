CREATE DATABASE  IF NOT EXISTS `softsafety` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_turkish_ci */;
USE `softsafety`;
--
-- Table structure for table `personel`
--

DROP TABLE IF EXISTS `personel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_turkish_ci DEFAULT NULL,
  `surname` varchar(45) COLLATE utf8_turkish_ci DEFAULT NULL,
  `age` varchar(45) COLLATE utf8_turkish_ci DEFAULT NULL,
  `mail` varchar(45) COLLATE utf8_turkish_ci DEFAULT NULL,
  `country` varchar(45) COLLATE utf8_turkish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personel`
--

LOCK TABLES `personel` WRITE;
/*!40000 ALTER TABLE `personel` DISABLE KEYS */;
INSERT INTO `personel` VALUES (1,'Gültekin','Bütün','29','gltknbtn@hotmail.com','Türkiye');
/*!40000 ALTER TABLE `personel` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_turkish_ci DEFAULT NULL,
  `surname` varchar(45) COLLATE utf8_turkish_ci DEFAULT NULL,
  `username` varchar(45) COLLATE utf8_turkish_ci DEFAULT NULL,
  `password` varchar(45) COLLATE utf8_turkish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Administrator','Administrator Surname','admin','3113327');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
