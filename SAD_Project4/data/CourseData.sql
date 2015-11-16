-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 15, 2015 at 05:50 PM
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
  `uID` text NOT NULL,
  `Name` text NOT NULL,
  `Password` text NOT NULL,
  `AvailNextTerm` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Admin`
--

INSERT INTO `Admin` (`uID`, `Name`, `Password`, `AvailNextTerm`) VALUES
('a1345', 'Agent Smith', 'abc123', '1'),
('a1346', 'Agent Jones', 'abc456', '1'),
('a1347', 'Agent Roberts', 'abc789', '1');

-- --------------------------------------------------------

--
-- Table structure for table `AdminClassLimit`
--

CREATE TABLE IF NOT EXISTS `AdminClassLimit` (
  `uID` text NOT NULL,
  `SemesterID` text NOT NULL,
  `CourseID` text NOT NULL,
  `CourseLimit` smallint(6) NOT NULL,
  `ShadowMode` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  `uID` text NOT NULL,
  `CourseID` text NOT NULL,
  `SemesterID` text NOT NULL,
  `StaffID` text NOT NULL,
  `ProfessorOrTA` text NOT NULL,
  `ShadowMode` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `AdminStaffAllocation`
--

INSERT INTO `AdminStaffAllocation` (`uID`, `CourseID`, `SemesterID`, `StaffID`, `ProfessorOrTA`, `ShadowMode`) VALUES
('a1345', 'CS6210', '2015SP', 'p4657', 'P', 0),
('a1345', 'CS6250', '2015SP', 't5768', 'T', 0);

-- --------------------------------------------------------

--
-- Table structure for table `Course`
--

CREATE TABLE IF NOT EXISTS `Course` (
  `CourseID` text NOT NULL,
  `Description` text NOT NULL,
  `CourseLimit` tinyint(4) NOT NULL,
  `PreRequisite` text NOT NULL,
  `CoRequisite` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Course`
--

INSERT INTO `Course` (`CourseID`, `Description`, `CourseLimit`, `PreRequisite`, `CoRequisite`) VALUES
('CS6210', 'Advanced Operating Systems', 50, 'CS8803-002', '-1'),
('CS6250', 'Computer Networks', 60, '-1', '-1'),
('CS6300', 'Software Development Process', 50, '-1', '-1'),
('CS7641', 'Machine Learning', 50, '-1', '-1'),
('CS6290', 'High Performance Computer Architecture', 60, '-1', '-1'),
('CS6310', 'Software Architecture and Design', 40, '-1', '-1'),
('CS6440', 'Intro to Health Informatics', 30, 'CS6300', '-1'),
('CS6505', 'Computability, Complexity and Algorithms', 70, '-1', '-1'),
('CS7637', 'Knowledge-Based Artificial Intelligence, Cognitive Systems', 50, '-1', '-1'),
('CS4495', 'Computer Vision', 30, '-1', '-1'),
('CS6475', 'Computational Photography', 50, '-1', '-1'),
('CS8803-002', 'Introduction to Operating Systems', 60, '-1', '-1'),
('CS8803-001', 'Artificial Intelligence for Robotics', 70, 'CS7637', '-1'),
('CS6035', 'Introduction to Information Security', 40, '-1', '-1'),
('CSE6220', 'High-Performance Computing', 60, '-1', '-1'),
('CS7646', 'Machine Learning for Trading', 50, 'CS7641', '-1'),
('CS8803', 'Special Topics: Reinforcement Learning', 40, '-1', '-1'),
('CSE8803', 'Special Topics: Big Data', 60, '-1', '-1');

-- --------------------------------------------------------

--
-- Table structure for table `CourseDemand`
--

CREATE TABLE IF NOT EXISTS `CourseDemand` (
  `uID` text NOT NULL,
  `TimeStamp` text NOT NULL,
  `CourseID` text NOT NULL,
  `SemesterID` text NOT NULL,
  `PriorityNum` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CourseDemand`
--

INSERT INTO `CourseDemand` (`uID`, `TimeStamp`, `CourseID`, `SemesterID`, `PriorityNum`) VALUES
('s56878', '2015-09-17 11:15:54', 'CS7641', '2015SP', 1),
('s23536', '2015-09-17 11:15:53', 'CS7641', '2015SP', 3),
('s47568', '2015-09-17 09:15:54', 'CS7641', '2015SP', 2),
('s71600', '2015-09-17 11:15:52', 'CS7641', '2015SP', 1),
('s95632', '2015-09-17 11:15:51', 'CS7641', '2015SP', 3),
('s119664', '2015-09-17 09:15:55', 'CS7641', '2015SP', 3);

-- --------------------------------------------------------

--
-- Table structure for table `LoginEvents`
--

CREATE TABLE IF NOT EXISTS `LoginEvents` (
  `uID` text NOT NULL,
  `TimeStamp` text NOT NULL,
  `Admin` tinyint(1) NOT NULL,
  `SuccessFail` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `OptimizerRecs`
--

CREATE TABLE IF NOT EXISTS `OptimizerRecs` (
  `TimeStamp` text NOT NULL,
  `uID_admin` text NOT NULL,
  `uID_student` text NOT NULL,
  `CourseID` text NOT NULL,
  `SemesterID` text NOT NULL,
  `ShadowMode` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `OptimizerRecs`
--

INSERT INTO `OptimizerRecs` (`TimeStamp`, `uID_admin`, `uID_student`, `CourseID`, `SemesterID`, `ShadowMode`) VALUES
('2015-10-23 11:14:56', 'a1346', 's56878', 'CS7641', '2015SP', 0),
('2015-10-23 11:14:56', 'a1346', 's23536', 'CS7641', '2015SP', 0),
('2015-10-23 11:14:56', 'a1346', 's47568', 'CS7641', '2015SP', 0),
('2015-10-23 11:14:56', 'a1346', 's71600', 'CS7641', '2015SP', 0),
('2015-10-23 11:14:56', 'a1346', 's95632', 'CS7641', '2015SP', 0),
('2015-10-23 11:14:56', 'a1346', 's119664', 'CS7641', '2015SP', 0);

-- --------------------------------------------------------

--
-- Table structure for table `Professor`
--

CREATE TABLE IF NOT EXISTS `Professor` (
  `StaffID` text NOT NULL,
  `Name` text NOT NULL,
  `AvailNextTerm` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Professor`
--

INSERT INTO `Professor` (`StaffID`, `Name`, `AvailNextTerm`) VALUES
('p1677', 'Tim Horthy', 1),
('p4657', 'Josef Blorian', 1),
('p4658', 'James Thomson', 1),
('p4659', 'Maria Vorsakian', 0),
('p4660', 'Tom Jones', 1),
('p4661', 'Tom Jacobs', 1),
('p4662', 'Tom Johnson', 1),
('p4663', 'Tom Bobson', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Student`
--

CREATE TABLE IF NOT EXISTS `Student` (
  `uID` text NOT NULL,
  `Name` text NOT NULL,
  `CreditsCompleted` tinyint(4) NOT NULL,
  `CoursesCompleted` tinyint(4) NOT NULL,
  `Password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Student`
--

INSERT INTO `Student` (`uID`, `Name`, `CreditsCompleted`, `CoursesCompleted`, `Password`) VALUES
('s58689', 'Joe Smith', 9, 3, 'stallion'),
('s36577', 'Bob Williams', 12, 4, 'abc123'),
('s56878', 'Mary Wang', 15, 5, 'mustang'),
('s23536', 'Tom Chen', 18, 6, 'apple'),
('s47568', 'Sam Gan', 9, 3, 'pear'),
('s71600', 'Eric Smith', 12, 4, 'mango'),
('s95632', 'Mary Wang', 15, 5, 'abc124'),
('s119664', 'Tom Chen', 18, 6, 'mustang'),
('s143696', 'Bill Keaton', 12, 4, 'apple'),
('s167728', 'Tom Keaton', 15, 5, 'pear'),
('s191760', 'Mary Wang', 9, 3, 'mango'),
('s215792', 'Tom Chen', 21, 7, 'abc125'),
('s239824', 'Tim Jones', 12, 4, 'mustang'),
('s263856', 'Eric Smith', 18, 6, 'apple'),
('s287888', 'Tom Keaton', 15, 5, 'pear'),
('s311920', 'Tom Chen', 21, 7, 'mango'),
('s335952', 'Sam Gan', 12, 4, 'abc126'),
('s359984', 'Eric Smith', 18, 6, 'mustang'),
('s384016', 'Mary Wang', 15, 5, 'apple'),
('s408048', 'Tom Keaton', 9, 3, 'pear'),
('s432080', 'Sam Gan', 15, 5, 'mango'),
('s456112', 'Eric Smith', 18, 6, 'abc127'),
('s480144', 'Mary Wang', 21, 7, 'mustang'),
('s504176', 'Tom Chen', 15, 5, 'apple'),
('s528208', 'Sam Gan', 9, 3, 'pear'),
('s552240', 'Eric Smith', 6, 2, 'mango'),
('s576272', 'Mary Wang', 15, 5, 'abc128'),
('s600304', 'Tom Keaton', 18, 6, 'mustang'),
('s624336', 'Sam Gan', 21, 7, 'apple'),
('s648368', 'Eric Smith', 24, 8, 'pear'),
('s672400', 'Mary Wang', 18, 6, 'mango'),
('s696432', 'Tom Chen', 12, 4, 'abc129'),
('s720464', 'Sam Gan', 9, 3, 'mustang'),
('s744496', 'Eric Smith', 15, 5, 'apple'),
('s768528', 'Mary Wang', 18, 6, 'pear'),
('s792560', 'Tom Chen', 21, 7, 'mango'),
('s816592', 'Sam Gan', 18, 6, 'abc130'),
('s840624', 'Eric Smith', 9, 3, 'mustang'),
('s864656', 'Mary Wang', 12, 4, 'apple'),
('s888688', 'Tom Chen', 18, 6, 'pear'),
('s912720', 'Sam Gan', 21, 7, 'mango'),
('s936752', 'Eric Smith', 15, 5, 'abc131'),
('s960784', 'Mary Wang', 12, 4, 'mustang'),
('s984816', 'Tom Chen', 27, 9, 'apple'),
('s1008848', 'Sam Gan', 6, 2, 'pear'),
('s1032880', 'Eric Smith', 12, 4, 'mango'),
('s1056912', 'Mary Wang', 15, 5, 'abc132'),
('s1080944', 'Tom Chen', 21, 7, 'mustang'),
('s1104976', 'Sam Gan', 12, 4, 'apple');

-- --------------------------------------------------------

--
-- Table structure for table `TA`
--

CREATE TABLE IF NOT EXISTS `TA` (
  `StaffID` text NOT NULL,
  `Name` text NOT NULL,
  `AvailNextTerm` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TA`
--

INSERT INTO `TA` (`StaffID`, `Name`, `AvailNextTerm`) VALUES
('t5768', 'Joe Louis', 1),
('t5769', 'Joe Bobson', 1),
('t5770', 'Joe Thomson', 1),
('t5771', 'Joe Davison', 0),
('t5772', 'Joe Johnson', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
