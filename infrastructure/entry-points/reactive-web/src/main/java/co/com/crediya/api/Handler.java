package co.com.crediya.api;

import co.com.crediya.usecase.generarreporte.GenerarReporteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
private  final GenerarReporteUseCase generarReporteUseCase;


    public Mono<ServerResponse> generar(ServerRequest serverRequest) {

        return generarReporteUseCase.generarReporte()
                .flatMap(reporte -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(reporte));

    }



}
