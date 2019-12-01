CREATE TABLE `categoria` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `data_atualizacao` datetime NOT NULL,
  `data_criacao` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


  
--
-- AUTO_INCREMENT for table `categoria`
--
ALTER TABLE `categoria`
  