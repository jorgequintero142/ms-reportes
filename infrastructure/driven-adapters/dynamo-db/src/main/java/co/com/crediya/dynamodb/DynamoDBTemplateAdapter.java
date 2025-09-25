package co.com.crediya.dynamodb;

import co.com.crediya.dynamodb.helper.TemplateAdapterOperations;
import co.com.crediya.model.reportesolicitud.ReporteSolicitud;
import co.com.crediya.model.reportesolicitud.TotalSolicitudes;
import co.com.crediya.model.reportesolicitud.gateways.ReporteSolicitudRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


@Repository
public class DynamoDBTemplateAdapter extends TemplateAdapterOperations<ReporteSolicitud, String, ReporteSolicitudEntity> implements ReporteSolicitudRepository {

    public DynamoDBTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(connectionFactory, mapper, d -> mapper.map(d, ReporteSolicitud.class), "ReporteSolicitudTable");
    }

    public Mono<List<ReporteSolicitud>> getEntityBySomeKeys(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return query(queryExpression);
    }

    public Mono<List<ReporteSolicitud>> getEntityBySomeKeysByIndex(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return queryByIndex(queryExpression);
    }

    private QueryEnhancedRequest generateQueryExpression(String partitionKey, String sortKey) {
        return QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(partitionKey).build()))
                .queryConditional(QueryConditional.sortGreaterThanOrEqualTo(Key.builder().sortValue(sortKey).build()))
                .build();
    }

    @Override
    public Mono<TotalSolicitudes> consultarReporte() {
        return  scan()
                .map(list -> list.stream()
                        .filter(r -> r.getMontoTotalPrestamos().compareTo(BigDecimal.ZERO) > 0)
                        .toList())
                .map(filteredList -> {
                    int totalItems = filteredList.size();
                    BigDecimal sumaMontos = filteredList.stream()
                            .map(ReporteSolicitud::getMontoTotalPrestamos)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return new TotalSolicitudes(totalItems, sumaMontos);
                });
    }

    @Override
    public Mono<Void> registrarActualizacion(ReporteSolicitud reporteSolicitud) {
        ReporteSolicitudEntity entity = new ReporteSolicitudEntity();
        reporteSolicitud.setId(UUID.randomUUID().toString());
        return  save(reporteSolicitud).then();
    }
}
