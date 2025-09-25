package co.com.crediya.model.reportesolicitud.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class InformacionUsuarioToken {
    private int estado;
    private String mensaje;
    private InformacionUsuario data;
}
