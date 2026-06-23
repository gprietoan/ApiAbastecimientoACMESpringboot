# API de Integración de Abastecimiento ACME - Spring Boot

Este proyecto es un microservicio desarrollado en **Spring Boot (Java 17)** que actúa como una capa de abstracción y transformación de datos para el sistema de abastecimiento de ACME. Su función principal es recibir solicitudes de pedidos en formato **JSON** a través de un endpoint REST, transformar la estructura a un payload **XML SOAP**, interoperar con los servicios remotos de la compañía y realizar el mapeo inverso de la respuesta XML a un formato JSON limpio y estructurado de cara al cliente.

---

## Arquitectura y Capacidades

El servicio está diseñado siguiendo principios de arquitectura limpia y desacoplamiento, estructurado en las siguientes capas:
* **Controller:** Expone el endpoint REST seguro `/api/v1/pedidos/enviar` para la recepción de solicitudes.
* **Service (Logística de Negocio):** Se encarga de la orquestación del flujo, la inyección dinámica de datos en SOAP y la gestión del cliente HTTP.
* **Data Transfer Objects (DTO):** Modelos estrictamente tipados (`OrderRequest` y `OrderResponse`) para garantizar los datos íntegros.

### Características Clave:
* **Transformación Estructural Bidireccional:** Procesamiento eficiente y nativo de XML a través de `DocumentBuilderFactory` sin dependencias pesadas de terceros.
* **Contingencia y Resiliencia Local:** Cuenta con un mecanismo de contingencia mockeado localmente para garantizar la continuidad de las pruebas de integración en entornos de desarrollo sin dependencias de red externas.
* **Bypass de Seguridad SSL:** Implementación de un contexto seguro simplificado (`TrustManager` personalizado) para omitir problemas de cadenas de certificación (`SunCertPathBuilderException`) en ambientes no productivos.

---

## Tecnologías Utilizadas

* **Java 17** (Eclipse Adoptium)
* **Spring Boot 3.x** (Web Starters)
* **Maven** (Gestor de dependencias)
* **VS

## IMPORTANTE
El desarrollo quedó con un Mock porque la URL entregada en el documento que va hacia Mocky se encuentra inhabilitada y siempre genera un error de comunicación 400
