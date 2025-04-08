CREATE TABLE logistica.secao_historico (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           localizacao VARCHAR(255) NOT NULL,
                                           capacidade_arm_alcool INT,
                                           capacidade_arm_nao_alcool INT,
                                           extrato_id BIGINT,
                                           dat_insercao_reg TIMESTAMP,
                                           dat_hora_ult_modif_reg TIMESTAMP,
                                           FOREIGN KEY (extrato_id) REFERENCES extrato_bebida(id)
);