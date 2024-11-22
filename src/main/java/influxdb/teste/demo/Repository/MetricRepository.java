package influxdb.teste.demo.Repository;

import influxdb.teste.demo.Model.Metric;

import org.springframework.stereotype.Repository;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class MetricRepository {

    private final InfluxDBClient influxDBClient;

    public MetricRepository(InfluxDBClient influxDBClient) {
        this.influxDBClient = influxDBClient;
    }

    public void saveMetric(Metric metric) {
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        Point point = Point
            .measurement(metric.getName())
            .addField("value", metric.getValue())
            .time(metric.getTimestamp(), WritePrecision.MS);
        writeApi.writePoint(point);
    }

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
}
