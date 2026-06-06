CREATE TABLE `clientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `razao_social` varchar(255) NOT NULL,
  `cnpj` varchar(18) NOT NULL,
  `nome_responsavel` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `senha_hash` varchar(255) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `ativo` tinyint(1) DEFAULT '1',
  `criado_em` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cnpj` (`cnpj`),
  UNIQUE KEY `email` (`email`)
);