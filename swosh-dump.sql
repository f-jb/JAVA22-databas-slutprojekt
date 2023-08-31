-- MariaDB dump 10.19  Distrib 10.5.22-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: swosh
-- ------------------------------------------------------
-- Server version	10.5.22-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `owner` int(11) DEFAULT NULL,
  `accountNumber` int(9) NOT NULL AUTO_INCREMENT,
  `accountName` varchar(50) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT current_timestamp(),
  `balance` decimal(18,2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`accountNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=500000009 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,500000000,'Hundmat','2023-08-31 08:45:44',35000.00),(1,500000001,'Hundleksaker','2023-08-31 08:45:59',2330.00),(3,500000003,'Guld','2023-08-31 08:52:25',999970999.00),(2,500000007,'kattmynta','2023-08-31 09:48:53',0.00),(2,500000008,'Kattsand','2023-08-31 09:49:10',5543.00);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp(),
  `fromUser` varchar(50) DEFAULT NULL,
  `fromAccount` int(11) DEFAULT NULL,
  `toUser` varchar(50) DEFAULT NULL,
  `toAccount` int(11) DEFAULT NULL,
  `amount` decimal(18,2) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,'2023-08-31 08:46:45','1',NULL,'1',500000000,3000.00,'Cash Deposit'),(2,'2023-08-31 08:47:18','1',NULL,'1',500000001,2330.00,'Cash Deposit'),(3,'2023-08-31 09:26:14','3',NULL,'3',500000003,999999999.00,'Cash Deposit'),(4,'2023-08-31 09:33:23','3',500000003,'1',500000000,32000.00,'L√∂n'),(5,'2023-08-31 09:37:34','2',NULL,'2',500000004,400.00,'Cash Deposit'),(6,'2023-08-31 09:49:18','2',NULL,'2',500000007,3000.00,'Cash Deposit'),(7,'2023-08-31 09:49:25','2',NULL,'2',500000008,5543.00,'Cash Deposit'),(8,'2023-08-31 13:13:22','2',500000007,'3',500000003,3000.00,'L√∂nnmord');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT current_timestamp(),
  `latestActivity` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `personalNumber` varchar(12) DEFAULT NULL,
  `password` varbinary(200) DEFAULT NULL,
  `hash` varbinary(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT 1,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `personalNumber` (`personalNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2023-08-31 08:40:11','2023-08-31 13:23:23','202209131234','”ìÈwe¨ÍDïØÈíÚÂ¬ÒØ·ﬂOú„`Î\'8ñhü0','ˆÉ0·)[èk','Bing Bingovich','bing@labrador.net',1,'0708888888','Testgatan 14'),(2,'2023-08-31 08:42:07','2023-08-31 09:48:44','201403211234','∫à´]_Ë+πæ7~w,Ó∆qi;¶-œ–ßQπìSª•','ôˇ\\\\~‚äñ','Yrsa','yrsa@katthotell.se',1,'07012345678','Kattegattg 34'),(3,'2023-08-31 08:43:42','2023-08-31 09:32:54','201705281234','·.8 Ωf_©àní≥µLø√<˝g` ;ù∏','·‘7\r˝™\0/','Joakim von Anka','joakim@pengabingen.se',1,'073535132','Pengabingen 1');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-31 15:25:27
