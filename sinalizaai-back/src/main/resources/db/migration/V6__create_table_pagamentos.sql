CREATE TABLE `pagamentos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pedido_id` int NOT NULL,
  `plataforma` varchar(50) NOT NULL,
  `transacao_id` varchar(255) NOT NULL,
  `token_cartao` varchar(255) DEFAULT NULL,
  `bandeira` varchar(20) DEFAULT NULL,
  `final_cartao` varchar(4) DEFAULT NULL,
  `status` enum('pendente','aprovado','recusado','estornado') NOT NULL DEFAULT 'pendente',
  `valor` decimal(10,2) NOT NULL,
  `criado_em` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `pedido_id` (`pedido_id`),
  CONSTRAINT `pagamentos_ibfk_1` FOREIGN KEY (`pedido_id`) REFERENCES `pedidos` (`id`)
);