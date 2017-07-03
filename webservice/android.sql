-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 09-03-2017 a las 10:24:15
-- Versión del servidor: 5.5.54-0+deb8u1
-- Versión de PHP: 5.6.30-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `android`
--
CREATE DATABASE IF NOT EXISTS `android` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `android`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `products`
--

CREATE TABLE IF NOT EXISTS `products` (
`pid` int(11) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish2_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_spanish2_ci,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `products`
--

INSERT INTO `products` (`pid`, `name`, `price`, `description`, `created_at`, `updated_at`) VALUES
(1, 'papas', 12.50, 'A que no puedes comer solo una', '2016-03-30 00:59:14', '0000-00-00 00:00:00'),
(2, 'galletas', 6.70, 'galletas arcoiris', '2016-03-30 14:33:12', '0000-00-00 00:00:00'),
(3, 'chicles', 5.40, 'Chiclets adams', '2016-06-29 14:48:06', '0000-00-00 00:00:00'),
(4, 'coca cola', 12.50, 'refresco de cola', '2016-06-29 14:56:56', '0000-00-00 00:00:00'),
(5, 'nueces', 5.90, 'las nueces son ricas', '2016-07-01 01:52:10', '0000-00-00 00:00:00'),
(6, 'wii', 989.99, 'la wii esta chida', '2016-07-02 18:30:44', '0000-00-00 00:00:00');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `products`
--
ALTER TABLE `products`
 ADD PRIMARY KEY (`pid`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `products`
--
ALTER TABLE `products`
MODIFY `pid` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
