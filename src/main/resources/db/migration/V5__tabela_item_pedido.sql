CREATE TABLE `item_pedido` (
  `id` bigint(20) NOT NULL,
  `valor` DECIMAL(10, 2) NOT NULL,
  `quantidade` INTEGER NOT NULL,
  `subtotal` DECIMAL(10, 2) NOT NULL,
  `produto_id` bigint(20) DEFAULT NULL,
  `pedido_id` bigint(20) DEFAULT NULL,
  `data_atualizacao` datetime NOT NULL,
  `data_criacao` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for table `item_pedido`
--
ALTER TABLE `item_pedido`
  ADD PRIMARY KEY (`id`);
  
--
-- AUTO_INCREMENT for table `item_pedido`
--
ALTER TABLE `item_pedido`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;  
  
 --
-- Constraints for table `item_pedido`
--
ALTER TABLE `item_pedido`
  ADD CONSTRAINT  FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`),

  ADD CONSTRAINT  FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`);
  
  
  