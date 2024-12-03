package influxdb.teste.demo.Repository;

import influxdb.teste.demo.Model.Metric;
import org.springframework.stereotype.Repository;

import com.influxdb.client.DeleteApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MetricRepository {

    private final InfluxDBClient influxDBClient;

    public MetricRepository(InfluxDBClient influxDBClient) {
        this.influxDBClient = influxDBClient;
    }

    /**
     * Salva uma métrica no InfluxDB
     * @param metric
     */
    public void saveMetric(Metric metric) {
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking(); // Utilize o WriteApiBlocking para operações síncronas e WriteApi para operações assíncrona
        Point point = Point
            .measurement(metric.getName())
            .addField("value", metric.getValue())
            .time(metric.getTimestamp(), WritePrecision.MS); // WritePrecision.MS para milissegundos

        writeApi.writePoint(point); // Escreve a métrica no InfluxDB
    }

    /**
     * Retorna todas as métricas de um determinado range
     * @param range
     * @return
     */
    public List<Metric> getAllMetrics(String range) {
        String fluxQuery = String.format("from(bucket: \"bucket\") |> range(start: %s)", range);
        List<FluxTable> tables = influxDBClient.getQueryApi().query(fluxQuery);
        return tables.stream()
            .flatMap(table -> table.getRecords().stream())
            .map(record -> new Metric(
                record.getMeasurement(),
                (Double) record.getValueByKey("value"),
                record.getTime()
            ))
            .collect(Collectors.toList());
    }

    /**
     * Deleta uma métrica do InfluxDB
     * @param metric
     */
    public void deleteMetric(Metric metric) {

        DeleteApi deleteApi = influxDBClient.getDeleteApi();

        OffsetDateTime start = OffsetDateTime.ofInstant(metric.getTimestamp(), ZoneOffset.UTC);
        OffsetDateTime end = start.plus(1, ChronoUnit.MILLIS);

        String predicate = String.format("_measurement=\"%s\"", metric.getName());

        deleteApi.delete(
            start,
            end,
            predicate,
            "bucketMetrics", // Substitua pelo nome real do seu bucket
            "GEDT"     // Substitua pelo nome da sua organização
        );

    }

}
