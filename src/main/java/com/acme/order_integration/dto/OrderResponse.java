package com.acme.order_integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderResponse {
    @JsonProperty("enviarPedidoRespuesta")
    private OrderResponseData enviarPedidoRespuesta;

    public OrderResponse() {}

    public OrderResponse(String codigoEnvio, String estado) {
        this.enviarPedidoRespuesta = new OrderResponseData(codigoEnvio, estado);
    }

    // Getters y Setters
    public OrderResponseData getEnviarPedidoRespuesta() { return enviarPedidoRespuesta; }
    public void setEnviarPedidoRespuesta(OrderResponseData enviarPedidoRespuesta) { this.enviarPedidoRespuesta = enviarPedidoRespuesta; }

    public static class OrderResponseData {
        private String codigoEnvio;
        private String estado;

        public OrderResponseData() {}
        public OrderResponseData(String codigoEnvio, String estado) {
            this.codigoEnvio = codigoEnvio;
            this.estado = estado;
        }

        // Getters y Setters
        public String getCodigoEnvio() { return codigoEnvio; }
        public void setCodigoEnvio(String codigoEnvio) { this.codigoEnvio = codigoEnvio; }
        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }
    }
}