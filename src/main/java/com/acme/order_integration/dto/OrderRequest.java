package com.acme.order_integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderRequest {
    @JsonProperty("enviarPedido")
    private OrderData enviarPedido;

    // Getters y Setters

    public OrderData getEnviarPedido() { return enviarPedido; }
    public void setEnviarPedido(OrderData enviarPedido) { this.enviarPedido = enviarPedido; }

    public static class OrderData {
        private String numPedido;
        private String cantidadPedido;
        private String codigoEAN;
        private String nombreProducto;
        private String numDocumento;
        private String direccion;

        // Getters y Setters
        public String getNumPedido() { return numPedido; }
        public void setNumPedido(String numPedido) { this.numPedido = numPedido; }
        public String getCantidadPedido() { return cantidadPedido; }
        public void setCantidadPedido(String cantidadPedido) { this.cantidadPedido = cantidadPedido; }
        public String getCodigoEAN() { return codigoEAN; }
        public void setCodigoEAN(String codigoEAN) { this.codigoEAN = codigoEAN; }
        public String getNombreProducto() { return nombreProducto; }
        public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
        public String getNumDocumento() { return numDocumento; }
        public void setNumDocumento(String numDocumento) { this.numDocumento = numDocumento; }
        public String getDireccion() { return direccion; }
        public void setDireccion(String direccion) { this.direccion = direccion; }
    }
}