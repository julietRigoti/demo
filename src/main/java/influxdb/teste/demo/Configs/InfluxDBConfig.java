package influxdb.teste.demo.Configs;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfig {

    private final String url = "http://influxdb:8086/";
    private final String token = "INFLUXGEDTOKEN";
    private final String org = "GEDT";
    private final String bucket = "bucketMetrics";

    @Bean
    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
    }
}
