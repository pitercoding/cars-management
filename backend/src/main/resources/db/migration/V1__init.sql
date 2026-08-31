-- Baseline migration. This reproduces, table for table and constraint for
-- constraint, the schema Hibernate's ddl-auto=update had already been
-- producing (captured via SHOW CREATE TABLE against a live instance).
-- On a database that already has these tables, Flyway's
-- baseline-on-migrate marks this version as already applied instead of
-- re-running it. On a fresh/empty database (new clone, CI), this actually
-- creates the schema from scratch.

CREATE TABLE `brand` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `tax_identification_number` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsupxqibu9ncverkq3k4fl5wtn` (`tax_identification_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `owner` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_of_birth` date NOT NULL,
  `drivers_license` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdb3dfw61rbp2e6y57xi1j19y3` (`drivers_license`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `accessory` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKh5v8uk2i8yx0dfplr749lto7o` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `car` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `manufacture_year` int NOT NULL,
  `model` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `brand_id` bigint NOT NULL,
  `owner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKifw05imckjjv1necyu59fg5rj` (`owner_id`),
  KEY `FKj1mws2ruu9q6k2sa4pwlxthxn` (`brand_id`),
  CONSTRAINT `FK4ec59hmbtvq3tjp1xcluis1j6` FOREIGN KEY (`owner_id`) REFERENCES `owner` (`id`),
  CONSTRAINT `FKj1mws2ruu9q6k2sa4pwlxthxn` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `car_accessory` (
  `car_id` bigint NOT NULL,
  `accessory_id` bigint NOT NULL,
  KEY `FKgjjf4hn6utmae40pa8vgcqu1d` (`accessory_id`),
  KEY `FKgqpmd4dp4q2562wg1hwnon823` (`car_id`),
  CONSTRAINT `FKgjjf4hn6utmae40pa8vgcqu1d` FOREIGN KEY (`accessory_id`) REFERENCES `accessory` (`id`),
  CONSTRAINT `FKgqpmd4dp4q2562wg1hwnon823` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
