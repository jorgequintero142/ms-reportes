package co.com.crediya.usecase.generarreporte;

import co.com.crediya.model.reportesolicitud.ReporteSolicitud;
import co.com.crediya.model.reportesolicitud.TotalSolicitudes;
import co.com.crediya.model.reportesolicitud.gateways.ReporteSolicitudRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GenerarReporteUseCase {
   private final ReporteSolicitudRepository reporteSolicitudRepository;
    public Mono<TotalSolicitudes> generarReporte() {
          return reporteSolicitudRepository.consultarReporte();
    }

    public Mono<Void> actualizarInformacion(ReporteSolicitud reporteSolicitud) {
        System.out.println("actualizarInformacion -->"+reporteSolicitud);
        return reporteSolicitudRepository.registrarActualizacion(reporteSolicitud)
                .doOnSuccess(v -> System.out.println("Registro actualizado correctamente: " + reporteSolicitud))
                .doOnError(e -> System.out.println("Error al actualizar registro: " + e.getMessage()))
                .then();

    }
}
