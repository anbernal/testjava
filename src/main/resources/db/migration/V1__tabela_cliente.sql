CREATE TABLE `cliente` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `perfil` varchar(255) NOT NULL,
  `rua` varchar(255),
  `bairro` varchar(255),
  `cidade` varchar(255),
  `estado` varchar(255),
  `cep` varchar(255),
  `data_atualizacao` datetime NOT NULL,
  `data_criacao` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

  
--
-- AUTO_INCREMENT for table `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT; 
  

  
  
  