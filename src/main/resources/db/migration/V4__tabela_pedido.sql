CREATE TABLE `pedido` (
  `id` bigint(20) NOT NULL,
  `data_pedido` datetime NOT NULL,
  `status` varchar(255),
  `sessao` varchar(255),
  `cliente_id` bigint(20) DEFAULT NULL,
  `data_atualizacao` datetime NOT NULL,
  `data_criacao` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for table `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id`);
  
--
-- AUTO_INCREMENT for table `pedido`
--
ALTER TABLE `pedido`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;  
  
  
--
-- Constraints for table `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT  FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`);
  
  