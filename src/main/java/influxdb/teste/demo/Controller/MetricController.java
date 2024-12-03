package influxdb.teste.demo.Controller;

import com.influxdb.client.InfluxDBClient;
import influxdb.teste.demo.Model.Metric;
import influxdb.teste.demo.Repository.MetricRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/metrics")
public class MetricController {

    private final MetricRepository metricRepository;

    public MetricController(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    /**
     * Método para escrever uma métrica no influxDB
     * @param metric
     * @return ResponseEntity<Void>
     */
    @PostMapping
    public ResponseEntity<Void> writeMetric(@RequestBody Metric metric) {
        if(InfluxDBClient.metricIsInvalid(metric)) { //arrumar isso aqui
            return ResponseEntity.badRequest().build();
        }
        metricRepository.saveMetric(metric);
        return ResponseEntity.ok().build();
    }
    /**
     * Método para buscar todas as métricas do influxDB
     * @param range
     * @return List<Metric>
     */

    @GetMapping
    public List<Metric> queryMetrics(@RequestParam(defaultValue = "-1h") String range) {
        return metricRepository.getAllMetrics(range);
    }

    /**
     * Método para buscar uma métrica específica do influxDB
     * @param metric
     * @return Metric
     */

    @PutMapping
    public ResponseEntity<Void> updateMetric(@RequestBody Metric metric) {
        metricRepository.saveMetric(metric);
        return ResponseEntity.ok().build();
    }

    /**
     * Método para deletar uma métrica específica do influxDB
     * @param metric
     * @return ResponseEntity<Void>
     */

    @DeleteMapping
    public ResponseEntity<Void> deleteMetric(@RequestBody Metric metric) {
        metricRepository.deleteMetric(metric);
        return ResponseEntity.ok().build();
    }

}