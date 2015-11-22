-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 22, 2015 at 01:07 PM
-- Server version: 5.5.35
-- PHP Version: 5.3.10-1ubuntu3.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `CourseData`
--

-- --------------------------------------------------------

--
-- Table structure for table `Admin`
--

CREATE TABLE IF NOT EXISTS `Admin` (
  `uID` varchar(5) DEFAULT NULL,
  `Name` varchar(13) DEFAULT NULL,
  `Password` varchar(6) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Admin`
--

INSERT INTO `Admin` (`uID`, `Name`, `Password`) VALUES
('a1345', 'Agent Smith', 'abc123'),
('a1346', 'Agent Jones', 'abc456'),
('a1347', 'Agent Roberts', 'abc789');

-- --------------------------------------------------------

--
-- Table structure for table `AdminClassLimit`
--

CREATE TABLE IF NOT EXISTS `AdminClassLimit` (
  `uID` varchar(7) DEFAULT NULL,
  `SemesterID` varchar(6) DEFAULT NULL,
  `CourseID` varchar(6) DEFAULT NULL,
  `CourseLimit` int(2) DEFAULT NULL,
  `ShadowMode` int(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `AdminClassLimit`
--

INSERT INTO `AdminClassLimit` (`uID`, `SemesterID`, `CourseID`, `CourseLimit`, `ShadowMode`) VALUES
('abc1346', '2015SP', 'CS6210', 40, 1),
('abc1346', '2015SU', 'CS6250', 40, 1);

-- --------------------------------------------------------

--
-- Table structure for table `AdminStaffAllocation`
--

CREATE TABLE IF NOT EXISTS `AdminStaffAllocation` (
  `uID` varchar(5) DEFAULT NULL,
  `CourseID` varchar(6) DEFAULT NULL,
  `SemesterID` varchar(6) DEFAULT NULL,
  `StaffID` varchar(5) DEFAULT NULL,
  `ProfessorOrTA` varchar(1) DEFAULT NULL,
  `ShadowMode` int(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `AdminStaffAllocation`
--

INSERT INTO `AdminStaffAllocation` (`uID`, `CourseID`, `SemesterID`, `StaffID`, `ProfessorOrTA`, `ShadowMode`) VALUES
('a1345', 'CS7641', '2016SP', 'p4674', 'P', 0),
('a1346', 'CS7641', '2016FA', 'p4674', 'P', 0);

-- --------------------------------------------------------

--
-- Table structure for table `Course`
--

CREATE TABLE IF NOT EXISTS `Course` (
  `CourseNum` int(2) DEFAULT NULL,
  `CourseID` varchar(10) DEFAULT NULL,
  `Description` varchar(58) DEFAULT NULL,
  `CourseLimit` int(3) DEFAULT NULL,
  `Prerequisite` int(2) DEFAULT NULL,
  `Corequisite` int(2) DEFAULT NULL,
  `SemesterOffered` varchar(5) DEFAULT NULL,
  `Sys_spec` int(1) DEFAULT NULL,
  `II_Spec` int(1) DEFAULT NULL,
  `ML_Spec` int(1) DEFAULT NULL,
  `Rob_Spec` int(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Course`
--

INSERT INTO `Course` (`CourseNum`, `CourseID`, `Description`, `CourseLimit`, `Prerequisite`, `Corequisite`, `SemesterOffered`, `Sys_spec`, `II_Spec`, `ML_Spec`, `Rob_Spec`) VALUES
(1, 'CS6035', 'Introduction to Information Security', 25, -1, -1, 'EVERY', 1, 0, 0, 0),
(2, 'CS6210', 'Advanced Operating Systems', 10, -1, -1, 'EVERY', 1, 0, 0, 0),
(3, 'CS6250', 'Computer Networks', 20, -1, -1, 'EVERY', 1, 0, 0, 0),
(4, 'CS6290', 'High Performance Computer Architecture', 40, -1, -1, 'EVERY', 1, 0, 0, 0),
(5, 'CS6300', 'Software Development Process', 100, -1, -1, 'EVERY', 1, 1, 0, 0),
(6, 'CS6310', 'Software Architecture and Design', 20, -1, -1, 'EVERY', 1, 0, 0, 0),
(7, 'CS6340', 'Software Analysis and Test', 20, -1, -1, 'EVERY', 1, 0, 0, 0),
(8, 'CS6400', 'Database Systems Concepts and Design', 20, -1, -1, 'EVERY', 1, 0, 0, 0),
(9, 'CS6440', 'Intro to Health Informatics', 40, -1, -1, 'EVERY', 0, 1, 0, 0),
(10, 'CS6475', 'Computational Photography', 10, -1, -1, 'EVERY', 0, 0, 0, 1),
(11, 'CS6476', 'Computer Vision', 20, -1, -1, 'EVERY', 0, 0, 0, 1),
(12, 'CS6505', 'Computability, Complexity and Algorithms', 150, -1, -1, 'EVERY', 1, 1, 1, 1),
(13, 'CS7637', 'Knowledge-Based Artificial Intelligence, Cognitive Systems', 20, -1, -1, 'EVERY', 0, 1, 0, 0),
(14, 'CS7641', 'Machine Learning', 25, -1, -1, 'EVERY', 0, 0, 1, 1),
(15, 'CS7646', 'Machine Learning for Trading', 20, -1, -1, 'EVERY', 0, 0, 1, 0),
(16, 'CS8803', 'Special Topics: Embedded Software', 20, -1, -1, 'EVERY', 0, 0, 1, 0),
(17, 'CS8803-001', 'Artificial Intelligence for Robotics', 100, -1, -1, 'EVERY', 0, 1, 0, 1),
(18, 'CS8803-002', 'Introduction to Operating Systems', 20, -1, -1, 'EVERY', 1, 0, 0, 0),
(19, 'CS8803-003', 'Special Topics: Reinforcement Learning', 40, -1, -1, 'EVERY', 0, 0, 1, 0),
(20, 'CSE6220', 'High-Performance Computing', 40, -1, -1, 'EVERY', 1, 0, 0, 0),
(21, 'CSE8803', 'Special Topics: Big Data', 150, -1, -1, 'EVERY', 0, 0, 1, 0),
(22, 'CSE8803', 'Special Topics: Big Data for Health Informatics', 40, -1, -1, 'EVERY', 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `dataModel`
--

CREATE TABLE IF NOT EXISTS `dataModel` (
  `A` varchar(10) DEFAULT NULL,
  `B` varchar(10) DEFAULT NULL,
  `C` varchar(10) DEFAULT NULL,
  `D` varchar(10) DEFAULT NULL,
  `E` varchar(10) DEFAULT NULL,
  `F` varchar(10) DEFAULT NULL,
  `G` varchar(10) DEFAULT NULL,
  `H` varchar(10) DEFAULT NULL,
  `I` varchar(10) DEFAULT NULL,
  `J` varchar(10) DEFAULT NULL,
  `K` varchar(41) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dataModel`
--

INSERT INTO `dataModel` (`A`, `B`, `C`, `D`, `E`, `F`, `G`, `H`, `I`, `J`, `K`) VALUES
(NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'notes'),
(NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'remove precoreqmap (integrated to course)');

-- --------------------------------------------------------

--
-- Table structure for table `LoginEvents`
--

CREATE TABLE IF NOT EXISTS `LoginEvents` (
  `uID` varchar(6) DEFAULT NULL,
  `TimeStamp` varchar(19) DEFAULT NULL,
  `Admin` int(1) DEFAULT NULL,
  `SuccessFail` int(1) DEFAULT NULL,
  `E` varchar(10) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `LoginEvents`
--

INSERT INTO `LoginEvents` (`uID`, `TimeStamp`, `Admin`, `SuccessFail`, `E`) VALUES
('a1346', '2015-10-23 11:14:56', 1, 1, NULL),
('s16466', '2015-10-23 16:24:43', 0, 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `OptimizerRecs`
--

CREATE TABLE IF NOT EXISTS `OptimizerRecs` (
  `TimeStamp` varchar(19) DEFAULT NULL,
  `uID_admin` varchar(5) DEFAULT NULL,
  `uID_student` varchar(7) DEFAULT NULL,
  `CourseID` varchar(6) DEFAULT NULL,
  `ShadowMode` int(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `OptimizerRecs`
--

INSERT INTO `OptimizerRecs` (`TimeStamp`, `uID_admin`, `uID_student`, `CourseID`, `ShadowMode`) VALUES
('2015-10-23 11:14:56', 'a1346', 's56878', 'CS7641', 0),
('2015-10-23 11:14:56', 'a1346', 's56878', 'CS6300', 0),
('2015-10-23 11:14:56', 'a1346', 's47568', 'CS7641', 0),
('2015-10-23 11:14:56', 'a1346', 's71600', 'CS7641', 0),
('2015-10-23 11:14:56', 'a1346', 's95632', 'CS7641', 0),
('2015-10-23 11:14:56', 'a1346', 's119664', 'CS7641', 0);

-- --------------------------------------------------------

--
-- Table structure for table `Professor`
--

CREATE TABLE IF NOT EXISTS `Professor` (
  `StaffID` varchar(5) DEFAULT NULL,
  `Name` varchar(16) DEFAULT NULL,
  `AvailNextTerm` int(1) DEFAULT NULL,
  `ProfCompetencies` varchar(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Professor`
--

INSERT INTO `Professor` (`StaffID`, `Name`, `AvailNextTerm`, `ProfCompetencies`) VALUES
('p1677', 'Nelle Brownlow', 1, '1,3,7,5,11,13,14'),
('p4657', 'Lynelle Jett', 1, '2,4,6,8,10,12,16'),
('p4658', 'Daniell Jarnigan', 1, '2,5,9,15,17,18'),
('p4659', 'Page Gonzales', 0, '1,3,7,5,11,13,15'),
('p4660', 'Donnie Rote', 1, '2,4,6,8,10,12,17'),
('p4661', 'Nigel Pisani', 1, '2,5,9,15,17,19'),
('p4662', 'Quintin Mapp', 1, '1,3,7,5,11,13,16'),
('p4663', 'Assunta Kirkley', 1, '2,4,6,8,10,12,18'),
('p4664', 'Kyoko Spenser', 0, '2,5,9,15,17,20'),
('p4665', 'Aimee Pugh', 1, '1,3,7,5,11,13,17'),
('p4666', 'Terrell Westphal', 1, '2,4,6,8,10,12,19'),
('p4667', 'Marlo Warr', 0, '2,5,9,15,17,21'),
('p4668', 'Corliss Myers', 1, '1,3,7,5,11,13,18'),
('p4669', 'Thu Stefani', 0, '2,4,6,8,10,12,20'),
('p4670', 'Marla Lindeman', 1, '2,5,9,15,17,22'),
('p4671', 'Louetta Drayer', 1, '1,3,7,5,11,13,19'),
('p4672', 'Loni Poucher', 1, '2,4,6,8,10,12,21'),
('p4673', 'Jammie More', 1, '2,5,9,15,17,22'),
('p4674', 'Summer Bashore', 1, '1,3,7,5,11,13,20'),
('p4675', 'Ann Russum', 0, '2,4,6,8,10,12,22');

-- --------------------------------------------------------

--
-- Table structure for table `Student`
--

CREATE TABLE IF NOT EXISTS `Student` (
  `uID` varchar(8) DEFAULT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `CreditsCompleted` int(2) DEFAULT NULL,
  `CoursesCompleted` int(1) DEFAULT NULL,
  `Password` varchar(8) DEFAULT NULL,
  `NumCoursesDesired` int(1) DEFAULT NULL,
  `Desired Courses` varchar(22) DEFAULT NULL,
  `CompletedCourses` varchar(22) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Student`
--

INSERT INTO `Student` (`uID`, `Name`, `CreditsCompleted`, `CoursesCompleted`, `Password`, `NumCoursesDesired`, `Desired Courses`, `CompletedCourses`) VALUES
('s58689', 'Stephine Galasso', 6, 2, 'stallion', 1, '9,13,3,7,5,8,6', '18,15'),
('s36577', 'Tyree Crippen', 12, 4, 'abc123', 2, '12,1,4,16,8,5', '18,9'),
('s56878', 'Emerita Balli', 15, 5, 'mustang', 1, '4,16,9,13,17', '14,3,11,15,10'),
('s23536', 'Marcela Liddle', 18, 6, 'apple', 2, '4,16,9,13', '2,15,5,10,17,8'),
('s47568', 'Allyson Bader', 3, 1, 'pear', 3, '4,16,12,1,18,14,9,17,8', '11'),
('s71600', 'Scarlet Kinghorn', 6, 2, 'mango', 2, '4,16,12,1,8,5,14,11', '15,2'),
('s95632', 'Cheri Brennen', 21, 7, 'abc124', 1, '9,13,12', '10,18,8,4,2,5,15'),
('s119664', 'Marina Brassard', 24, 8, 'mustang', 1, '9,13', '14,11,10,4,15,18,12,17'),
('s143696', 'Darcy Michaels', 6, 2, 'apple', 1, '12,1,3,7,5,10,11,6', '8,9'),
('s167728', 'Kaitlin Meece', 3, 1, 'pear', 2, '9,13,12,1,18,10,17,3,6', '8,15'),
('s191760', 'Iesha Ridder', 15, 5, 'mango', 2, '9,13,12,1,2', '10,18,17,14,4'),
('s215792', 'Santa Rosser', 6, 2, 'abc125', 1, '9,13,3,7,5,8,6,12', '18,15'),
('s239824', 'Rona Zachary', 12, 4, 'mustang', 2, '12,1,4,16,8,5', '18,9'),
('s263856', 'Orpha Ting', 15, 5, 'apple', 1, '4,16,9,13,17', '14,3,11,15,10'),
('s287888', 'Orlando Spellman', 18, 6, 'pear', 1, '4,16,9,13', '2,15,5,10,17,8'),
('s311920', 'Donnell Tebbs', 3, 1, 'mango', 2, '4,16,12,1,18,14,9,17,8', '11'),
('s335952', 'Gertude Raschke', 6, 2, 'abc126', 1, '4,16,12,1,8,5,14,11', '15,2'),
('s359984', 'Tanya Lore', 21, 7, 'mustang', 1, '9,13,12', '10,18,8,4,2,5,15'),
('s384016', 'Lavon Slaymaker', 24, 8, 'apple', 1, '9,13', '14,11,10,4,15,18,12,17'),
('s408048', 'Gracie Eisert', 6, 2, 'pear', 2, '12,1,3,7,5,10,11,6', '8,9'),
('s432080', 'Jessia Chism', 3, 1, 'mango', 1, '9,13,12,1,18,10,17,3,6', '8,15'),
('s456112', 'Marquetta Kline', 15, 5, 'abc127', 2, '9,13,12,1,2', '10,18,17,14,4'),
('s480144', 'Garfield Hagood', 6, 2, 'mustang', 1, '9,13,3,7,5,8,6,12', '18,15'),
('s504176', 'Nellie Bradford', 12, 4, 'apple', 2, '12,1,4,16,8,5', '18,9'),
('s528208', 'Malena Sigler', 15, 5, 'pear', 3, '4,16,9,13,17', '14,3,11,15,10'),
('s552240', 'Nickole Rahman', 18, 6, 'mango', 2, '4,16,9,13', '2,15,5,10,17,8'),
('s576272', 'Edwardo Kyger', 3, 1, 'abc128', 1, '4,16,12,1,18,14,9,17,8', '11'),
('s600304', 'Roosevelt Sperling', 6, 2, 'mustang', 1, '4,16,12,1,8,5,14,11', '15,2'),
('s624336', 'Leonarda Lax', 21, 7, 'apple', 1, '9,13,12', '10,18,8,4,2,5,15'),
('s648368', 'Hertha Holbert', 24, 8, 'pear', 2, '9,13', '14,11,10,4,15,18,12,17'),
('s672400', 'Aleen Garman', 6, 2, 'mango', 2, '12,1,3,7,5,10,11,6', '8,9'),
('s696432', 'Cary Delange', 3, 1, 'abc129', 1, '9,13,12,1,18,10,17,3,6', '8,15'),
('s720464', 'Genevie Floyd', 15, 5, 'mustang', 2, '9,13,12,1,2', '10,18,17,14,4'),
('s744496', 'Colin Friley', 6, 2, 'apple', 1, '9,13,3,7,5,8,6,12', '18,15'),
('s768528', 'Harmony Stone', 12, 4, 'pear', 1, '12,1,4,16,8,5', '18,9'),
('s792560', 'Davis Riggio', 15, 5, 'mango', 2, '4,16,9,13,17', '14,3,11,15,10'),
('s816592', 'Coletta Tregre', 18, 6, 'abc130', 1, '4,16,9,13', '2,15,5,10,17,8'),
('s840624', 'Michelle Warnock', 3, 1, 'mustang', 1, '4,16,12,1,18,14,9,17,8', '11'),
('s864656', 'Herma Serafino', 6, 2, 'apple', 1, '4,16,12,1,8,5,14,11', '15,2'),
('s888688', 'Candelaria Smelley', 21, 7, 'pear', 2, '9,13,12', '10,18,8,4,2,5,15'),
('s912720', 'Valery Lietz', 24, 8, 'mango', 1, '9,13', '14,11,10,4,15,18,12,17'),
('s936752', 'Tonda Garmon', 6, 2, 'abc131', 2, '12,1,3,7,5,10,11,6', '8,9'),
('s960784', 'Rudolf Voges', 3, 1, 'mustang', 1, '9,13,12,1,18,10,17,3,6', '8,15'),
('s984816', 'Josie Mastrangelo', 15, 5, 'apple', 2, '9,13,12,1,2', '10,18,17,14,4'),
('s1008848', 'Juli Lauver', 6, 2, 'pear', 3, '9,13,3,7,5,8,6,12', '18,15'),
('s1032880', 'Lora Biondi', 12, 4, 'mango', 2, '12,1,4,16,8,5', '18,9'),
('s1056912', 'Johnathan Witmer', 15, 5, 'abc132', 1, '4,16,9,13,17', '14,3,11,15,10'),
('s1080944', 'Newton Loggins', 18, 6, 'mustang', 1, '4,16,9,13', '2,15,5,10,17,8'),
('s1104976', 'Rolande Fernando', 3, 1, 'apple', 1, '4,16,12,1,18,14,9,17,8', '11'),
('s1129008', 'Mae Amarante', 6, 2, 'abc133', 2, '4,16,12,1,8,5,14,11', '15,2'),
('s1153040', 'Boris Waits', 21, 7, 'mustang', 2, '9,13,12', '10,18,8,4,2,5,15'),
('s1177072', 'Leia Deaton', 24, 8, 'apple', 1, '9,13', '14,11,10,4,15,18,12,17'),
('s1201104', 'Maira Dedman', 6, 2, 'abc134', 2, '12,1,3,7,5,10,11,6', '8,9'),
('s1225136', 'Ken Torkelson', 3, 1, 'mustang', 1, '9,13,12,1,18,10,17,3,6', '8,15'),
('s1249168', 'Zoe Mccright', 15, 5, 'apple', 1, '9,13,12,1,2', '10,18,17,14,4'),
('s1273200', 'Somer Riedel', 6, 2, 'abc135', 2, '9,13,3,7,5,8,6,12', '18,15'),
('s1297232', 'Katharyn Nace', 12, 4, 'mustang', 1, '12,1,4,16,8,5', '18,9'),
('s1321264', 'Darrell Bess', 15, 5, 'apple', 1, '4,16,9,13,17', '14,3,11,15,10'),
('s1345296', 'Marybeth Harshberger', 18, 6, 'abc136', 1, '4,16,9,13', '2,15,5,10,17,8'),
('s1369328', 'Jennie Weigand', 3, 1, 'mustang', 2, '4,16,12,1,18,14,9,17,8', '11'),
('s1393360', 'Louise Joachim', 6, 2, 'apple', 1, '4,16,12,1,8,5,14,11', '15,2'),
('s1417392', 'Consuela Mikels', 21, 7, 'abc137', 2, '9,13,12', '10,18,8,4,2,5,15'),
('s1441424', 'Reatha Police', 24, 8, 'mustang', 1, '9,13', '14,11,10,4,15,18,12,17'),
('s1465456', 'Marcell Zalenski', 6, 2, 'apple', 2, '12,1,3,7,5,10,11,6', '8,9'),
('s1489488', 'Detra Manier', 3, 1, 'abc138', 3, '9,13,12,1,18,10,17,3,6', '8,15'),
('s1513520', 'Zachariah Defalco', 15, 5, 'mustang', 2, '9,13,12,1,2', '10,18,17,14,4'),
('s1537552', 'Sandi Abrego', 6, 2, 'apple', 1, '9,13,3,7,5,8,6,12', '18,15'),
('s1561584', 'Nilsa Etsitty', 12, 4, 'abc139', 1, '12,1,4,16,8,5', '18,9'),
('s1585616', 'Rossie Devaul', 15, 5, 'mustang', 1, '4,16,9,13,17', '14,3,11,15,10'),
('s1609648', 'Elias Goyette', 18, 6, 'apple', 2, '4,16,9,13', '2,15,5,10,17,8'),
('s1633680', 'Amos Madison', 3, 1, 'abc140', 2, '4,16,12,1,18,14,9,17,8', '11'),
('s1657712', 'Cori Jenning', 6, 2, 'mustang', 1, '4,16,12,1,8,5,14,11', '15,2'),
('s1681744', 'Maryrose Awad', 21, 7, 'apple', 2, '9,13,12', '10,18,8,4,2,5,15'),
('s1705776', 'Apolonia Ellinger', 24, 8, 'abc141', 1, '9,13', '14,11,10,4,15,18,12,17'),
('s1729808', 'Leonarda Corkery', 6, 2, 'mustang', 1, '12,1,3,7,5,10,11,6', '8,9'),
('s1753840', 'Abbey Moorehead', 3, 1, 'apple', 2, '9,13,12,1,18,10,17,3,6', '8,15'),
('s1777872', 'Wanita Poll', 15, 5, 'abc142', 1, '9,13,12,1,2', '10,18,17,14,4'),
('s1801904', 'Yang Naber', 6, 2, 'mustang', 1, '9,13,3,7,5,8,6,12', '18,15'),
('s1825936', 'Oralee South', 12, 4, 'apple', 1, '12,1,4,16,8,5', '18,9'),
('s1849968', 'Diego Callaham', 15, 5, 'abc143', 2, '4,16,9,13,17', '14,3,11,15,10'),
('s1874000', 'Milan Carranco', 18, 6, 'mustang', 1, '4,16,9,13', '2,15,5,10,17,8'),
('s1898032', 'Lawana Parikh', 3, 1, 'apple', 2, '4,16,12,1,18,14,9,17,8', '11'),
('s1922064', 'Cinthia Weathersby', 6, 2, 'abc144', 1, '4,16,12,1,8,5,14,11', '15,2'),
('s1946096', 'Lon Dunkin', 21, 7, 'mustang', 2, '9,13,12', '10,18,8,4,2,5,15'),
('s1970128', 'Stevie Ybanez', 24, 8, 'apple', 2, '9,13', '14,11,10,4,15,18,12,17'),
('s1994160', 'Shella Burkitt', 6, 2, 'abc145', 2, '12,1,3,7,5,10,11,6', '8,9'),
('s2018192', 'Lannie Mcclane', 3, 1, 'mustang', 1, '9,13,12,1,18,10,17,3,6', '8,15'),
('s2042224', 'Althea Jurgensen', 15, 5, 'apple', 1, '9,13,12,1,2', '10,18,17,14,4'),
('s2066256', 'Antony Yonts', 6, 2, 'abc146', 1, '9,13,3,7,5,8,6,12', '18,15'),
('s2090288', 'Jacinto Zuccaro', 12, 4, 'mustang', 2, '12,1,4,16,8,5', '18,9'),
('s2114320', 'Geraldine Josephson', 15, 5, 'apple', 2, '4,16,9,13,17', '14,3,11,15,10'),
('s2138352', 'Louis Fleischmann', 18, 6, 'abc147', 1, '4,16,9,13', '2,15,5,10,17,8'),
('s2162384', 'Betty Dollar', 3, 1, 'mustang', 2, '4,16,12,1,18,14,9,17,8', '11'),
('s2186416', 'Britteny Pewitt', 6, 2, 'apple', 1, '4,16,12,1,8,5,14,11', '15,2'),
('s2210448', 'Leslee Powe', 21, 7, 'abc148', 1, '9,13,12', '10,18,8,4,2,5,15'),
('s2234480', 'Gregoria Eisenhart', 24, 8, 'mustang', 2, '9,13', '14,11,10,4,15,18,12,17'),
('s2258512', 'Yong Behan', 6, 2, 'apple', 1, '12,1,3,7,5,10,11,6', '8,9'),
('s2282544', 'Pamila Grosse', 3, 1, 'abc149', 1, '9,13,12,1,18,10,17,3,6', '8,15'),
('s2306576', 'Mercy Bjorklund', 15, 5, 'mustang', 1, '9,13,12,1,2', '10,18,17,14,4'),
('s2330608', 'Tom Chen', 3, 1, 'apple', 2, '9,13,12,1,18,10,17,3,6', '8,15');

-- --------------------------------------------------------

--
-- Table structure for table `TA`
--

CREATE TABLE IF NOT EXISTS `TA` (
  `StaffID` varchar(5) DEFAULT NULL,
  `Name` varchar(17) DEFAULT NULL,
  `AvailNextTerm` int(1) DEFAULT NULL,
  `TACompetencies` varchar(8) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `TA`
--

INSERT INTO `TA` (`StaffID`, `Name`, `AvailNextTerm`, `TACompetencies`) VALUES
('t5768', 'Cecily Seabaugh', 1, '1,3,7'),
('t5769', 'Tana Luechtefeld', 1, '2,4,6'),
('t5770', 'Rasheeda Leong', 1, '5,8,9'),
('t5771', 'Rosaura Ronquillo', 0, '10,12,14'),
('t5772', 'Keven Huff', 0, '11,15'),
('t5773', 'Marlen Beverly', 1, '16,17'),
('t5774', 'Tula Reinsch', 1, '15,18'),
('t5775', 'Sheila Flippo', 0, '1,3,7'),
('t5776', 'Kerry Epling', 0, '2,4,6'),
('t5777', 'Jorge Tinney', 0, '5,8,9'),
('t5778', 'Joana Garrow', 1, '10,12,14'),
('t5779', 'Lora Locust', 1, '11,15'),
('t5780', 'Doris Caley', 0, '16,17'),
('t5781', 'Pedro Westfield', 1, '15,18'),
('t5782', 'Janae Thong', 0, '1,3,7'),
('t5783', 'Princess Cobbs', 0, '2,4,6'),
('t5784', 'Dave Salley', 1, '5,8,9'),
('t5785', 'Jay Whittaker', 1, '10,12,14'),
('t5786', 'Marine Patlan', 1, '11,15'),
('t5787', 'Gordon Sayegh', 0, '16,17'),
('t5788', 'Stuart Ebinger', 1, '15,18'),
('t5789', 'Leida Iannuzzi', 0, '1,3,7'),
('t5790', 'Lane Gertsch', 1, '2,4,6'),
('t5791', 'Alita Casias', 0, '5,8,9'),
('t5792', 'Adriana Emigh', 1, '10,12,14'),
('t5793', 'Randy Seay', 0, '11,15'),
('t5794', 'Peggie Ellefson', 1, '16,17'),
('t5795', 'Rudy Manson', 1, '15,18'),
('t5796', 'My Schlueter', 0, '1,3,7'),
('t5797', 'Martina Wininger', 0, '2,4,6'),
('t5798', 'Lamont Mire', 0, '5,8,9'),
('t5799', 'Ligia Kittelson', 1, '10,12,14'),
('t5800', 'Sadye Mericle', 1, '11,15'),
('t5801', 'Myesha Mikus', 0, '16,17'),
('t5802', 'Cherri Utz', 1, '15,18'),
('t5803', 'Long Leitner', 0, '1,3,7'),
('t5804', 'Kirstie Mota', 0, '2,4,6'),
('t5805', 'Clarita Litten', 1, '5,8,9'),
('t5806', 'Lorita Ollie', 0, '10,12,14'),
('t5807', 'Hilario Doig', 1, '11,15'),
('t5808', 'Son Schaffner', 0, '16,17'),
('t5809', 'Lesli Nicolosi', 0, '15,18'),
('t5810', 'Aurelia Eder', 1, '1,3,7'),
('t5811', 'Tempie Goering', 1, '2,4,6'),
('t5812', 'Whitley Villagran', 1, '5,8,9'),
('t5813', 'Dorthy Murray', 0, '10,12,14'),
('t5814', 'Yi Tart', 0, '11,15'),
('t5815', 'Norine Lebow', 0, '16,17'),
('t5816', 'Harriett Haymaker', 1, '15,18'),
('t5817', 'Shirlene Fenster', 1, '10,12,14');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
