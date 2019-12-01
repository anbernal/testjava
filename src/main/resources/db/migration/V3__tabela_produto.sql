CREATE TABLE `produto` (
  `id` bigint(20) NOT NULL,
  `produto` varchar(255) NOT NULL,
  `preco` DECIMAL(10, 2) NOT NULL,
  `quantidade_estoque` INTEGER NOT NULL,
  `descricao` varchar(255),
  `foto` varchar(255),
  `categoria_id` bigint(20) DEFAULT NULL,
  `data_atualizacao` datetime NOT NULL,
  `data_criacao` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Indexes for table `produto`
--
ALTER TABLE `produto`
  ADD PRIMARY KEY (`id`);
  
--
-- AUTO_INCREMENT for table `produto`
--
ALTER TABLE `produto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT; 
  
--
-- Constraints for table `produto`
--
ALTER TABLE `produto`
  ADD CONSTRAINT  FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`);
  