package co.com.crediya.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    private static final String PROPIEDAD_ERROR = "error";
    private static final String PROPIEDAD_MENSAJE = "mensaje";
    private static final String PROPIEDAD_ESTADO = "estado";
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        Map<String, Object> error = new HashMap<>();
            error.put(PROPIEDAD_ERROR, HttpStatus.CONFLICT.value());
            error.put(PROPIEDAD_MENSAJE,"Error inesperado");
            error.put(PROPIEDAD_ESTADO, "Error de autenticacion o de aplicación, intente más tarde");
            logger.error("Error inesperado: {}", ex.getMessage(), ex);


        String body = String.format(
                """
                        {
                          "error": "%s",
                          "mensaje": "%s",
                          "estado": "%s"
                        }
                        """,
                error.get(PROPIEDAD_ERROR),
                error.get(PROPIEDAD_MENSAJE),
                error.get(PROPIEDAD_ESTADO)
        );

        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(bytes)));
    }
}
