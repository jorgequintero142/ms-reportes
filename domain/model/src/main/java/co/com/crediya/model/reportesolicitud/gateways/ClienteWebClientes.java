package co.com.crediya.model.reportesolicitud.gateways;

import co.com.crediya.model.reportesolicitud.dto.InformacionUsuarioToken;
import reactor.core.publisher.Mono;

public interface ClienteWebClientes {
    Mono<InformacionUsuarioToken> buscarUsuarioPorToken();
}
