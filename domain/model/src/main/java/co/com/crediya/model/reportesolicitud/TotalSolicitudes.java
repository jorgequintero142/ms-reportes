package co.com.crediya.model.reportesolicitud;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class TotalSolicitudes {
    private int totalPrestamosAprobados;
    private BigDecimal montoTotalPrestamos;
}
