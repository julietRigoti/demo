package influxdb.teste.demo.Model;

import lombok.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Metric {
    private String name;
    private double value;
    private Instant timestamp;
    private String host = "localhost";
    private String bucket = "metrics";
    private String org = "my-org";
    private String field = "value";
    private String tag = "";
    private String tagValue = "";

    public Metric(String name, Object value, Instant timestamp) {
        this.name = name;
        this.value = (double) value;
        this.timestamp = timestamp;
    }
    

}

