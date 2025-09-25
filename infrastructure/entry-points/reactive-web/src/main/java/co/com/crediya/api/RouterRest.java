package co.com.crediya.api;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/reportes",
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "generar"
            )})
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return RouterFunctions.route()

                .GET("/api/v1/reportes", handler::generar)
                .build();
    }
}
