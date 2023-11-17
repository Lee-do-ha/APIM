-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: k9c201a.p.ssafy.io    Database: first_submit
-- ------------------------------------------------------
-- Server version	8.0.35-0ubuntu0.20.04.1

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
-- Table structure for table `transfer`
--

DROP TABLE IF EXISTS `transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transfer` (
  `transfer_id` bigint NOT NULL AUTO_INCREMENT,
  `arrival_date` date DEFAULT NULL,
  `departure_date` date DEFAULT NULL,
  `departure_location` varchar(255) DEFAULT NULL,
  `destination_location` varchar(255) DEFAULT NULL,
  `product` varchar(255) DEFAULT NULL,
  `quantity` bigint DEFAULT NULL,
  `transport_cost` bigint DEFAULT NULL,
  `transportation` varchar(255) DEFAULT NULL,
  `unit_price` bigint DEFAULT NULL,
  `way_bill_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`transfer_id`),
  UNIQUE KEY `UK_q5upedmleu4a2f5tccf41c1af` (`way_bill_number`)
) ENGINE=InnoDB AUTO_INCREMENT=975 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transfer`
--

LOCK TABLES `transfer` WRITE;
/*!40000 ALTER TABLE `transfer` DISABLE KEYS */;
INSERT INTO `transfer` VALUES (1,'2023-03-18','2023-03-17','광명시','부여군','가공 식품',91,2494037,'비행기',27407,'INV0001'),(2,'2023-09-13','2023-09-08','동해시','양구군','가공 식품',84,7118748,'열차',84747,'INV0002'),(3,'2023-02-11','2023-02-03','평창군','홍성군','자동차 부품',60,3455160,'비행기',57586,'INV0003'),(4,'2023-11-20','2023-11-15','진천군','연천군','가전제품',36,1706184,'열차',47394,'INV0004'),(5,'2023-11-02','2023-10-30','영월군','영동군','전자제품',44,3933512,'비행기',89398,'INV0005'),(6,'2023-07-04','2023-07-01','안양시','고양시 일산서구','신발',65,3879980,'선박',59692,'INV0006'),(7,'2023-04-23','2023-04-13','옥천군','용인시','의류',43,1567522,'비행기',36454,'INV0007'),(8,'2023-08-17','2023-08-12','수원시 장안구','예산군','공구',20,1903660,'선박',95183,'INV0008'),(9,'2023-10-21','2023-10-18','홍성군','용인시 처인구','가공 식품',10,73880,'선박',7388,'INV0009'),(10,'2023-06-30','2023-06-21','군포시','아산시','신발',36,2393604,'비행기',66489,'INV0010'),(11,'2023-11-15','2023-11-08','광명시','수원시','식물',14,1155112,'선박',82508,'INV0011'),(12,'2023-10-04','2023-10-03','성남시 수정구','횡성군','의류',97,1889075,'비행기',19475,'INV0012'),(13,'2023-10-25','2023-10-20','예산군','춘천시','식물',73,3437497,'비행기',47089,'INV0013'),(14,'2023-09-05','2023-08-29','의정부시','부천시 소사구','가전제품',95,889200,'선박',9360,'INV0014'),(15,'2023-05-04','2023-04-28','보은군','증평군','가전제품',88,6526344,'비행기',74163,'INV0015'),(16,'2023-01-07','2023-01-05','수원시','화성시','문구류',74,235468,'트럭',3182,'INV0016'),(17,'2023-06-28','2023-06-24','부천시 오정구','의왕시','액세서리',10,546510,'트럭',54651,'INV0017'),(18,'2023-01-09','2023-01-03','부천시 소사구','김포시','식기류',55,4628745,'열차',84159,'INV0018'),(19,'2023-09-23','2023-09-13','화성시','안성시','가구',26,236938,'선박',9113,'INV0019'),(20,'2023-05-15','2023-05-14','부여군','광명시','의류',31,1513451,'트럭',48821,'INV0020'),(21,'2023-06-30','2023-06-26','양평군','보령시','공구',5,118240,'트럭',23648,'INV0021'),(22,'2023-01-17','2023-01-09','용인시','고양시','운동용품',94,8192570,'트럭',87155,'INV0022'),(23,'2023-02-16','2023-02-06','천안시 서북구','동해시','장난감',58,677092,'트럭',11674,'INV0023'),(24,'2023-04-25','2023-04-22','수원시 장안구','강릉시','공구',40,2459240,'비행기',61481,'INV0024'),(25,'2023-06-25','2023-06-19','진천군','과천시','문구류',17,358394,'비행기',21082,'INV0025'),(26,'2023-03-12','2023-03-10','군포시','청주시 서원구','약품',39,3732534,'열차',95706,'INV0026'),(27,'2023-04-13','2023-04-08','부천시 오정구','연천군','자동차 부품',3,134100,'열차',44700,'INV0027'),(28,'2023-02-24','2023-02-23','태안군','원주시','화장품',86,3265076,'선박',37966,'INV0028'),(29,'2023-09-02','2023-08-25','포천시','춘천시','전자제품',32,430560,'비행기',13455,'INV0029'),(30,'2023-05-30','2023-05-27','남양주시','시흥시','신발',34,2657678,'트럭',78167,'INV0030'),(31,'2023-06-10','2023-06-09','수원시 팔달구','남양주시','운동용품',52,2398604,'비행기',46127,'INV0031'),(32,'2023-05-01','2023-04-28','의왕시','이천시','서적',47,3377796,'트럭',71868,'INV0032'),(33,'2023-09-04','2023-08-30','용인시 처인구','의왕시','의류',53,949124,'트럭',17908,'INV0033'),(34,'2023-11-23','2023-11-20','안산시 단원구','고양시','장난감',77,1772848,'비행기',23024,'INV0034'),(35,'2023-12-29','2023-12-28','광주시','화천군','전자제품',79,4189370,'선박',53030,'INV0035'),(36,'2023-12-22','2023-12-13','수원시 팔달구','시흥시','가전제품',49,4735850,'비행기',96650,'INV0036'),(37,'2023-07-25','2023-07-21','철원군','정선군','식품',69,5947179,'선박',86191,'INV0037'),(38,'2023-03-27','2023-03-20','시흥시','용인시','신발',11,391831,'트럭',35621,'INV0038'),(39,'2023-05-29','2023-05-27','군포시','수원시','식물',83,1078585,'열차',12995,'INV0039'),(40,'2023-09-02','2023-08-29','수원시 영통구','여주시','가구',56,4457824,'열차',79604,'INV0040'),(41,'2023-08-12','2023-08-09','청양군','안성시','액세서리',71,3772372,'트럭',53132,'INV0041'),(42,'2023-11-28','2023-11-23','평창군','안산시 상록구','공구',88,6553536,'열차',74472,'INV0042'),(43,'2023-09-14','2023-09-12','양주시','의정부시','전자제품',41,226853,'트럭',5533,'INV0043'),(44,'2024-01-06','2023-12-30','여주시','영월군','운동용품',15,84600,'트럭',5640,'INV0044'),(45,'2023-05-09','2023-05-02','음성군','안양시 만안구','가전제품',39,2767908,'선박',70972,'INV0045'),(46,'2023-11-16','2023-11-10','수원시 권선구','수원시 팔달구','식품',67,3613042,'선박',53926,'INV0046'),(47,'2023-05-26','2023-05-21','태백시','고양시 덕양구','액세서리',48,1704288,'선박',35506,'INV0047'),(48,'2023-08-06','2023-07-31','파주시','용인시 기흥구','가전제품',23,1105426,'열차',48062,'INV0048'),(49,'2023-04-22','2023-04-20','성남시 분당구','부천시','의류',78,2574078,'트럭',33001,'INV0049'),(50,'2023-06-04','2023-05-30','화성시','김포시','문구류',42,2362794,'트럭',56257,'INV0050'),(51,'2023-07-16','2023-07-06','횡성군','화성시','약품',63,5942601,'비행기',94327,'INV0051'),(52,'2023-01-14','2023-01-08','파주시','아산시','식물',32,1698112,'트럭',53066,'INV0052'),(53,'2023-07-24','2023-07-18','포천시','여주시','자동차 부품',52,1552980,'열차',29865,'INV0053'),(54,'2023-07-02','2023-06-29','안양시 동안구','광명시','약품',10,771880,'열차',77188,'INV0054'),(55,'2023-02-07','2023-02-01','인제군','평택시','전자제품',35,1164730,'트럭',33278,'INV0055'),(56,'2023-01-30','2023-01-29','단양군','의정부시','가방',16,1566832,'열차',97927,'INV0056'),(57,'2023-03-15','2023-03-14','평택시','예산군','액세서리',61,3130764,'열차',51324,'INV0057'),(58,'2023-02-27','2023-02-21','증평군','광명시','화장품',12,197880,'선박',16490,'INV0058'),(59,'2023-04-23','2023-04-20','괴산군','안양시 만안구','운동용품',3,291132,'트럭',97044,'INV0059'),(60,'2023-09-22','2023-09-16','수원시 장안구','군포시','가공 식품',97,4451524,'트럭',45892,'INV0060'),(61,'2023-09-09','2023-09-02','정선군','괴산군','문구류',6,445086,'열차',74181,'INV0061'),(62,'2023-07-24','2023-07-14','논산시','의왕시','화장품',98,4257708,'선박',43446,'INV0062'),(63,'2023-04-12','2023-04-09','증평군','안산시 상록구','가방',3,89751,'트럭',29917,'INV0063'),(64,'2023-09-06','2023-09-05','연천군','단양군','가공 식품',10,523370,'열차',52337,'INV0064'),(65,'2023-04-02','2023-04-01','인제군','광주시','자동차 부품',21,363027,'선박',17287,'INV0065'),(66,'2023-05-25','2023-05-24','하남시','부천시 원미구','식물',18,1786968,'트럭',99276,'INV0066'),(67,'2023-05-10','2023-05-05','청주시 흥덕구','옥천군','문구류',22,2059244,'열차',93602,'INV0067'),(68,'2023-01-10','2023-01-03','고양시','고성군','식품',43,4244315,'비행기',98705,'INV0068'),(69,'2023-02-22','2023-02-12','동두천시','춘천시','식기류',99,2877039,'선박',29061,'INV0069'),(70,'2023-10-05','2023-09-25','성남시 수정구','서천군','문구류',50,320200,'트럭',6404,'INV0070'),(71,'2023-08-31','2023-08-26','안양시','하남시','화장품',18,659106,'선박',36617,'INV0071'),(72,'2023-05-11','2023-05-04','제천시','고양시 덕양구','화장품',48,4426896,'선박',92227,'INV0072'),(73,'2023-10-20','2023-10-19','당진시','이천시','식기류',5,95915,'트럭',19183,'INV0073'),(74,'2023-01-25','2023-01-17','서산시','양양군','신발',12,567300,'열차',47275,'INV0074'),(75,'2023-08-24','2023-08-20','수원시 장안구','양주시','공구',41,2843104,'비행기',69344,'INV0075'),(76,'2023-08-29','2023-08-28','과천시','부천시','음악 앨범',53,4946066,'열차',93322,'INV0076'),(77,'2023-06-17','2023-06-08','부천시','청주시 청원구','화장품',46,3027076,'비행기',65806,'INV0077'),(78,'2023-02-17','2023-02-15','충주시','인제군','가공 식품',57,4487382,'트럭',78726,'INV0078'),(79,'2023-05-09','2023-05-04','광명시','부천시 소사구','가공 식품',9,667602,'트럭',74178,'INV0079'),(80,'2023-02-08','2023-02-06','광명시','금산군','운동용품',69,6834519,'열차',99051,'INV0080'),(81,'2023-01-23','2023-01-14','수원시 팔달구','용인시 기흥구','전자제품',84,5676720,'트럭',67580,'INV0081'),(82,'2023-07-18','2023-07-14','철원군','영동군','약품',87,8395674,'선박',96502,'INV0082'),(83,'2023-10-30','2023-10-27','속초시','청주시 서원구','액세서리',71,2308210,'선박',32510,'INV0083'),(84,'2023-11-21','2023-11-19','당진시','용인시','운동용품',25,1821550,'트럭',72862,'INV0084'),(85,'2023-09-17','2023-09-08','속초시','부천시 오정구','음악 앨범',73,2785461,'선박',38157,'INV0085'),(86,'2023-11-03','2023-11-02','강릉시','청주시 청원구','가전제품',73,358576,'열차',4912,'INV0086'),(87,'2023-03-09','2023-03-03','광주시','안산시 상록구','자동차 부품',27,1329615,'열차',49245,'INV0087'),(88,'2023-10-26','2023-10-20','예산군','광주시','장난감',48,523968,'열차',10916,'INV0088'),(89,'2023-02-25','2023-02-19','천안시 동남구','고양시 일산서구','음악 앨범',38,397632,'트럭',10464,'INV0089'),(90,'2023-12-11','2023-12-03','청양군','금산군','전자제품',43,1676570,'선박',38990,'INV0090'),(91,'2023-11-20','2023-11-14','논산시','양평군','장난감',15,1259640,'트럭',83976,'INV0091'),(92,'2023-01-08','2023-01-04','천안시 서북구','양양군','신발',93,8207715,'비행기',88255,'INV0092'),(93,'2023-12-27','2023-12-19','안산시','성남시 수정구','가방',72,2457720,'트럭',34135,'INV0093'),(94,'2023-05-12','2023-05-08','춘천시','화천군','가전제품',51,2244255,'열차',44005,'INV0094'),(95,'2023-04-07','2023-04-03','당진시','청주시 청원구','가전제품',30,534090,'트럭',17803,'INV0095'),(96,'2024-01-03','2023-12-26','이천시','고양시','액세서리',35,1770475,'열차',50585,'INV0096'),(97,'2023-08-06','2023-08-05','수원시 팔달구','용인시','식물',56,1810536,'선박',32331,'INV0097'),(98,'2023-02-20','2023-02-10','청주시 청원구','단양군','자동차 부품',29,1860466,'선박',64154,'INV0098'),(99,'2023-12-23','2023-12-20','안산시 단원구','논산시','가공 식품',11,886402,'선박',80582,'INV0099'),(100,'2023-05-15','2023-05-10','양양군','용인시 처인구','운동용품',53,2527517,'선박',47689,'INV0100'),(101,'2023-11-15','2023-11-13','서울특별시','광주 광산구','장난감',150,102930,'차량',300,'INV0101');
/*!40000 ALTER TABLE `transfer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-17 11:47:25
