package influxdb.teste.demo.Controller;

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

    @PostMapping
    public ResponseEntity<Void> writeMetric(@RequestBody Metric metric) {
        metricRepository.saveMetric(metric);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Metric> queryMetrics(@RequestParam(defaultValue = "-1h") String range) {
        return metricRepository.getAllMetrics(range);
    }

    @PutMapping
    public ResponseEntity<Void> updateMetric(@RequestBody Metric metric) {
        metricRepository.saveMetric(metric);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMetric(@RequestBody Metric metric) {
        metricRepository.deleteMetric(metric);
        return ResponseEntity.ok().build();
    }

}