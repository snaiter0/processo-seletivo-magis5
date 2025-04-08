CREATE TABLE logistica.secao (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 localizacao VARCHAR(255) NOT NULL,
                                 ultimo_tipo_bebida_armazenado VARCHAR(50),
                                 data_esvaziamento_secao DATE,
                                 capacidade_arm_alcool INT,
                                 capacidade_arm_nao_alcool INT,
                                 dat_insercao_reg TIMESTAMP,
                                 dat_hora_ult_modif_reg TIMESTAMP
);
