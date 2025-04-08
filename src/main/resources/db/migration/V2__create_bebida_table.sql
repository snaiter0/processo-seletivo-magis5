CREATE TABLE logistica.bebida (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  categoria_bebida VARCHAR(255) NOT NULL,
                                  data_entrada DATE,
                                  data_saida DATE,
                                  marca VARCHAR(255),
                                  tipo_bebida VARCHAR(50),
                                  preco_compra DOUBLE PRECISION,
                                  preco_medio DOUBLE PRECISION,
                                  preco_venda DOUBLE PRECISION,
                                  litragem DOUBLE PRECISION,
                                  disp_venda VARCHAR(50),
                                  data_validade DATE,
                                  secao_id BIGINT,
                                  dat_insercao_reg TIMESTAMP,
                                  dat_hora_ult_modif_reg TIMESTAMP,
                                  FOREIGN KEY (secao_id) REFERENCES logistica.secao(id)
);