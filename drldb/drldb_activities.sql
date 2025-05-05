-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: drldb
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `activities`
--

DROP TABLE IF EXISTS `activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `organizer_id` int DEFAULT NULL,
  `faculty_id` int DEFAULT NULL,
  `max_participants` int DEFAULT NULL,
  `current_participants` int DEFAULT '0',
  `point_type` enum('POINT_1','POINT_2','POINT_3','POINT_4') NOT NULL,
  `point_value` int DEFAULT NULL,
  `status` enum('UPCOMING','ONGOING','COMPLETED','CANCELLED') DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `organizer_id` (`organizer_id`),
  KEY `faculty_id` (`faculty_id`),
  CONSTRAINT `activities_ibfk_1` FOREIGN KEY (`organizer_id`) REFERENCES `users` (`id`) ON DELETE SET NULL,
  CONSTRAINT `activities_ibfk_2` FOREIGN KEY (`faculty_id`) REFERENCES `faculties` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activities`
--

LOCK TABLES `activities` WRITE;
/*!40000 ALTER TABLE `activities` DISABLE KEYS */;
INSERT INTO `activities` VALUES (1,'Workshop Lập Trình','Một buổi workshop để nâng cao kỹ năng lập trình.','2025-05-01 09:00:00','2025-05-01 17:00:00','Phòng 101, Tòa nhà ABC',1,1,50,25,'POINT_1',10,'UPCOMING','https://res.cloudinary.com/druxxfmia/image/upload/v1743644936/cld-sample-2.jpg',1),(2,'Ngày Thể Thao','Một ngày thi đấu các môn thể thao.','2025-06-10 08:00:00','2025-06-10 18:00:00','Sân Thể Thao, Trường ABC',2,2,100,60,'POINT_3',20,'UPCOMING','https://res.cloudinary.com/druxxfmia/image/upload/v1743644935/samples/coffee.jpg',1),(3,'Chuyến Du Lịch Xây Dựng Đội Nhóm','Chuyến đi xây dựng tinh thần đội nhóm cho cán bộ và sinh viên.','2025-07-15 10:00:00','2025-07-17 15:00:00','Khu nghỉ dưỡng núi',3,3,30,15,'POINT_2',30,'UPCOMING','https://res.cloudinary.com/druxxfmia/image/upload/v1743644936/cld-sample-2.jpg',1),(4,'Hackathon','Cuộc thi lập trình để tìm kiếm giải pháp sáng tạo.','2025-08-01 09:00:00','2025-08-01 17:00:00','Phòng 202, Tòa nhà DEF',2,1,60,30,'POINT_2',15,'UPCOMING','https://res.cloudinary.com/druxxfmia/image/upload/v1743644936/cld-sample-2.jpg',1),(5,'Hội Thảo Sức Khỏe và Phúc Lợi','Hội thảo về duy trì lối sống lành mạnh.','2025-09-05 10:00:00','2025-09-05 12:00:00','Phòng 305, Tòa nhà GHI',3,2,80,50,'POINT_1',10,'UPCOMING','https://res.cloudinary.com/druxxfmia/image/upload/v1743644935/samples/coffee.jpg',1),(6,'Hội Nghị Khoa Học','Hội nghị chia sẻ các nghiên cứu khoa học mới.','2025-10-10 08:00:00','2025-10-10 17:00:00','Phòng Hội nghị, Tòa nhà XYZ',1,1,100,70,'POINT_2',25,'UPCOMING','https://res.cloudinary.com/druxxfmia/image/upload/v1743644935/samples/coffee.jpg',1),(7,'Ngày Tình Nguyện','Hoạt động tình nguyện hỗ trợ cộng đồng.','2025-11-01 08:00:00','2025-11-01 18:00:00','Trung tâm cộng đồng, Thành phố ABC',1,3,200,150,'POINT_4',20,'UPCOMING','https://res.cloudinary.com/druxxfmia/image/upload/v1743644935/samples/coffee.jpg',1),(8,'Lễ Hội Văn Hóa','Chương trình giao lưu văn hóa giữa các quốc gia.','2025-12-05 09:00:00','2025-12-05 16:00:00','Sảnh Tòa nhà ABC',2,2,150,100,'POINT_4',30,'UPCOMING','https://res.cloudinary.com/druxxfmia/image/upload/v1743644935/samples/coffee.jpg',1),(9,'Chạy Marathon','Cuộc thi chạy marathon với sự tham gia của sinh viên và giảng viên.','2026-01-15 06:00:00','2026-01-15 12:00:00','Sân vận động ABC',3,1,500,350,'POINT_3',40,'UPCOMING','https://res.cloudinary.com/druxxfmia/image/upload/v1743644936/cld-sample-2.jpg',1),(10,'Tập huấn Quản Lý Thời Gian','Khóa học giúp cải thiện kỹ năng quản lý thời gian cho sinh viên.','2026-02-20 09:00:00','2026-02-20 17:00:00','Phòng 404, Tòa nhà JKL',1,3,60,45,'POINT_4',15,'UPCOMING','https://res.cloudinary.com/druxxfmia/image/upload/v1743644935/samples/coffee.jpg',1);
/*!40000 ALTER TABLE `activities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-05 21:03:11
