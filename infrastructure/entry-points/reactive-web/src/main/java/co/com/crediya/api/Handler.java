package co.com.crediya.api;

import co.com.crediya.usecase.generarreporte.GenerarReporteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final GenerarReporteUseCase generarReporteUseCase;

    @Operation(
            summary = "Consultar total de solocitudes aprobadas",
            description = "Consultar total de solocitudes aprobadas",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"Reportes"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Reporte de solicitudes",
                            content = @Content(
                                    schema = @Schema(
                                            example = """
                                                    {
                                                        "totalPrestamosAprobados": 7,
                                                        "montoTotalPrestamos": 686000
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    public Mono<ServerResponse> generar(ServerRequest serverRequest) {

        return generarReporteUseCase.generarReporte()
                .flatMap(reporte -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(reporte));

    }


}
