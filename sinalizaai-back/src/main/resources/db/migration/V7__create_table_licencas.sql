CREATE TABLE `licencas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cliente_id` int NOT NULL,
  `pedido_id` int NOT NULL,
  `chave` varchar(255) NOT NULL,
  `ativo` tinyint(1) DEFAULT '1',
  `data_inicio` datetime DEFAULT NULL,
  `data_expiracao` datetime DEFAULT NULL,
  `criado_em` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `chave` (`chave`),
  KEY `cliente_id` (`cliente_id`),
  KEY `pedido_id` (`pedido_id`),
  CONSTRAINT `licencas_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`),
  CONSTRAINT `licencas_ibfk_2` FOREIGN KEY (`pedido_id`) REFERENCES `pedidos` (`id`)
);