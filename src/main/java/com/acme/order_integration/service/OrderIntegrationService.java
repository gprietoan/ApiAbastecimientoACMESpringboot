package com.acme.order_integration.service;

import com.acme.order_integration.dto.OrderRequest;
import com.acme.order_integration.dto.OrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.security.cert.X509Certificate;

@Service
public class OrderIntegrationService {

    private final RestTemplate restTemplate;
    private final String soapEndpoint = "https://run.mocky.io/v3/19217075-6d4e-4818-98bc-416d1feb7b84";

    static {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OrderIntegrationService() {
        this.restTemplate = new RestTemplate();
    }

    // Clase llamando a la URL Externa de Mocky pero se encuentra denegada.
    /* 
    public OrderResponse processOrder(OrderRequest request) {
        try {
            // Transformación Estructural (JSON -> XML SOAP)
            String soapXmlPayload = buildSoapRequest(request.getEnviarPedido());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "text/xml; charset=utf-8");
            headers.add("Accept", "text/xml");
            
            HttpEntity<String> entity = new HttpEntity<>(soapXmlPayload, headers);

            // Consumo del Endpoint Externo
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    soapEndpoint,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            String xmlResponse = responseEntity.getBody();

            if (xmlResponse == null || xmlResponse.trim().isEmpty()) {
                return new OrderResponse("500", "El servicio remoto devolvió una respuesta vacía.");
            }

            // Transformación Estructural Inversa (XML -> JSON)
            return parseSoapResponse(xmlResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return new OrderResponse("500", "Falla de comunicación o procesamiento: " + e.getMessage());
        }
    }
*/


// Clase para realizar la prueba local sin necesidad de consumir el endpoint externo, 
// simulando la respuesta XML esperada.
public OrderResponse processOrder(OrderRequest request) {
    try {
        // Simulamos la respuesta XML exacta que devolvería el servidor externo
        String xmlSimuladoResponse = """
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:res="http://WSDLs/EnvioPedidos/EnvioPedidosAcme">
           <soapenv:Header/>
           <soapenv:Body>
              <res:EnvioPedidoResponseAcme>
                 <Codigo>200</Codigo>
                 <Mensaje>Pedido %s recibido y procesado en contingencia local exitosamente</Mensaje>
              </res:EnvioPedidoResponseAcme>
           </soapenv:Body>
        </soapenv:Envelope>
        """.formatted(request.getEnviarPedido().getNumPedido());

        // Mapea la estructura inversa directamente de XML a tu DTO JSON de salida
        return parseSoapResponse(xmlSimuladoResponse);

    } catch (Exception e) {
        e.printStackTrace();
        return new OrderResponse("500", "Falla de procesamiento local: " + e.getMessage());
    }
}


    private String buildSoapRequest(OrderRequest.OrderData data) {
        return """
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:env="http://WSDLs/EnvioPedidos/EnvioPedidosAcme">
           <soapenv:Header/>
           <soapenv:Body>
              <env:EnvioPedidoAcme>
                 <EnvioPedidoRequest>
                    <pedido>%s</pedido>
                    <Cantidad>%s</Cantidad>
                    <EAN>%s</EAN>
                    <Producto>%s</Producto>
                    <Cedula>%s</Cedula>
                    <Direccion>%s</Direccion>
                 </EnvioPedidoRequest>
              </env:EnvioPedidoAcme>
           </soapenv:Body>
        </soapenv:Envelope>
        """.formatted(
            data.getNumPedido(),
            data.getCantidadPedido(),
            data.getCodigoEAN(),
            data.getNombreProducto(),
            data.getNumDocumento(),
            data.getDireccion()
        );
    }

    private OrderResponse parseSoapResponse(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); 
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));

            String codigo = "";
            String mensaje = "";

            if (doc.getElementsByTagNameNS("*", "Codigo").getLength() > 0) {
                codigo = doc.getElementsByTagNameNS("*", "Codigo").item(0).getTextContent();
            } else {
                codigo = "ERROR_DOM";
            }

            if (doc.getElementsByTagNameNS("*", "Mensaje").getLength() > 0) {
                mensaje = doc.getElementsByTagNameNS("*", "Mensaje").item(0).getTextContent();
            } else {
                mensaje = "No se pudo extraer el detalle del mensaje";
            }

            return new OrderResponse(codigo, mensaje);
        } catch (Exception e) {
            return new OrderResponse("500", "Error al parsear el XML de respuesta: " + e.getMessage());
        }
    }
}