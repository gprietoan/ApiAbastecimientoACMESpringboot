package com.acme.order_integration.controller;

import com.acme.order_integration.dto.OrderRequest;
import com.acme.order_integration.dto.OrderResponse;
import com.acme.order_integration.service.OrderIntegrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pedidos")
public class OrderController {

    private final OrderIntegrationService integrationService;

    public OrderController(OrderIntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<OrderResponse> enviarPedido(@RequestBody OrderRequest request) {
        OrderResponse response = integrationService.processOrder(request);
        return ResponseEntity.ok(response);
    }
}