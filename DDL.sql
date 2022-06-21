-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema clubmservice
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema clubmservice
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `clubmservice` DEFAULT CHARACTER SET utf8 ;
USE `clubmservice` ;

-- -----------------------------------------------------
-- Table `clubmservice`.`Bonus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `clubmservice`.`Bonus` (
  `id` BINARY(16) NOT NULL,
  `first` TINYINT NOT NULL,
  `Review_id` BINARY(16) NOT NULL,
  `Place_id` BINARY(16) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `place_first` (`Place_id` ASC, `first` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clubmservice`.`Content`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `clubmservice`.`Content` (
  `id` BINARY(16) NOT NULL,
  `text` INT NOT NULL,
  `photo` INT NOT NULL,
  `Review_id` BINARY(16) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `review` (`Review_id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clubmservice`.`User_Point`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `clubmservice`.`User_Point` (
  `id` BINARY(16) NOT NULL,
  `point` INT NOT NULL,
  `User_id` BINARY(16) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user` (`User_id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clubmservice`.`Point_History`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `clubmservice`.`Point_History` (
  `id` BINARY(16) NOT NULL,
  `point` INT NOT NULL,
  `date` TIMESTAMP NOT NULL,
  `User_id` BINARY(16) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user` (`User_id` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
