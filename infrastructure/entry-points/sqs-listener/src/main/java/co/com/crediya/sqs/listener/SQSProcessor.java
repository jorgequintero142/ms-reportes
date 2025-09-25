package co.com.crediya.sqs.listener;

import co.com.crediya.model.reportesolicitud.ReporteSolicitud;
import co.com.crediya.usecase.generarreporte.GenerarReporteUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.math.BigDecimal;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final GenerarReporteUseCase generarReporteUseCase;

    @Override
    public Mono<Void> apply(Message message) {
        return Mono.fromSupplier(() -> {
                    // Crear el objeto de forma reactiva
                    String[] datos = message.body().split(",");
                    return ReporteSolicitud.builder()
                            .totalPrestamosAprobados(Integer.parseInt(datos[0]))
                            .montoTotalPrestamos(new BigDecimal(datos[1]))
                            .build();
                })
                .doOnNext(reporte -> {
                    log.debug("Enviando nuevo registro");
                    System.out.println("Enviando nuevo registro " + message.body());
                })
                // Encadenar el guardado reactivo en DynamoDB
                .flatMap(reporte -> generarReporteUseCase.actualizarInformacion(reporte))
                .doOnSuccess(v -> System.out.println("Registro procesado correctamente"))
                .doOnError(e -> System.err.println("Error procesando registro: " + e.getMessage()));
    }
}
