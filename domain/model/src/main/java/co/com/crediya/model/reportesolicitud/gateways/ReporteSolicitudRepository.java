package co.com.crediya.model.reportesolicitud.gateways;

import co.com.crediya.model.reportesolicitud.ReporteSolicitud;
import co.com.crediya.model.reportesolicitud.TotalSolicitudes;
import reactor.core.publisher.Mono;

public interface ReporteSolicitudRepository {
    Mono<TotalSolicitudes> consultarReporte();
    Mono<Void> registrarActualizacion(ReporteSolicitud reporteSolicitud);
}
