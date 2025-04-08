package io.git.portal.logistica.adapters.in.controller.dto.routes;


public interface Rotas {

    // nome da api do microservico
	public static String API_LOGISTICA = "/logistica/api/v1";
    public static String BASE = "/estoque";

    public interface Secao {
       public static String SECAO = BASE + "/secao";
    }

    public interface Extrato{
        public static String EXTRATO = BASE + "/extrato";
        public static String EXTRATO_BEBIDAS_POR_SESSAO_POR_TIPO = EXTRATO + "/secao-historico";
    }

    public interface Bebida {
        public static String BEBIDA = Secao.SECAO + "/bebida";
        public static String VOLUME_TOTAL = BEBIDA + "/volume-total";
        public static String VOLUME_DISPONIVEL = BEBIDA + "/volume-disponivel";
        public static String VOLUME_PARA_VENDA = BEBIDA + "/volume-venda";

    }
}