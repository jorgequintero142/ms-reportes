package co.com.crediya.consumer;

import co.com.crediya.model.reportesolicitud.dto.InformacionUsuarioToken;
import co.com.crediya.model.reportesolicitud.gateways.ClienteWebClientes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestConsumer implements ClienteWebClientes {
    private final WebClient client;
    public static final String ERROR_TOKEN = "No se envió token de seguridad";


    @Override
    public Mono<InformacionUsuarioToken> buscarUsuarioPorToken() {
        System.out.println("buscarUsuarioPorToken");
        return leerTokenFromSecurityContext()
                .onErrorResume(throwable ->  Mono.error(new  Exception(ERROR_TOKEN)))
                .switchIfEmpty(Mono.error(new Exception(ERROR_TOKEN)))
                .flatMap(token -> {
                    System.out.println("token-->"+token);
                    return client
                        .get()
                        .uri("/api/v1/token")
                        .header("Authorization", "Bearer " + token)
                        .retrieve()
                        .bodyToMono(InformacionUsuarioToken.class);});
    }
    public Mono<String> leerTokenFromSecurityContext() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getCredentials().toString());
    }

}
