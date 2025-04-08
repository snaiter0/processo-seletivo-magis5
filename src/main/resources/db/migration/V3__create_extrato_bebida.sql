CREATE TABLE logistica.extrato_bebida (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          id_externo VARCHAR(36) NOT NULL,
                                          tipo_extrato_enum VARCHAR(50),
                                          data_registro_extrato TIMESTAMP,
                                          dat_insercao_reg TIMESTAMP,
                                          dat_hora_ult_modif_reg TIMESTAMP,
                                          CONSTRAINT uk_extrato_bebida_id_externo UNIQUE (id_externo)
);