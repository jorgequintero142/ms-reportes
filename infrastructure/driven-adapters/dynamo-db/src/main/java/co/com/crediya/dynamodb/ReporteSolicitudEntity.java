package co.com.crediya.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.math.BigDecimal;

/* Enhanced DynamoDB annotations are incompatible with Lombok #1932
         https://github.com/aws/aws-sdk-java-v2/issues/1932*/
@DynamoDbBean
public class ReporteSolicitudEntity {

    private String id; // clave de partición
    private int totalPrestamosAprobados;
    private BigDecimal montoTotalPrestamos;

    public ReporteSolicitudEntity() {}

    public ReporteSolicitudEntity(String id, int totalPrestamosAprobados, BigDecimal montoTotalPrestamos) {
        this.id = id;
        this.totalPrestamosAprobados = totalPrestamosAprobados;
        this.montoTotalPrestamos = montoTotalPrestamos;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbAttribute("totalPrestamosAprobados")
    public int getTotalPrestamosAprobados() {
        return totalPrestamosAprobados;
    }

    public void setTotalPrestamosAprobados(int totalPrestamosAprobados) {
        this.totalPrestamosAprobados = totalPrestamosAprobados;
    }

    @DynamoDbAttribute("montoTotalPrestamos")
    public BigDecimal getMontoTotalPrestamos() {
        return montoTotalPrestamos;
    }

    public void setMontoTotalPrestamos(BigDecimal montoTotalPrestamos) {
        this.montoTotalPrestamos = montoTotalPrestamos;
    }
}