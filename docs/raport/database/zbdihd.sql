-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 02 Maj 2015, 16:43
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `zbdihd`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `fuel_leakage_detections`
--

CREATE TABLE IF NOT EXISTS `fuel_leakage_detections` (
  `fld_id` int(11) NOT NULL AUTO_INCREMENT,
  `fld_tank_id` int(11) NOT NULL,
  `fld_begin_timestamp` datetime NOT NULL,
  `fld_end_timestamp` datetime NOT NULL,
  `fld_tank_interval_netto_volume` decimal(20,15) NOT NULL,
  `fld_tank_interval_gross_volume` decimal(20,15) NOT NULL,
  `fld_nozzles_interval_netto_volume` decimal(20,15) NOT NULL,
  `fld_nozzles_interval_gross_volume` decimal(20,15) NOT NULL,
  `fld_differential_netto_volume` decimal(20,15) NOT NULL,
  `fld_differential_gross_volume` decimal(20,15) NOT NULL,
  `fld_detected_fuel_leakage` tinyint(1) NOT NULL,
  PRIMARY KEY (`fld_id`),
  KEY `fld_tank_id` (`fld_tank_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci AUTO_INCREMENT=352 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `fuel_types`
--

CREATE TABLE IF NOT EXISTS `fuel_types` (
  `ft_id` int(11) NOT NULL AUTO_INCREMENT,
  `ft_name` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`ft_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci AUTO_INCREMENT=4 ;

--
-- Zrzut danych tabeli `fuel_types`
--

INSERT INTO `fuel_types` (`ft_id`, `ft_name`) VALUES
(0, 'PB95'),
(1, 'PB98'),
(2, 'ON');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `nozzles`
--

CREATE TABLE IF NOT EXISTS `nozzles` (
  `noozle_id` int(11) NOT NULL AUTO_INCREMENT,
  `noozle_tank_id` int(11) NOT NULL,
  `noozle_calibration_coefficient` decimal(4,3) NOT NULL,
  PRIMARY KEY (`noozle_id`),
  KEY `noozle_tank_id` (`noozle_tank_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci AUTO_INCREMENT=12 ;

--
-- Zrzut danych tabeli `nozzles`
--

INSERT INTO `nozzles` (`noozle_id`, `noozle_tank_id`, `noozle_calibration_coefficient`) VALUES
(0, 0, '0.999'),
(1, 1, '0.999'),
(2, 2, '0.999'),
(3, 0, '0.999'),
(4, 1, '0.999'),
(5, 2, '0.999'),
(6, 0, '0.999'),
(7, 1, '0.999'),
(8, 2, '0.999'),
(9, 0, '0.999'),
(10, 1, '0.999'),
(11, 2, '0.999');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `nozzles_measures`
--

CREATE TABLE IF NOT EXISTS `nozzles_measures` (
  `nm_id` int(11) NOT NULL AUTO_INCREMENT,
  `nm_nozzle_id` int(11) NOT NULL,
  `nm_tank_id` int(11) NOT NULL,
  `nm_begin_timestamp` datetime NOT NULL,
  `nm_end_timestamp` datetime NOT NULL,
  `nm_raw_wolume` decimal(20,15) NOT NULL,
  `nm_gross_volume` decimal(20,15) NOT NULL,
  `nm_netto_volume` decimal(20,15) NOT NULL,
  `nm_temperature` decimal(20,15) NOT NULL,
  PRIMARY KEY (`nm_id`),
  KEY `nm_nozzle_id` (`nm_nozzle_id`,`nm_tank_id`),
  KEY `nm_tank_id` (`nm_tank_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci AUTO_INCREMENT=121 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tanks`
--

CREATE TABLE IF NOT EXISTS `tanks` (
  `tank_id` int(11) NOT NULL AUTO_INCREMENT,
  `tank_fuel_type_id` int(11) NOT NULL,
  `tank_min_volume` decimal(5,2) NOT NULL,
  `tank_max_volume` decimal(5,2) NOT NULL,
  PRIMARY KEY (`tank_id`),
  KEY `tank_fuel_type_id` (`tank_fuel_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci AUTO_INCREMENT=5 ;

--
-- Zrzut danych tabeli `tanks`
--

INSERT INTO `tanks` (`tank_id`, `tank_fuel_type_id`, `tank_min_volume`, `tank_max_volume`) VALUES
(0, 0, '10.00', '100.00'),
(1, 1, '10.00', '100.00'),
(2, 2, '10.00', '100.00');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tank_measures`
--

CREATE TABLE IF NOT EXISTS `tank_measures` (
  `tm_id` int(11) NOT NULL AUTO_INCREMENT,
  `tm_tank_id` int(11) NOT NULL,
  `tm_timestamp` timestamp NOT NULL,
  `tm_gross_volume` decimal(20,15) NOT NULL,
  `tm_netto_volume` decimal(20,15) NOT NULL,
  `tm_water_wolume` decimal(20,15) NOT NULL,
  `tm_temperature` decimal(20,15) NOT NULL,
  PRIMARY KEY (`tm_id`),
  KEY `tm_tank_id` (`tm_tank_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci AUTO_INCREMENT=292 ;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `fuel_leakage_detections`
--
ALTER TABLE `fuel_leakage_detections`
  ADD CONSTRAINT `fld_tank_id` FOREIGN KEY (`fld_tank_id`) REFERENCES `tanks` (`tank_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `nozzles`
--
ALTER TABLE `nozzles`
  ADD CONSTRAINT `nozzle_tank_fk` FOREIGN KEY (`noozle_tank_id`) REFERENCES `tanks` (`tank_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `nozzles_measures`
--
ALTER TABLE `nozzles_measures`
  ADD CONSTRAINT `nm_nozzle_fk` FOREIGN KEY (`nm_nozzle_id`) REFERENCES `nozzles` (`noozle_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `nm_tank_id` FOREIGN KEY (`nm_tank_id`) REFERENCES `tanks` (`tank_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `tanks`
--
ALTER TABLE `tanks`
  ADD CONSTRAINT `tank_fuel_fk` FOREIGN KEY (`tank_fuel_type_id`) REFERENCES `fuel_types` (`ft_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ograniczenia dla tabeli `tank_measures`
--
ALTER TABLE `tank_measures`
  ADD CONSTRAINT `tm_tank_fk` FOREIGN KEY (`tm_tank_id`) REFERENCES `tanks` (`tank_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
