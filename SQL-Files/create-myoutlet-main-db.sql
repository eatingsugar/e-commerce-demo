DROP SCHEMA IF EXISTS `myoutlet-main-db`;
CREATE SCHEMA `myoutlet-main-db`;
use `myoutlet-main-db`;

SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sub_category`;
CREATE TABLE `sub_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_CATEGORY_idx` (`category_id`),
  CONSTRAINT `FK_CATEGORY` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `brand` varchar(128) DEFAULT NULL,
  `price` decimal(7, 2) DEFAULT NULL,
  `sale_price` decimal(7, 2) DEFAULT NULL,
  `sale_percent` decimal(5, 2) DEFAULT NULL,
  `active` tinyint(1) DEFAULT 1,
  `detail` text DEFAULT NULL,
  `upload_time` datetime,
  `sub_category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_SUBCATEGORY_idx` (`sub_category_id`),
  CONSTRAINT `FK_SUBCATEGORY` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `images`;
CREATE TABLE `images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img` longblob,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_PRODUCT_idx` (`product_id`),
  CONSTRAINT `FK_PRODUCT` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


SET FOREIGN_KEY_CHECKS = 1;