package co.com.crediya.usecase.generarreporte;

import co.com.crediya.model.reportesolicitud.ReporteSolicitud;
import co.com.crediya.model.reportesolicitud.TotalSolicitudes;
import co.com.crediya.model.reportesolicitud.gateways.ClienteWebClientes;
import co.com.crediya.model.reportesolicitud.gateways.ReporteSolicitudRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GenerarReporteUseCase {
    private final ReporteSolicitudRepository reporteSolicitudRepository;
    private final ClienteWebClientes clienteWebClientes;
    private static final String ROL_GENERAR_REPORTE = "Administrador";

    public Mono<TotalSolicitudes> generarReporte() {
        return clienteWebClientes
                .buscarUsuarioPorToken()
                .onErrorResume(throwable -> Mono.error(new Exception("Error de autenticacion")))
                .flatMap(informacionUsuarioToken ->
                        verificarPermisos(informacionUsuarioToken.getData().getRol())
                                .switchIfEmpty(Mono.error(new Exception("No tienes el rol para ejecutar esta accion")))
                                .flatMap(s -> reporteSolicitudRepository.consultarReporte())
                );
    }

    Mono<String> verificarPermisos(String rol) {
        return Mono.defer(() -> {
            if (!ROL_GENERAR_REPORTE.equals(rol)) {
                return Mono.empty();
            }
            return Mono.just("OK");
        });
    }

    public Mono<Void> actualizarInformacion(ReporteSolicitud reporteSolicitud) {
        System.out.println("actualizarInformacion -->" + reporteSolicitud);
        return reporteSolicitudRepository.registrarActualizacion(reporteSolicitud)
                .doOnSuccess(v -> System.out.println("Registro actualizado correctamente: " + reporteSolicitud))
                .doOnError(e -> System.out.println("Error al actualizar registro: " + e.getMessage()))
                .then();

    }
}
