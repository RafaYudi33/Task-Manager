CREATE TABLE IF NOT EXISTS `tb_tasks` (
  `id` binary(16) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_at` datetime(6) DEFAULT NULL,
  `id_user` binary(16) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `start_at` datetime(6) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
);