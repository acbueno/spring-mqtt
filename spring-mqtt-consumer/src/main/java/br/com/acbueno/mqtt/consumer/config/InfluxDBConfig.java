package br.com.acbueno.mqtt.consumer.config;

import java.util.concurrent.TimeUnit;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfig {

  @Bean
  public InfluxDB config() {
    String influxDbUrl = "http://localhost:1010";
    String unername = "abueno";
    String password = "UfqeuBPdAU";
    String datbase = "sensor";

    InfluxDB influxDB = InfluxDBFactory.connect(influxDbUrl, unername, password);
    influxDB.setDatabase(datbase);
    influxDB.setRetentionPolicy("autogen");
    influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
    influxDB.enableBatch(2000, 100, TimeUnit.MILLISECONDS);

    // Enable GZIP
    influxDB.enableGzip();

    // Test connection
    Pong response = influxDB.ping();
    if (response.getVersion().equalsIgnoreCase("unknown")) {
      throw new RuntimeException("Unable to connect to InfluxDB");
    }

    return influxDB;
  }

}
