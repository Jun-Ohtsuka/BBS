SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `bbs` DEFAULT CHARACTER SET utf8 ;
USE `bbs` ;

-- -----------------------------------------------------
-- Table `bbs`.`branchs`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bbs`.`branchs` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `insert_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  `update_date` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bbs`.`comments`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bbs`.`comments` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `threads_id` INT(11) NOT NULL ,
  `text` VARCHAR(500) NOT NULL ,
  `users_id` INT(11) NOT NULL ,
  `insert_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  `update_date` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bbs`.`positions`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bbs`.`positions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL DEFAULT NULL ,
  `insert_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  `update_date` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bbs`.`threads`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bbs`.`threads` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(50) NOT NULL ,
  `text` VARCHAR(1000) NOT NULL ,
  `category` VARCHAR(10) NOT NULL ,
  `users_id` INT(11) NOT NULL ,
  `insert_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  `update_date` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bbs`.`users`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bbs`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `account` VARCHAR(20) NOT NULL ,
  `password` VARCHAR(255) NOT NULL ,
  `name` VARCHAR(45) NULL DEFAULT NULL ,
  `branchs_id` INT(11) NULL DEFAULT NULL ,
  `positions_id` INT(11) NULL DEFAULT NULL ,
  `freeze` TINYINT(1) NOT NULL DEFAULT '0' ,
  `insert_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  `update_date` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `account_UNIQUE` (`account` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `bbs` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
