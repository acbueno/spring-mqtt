package br.com.acbueno.mqtt.consumer.service;


import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class MqttConsumerService implements MqttCallback {

  private static final String TEMPERATURE_TOPIC = "sensors/temperature";
  private static final String ROTATION_TOPIC = "sensors/rotation";
  private static final String MAF_SENSOR = "sensors/maf";
  private static final String TROTTLE_POSITION = "sensors/trottle";
  private static final Logger LOGGER = LoggerFactory.getLogger(MqttConsumerService.class);

  @Autowired
  private InfluxDB influxDB;

  @Autowired
  private MqttClient mqttClient;

  @PostConstruct
  public void subscribeToTopic() throws MqttException {
    mqttClient.setCallback(this);
    mqttClient.subscribe(TEMPERATURE_TOPIC);
    mqttClient.subscribe(ROTATION_TOPIC);
    mqttClient.subscribe(MAF_SENSOR);
    mqttClient.subscribe(TROTTLE_POSITION);
  }

  @Override
  public void connectionLost(Throwable cause) {
    LOGGER.warn("Connection Lost {}", cause.getMessage());
  }

  @Override
  public void messageArrived(String topic, MqttMessage message) throws Exception {
    String payload = new String(message.getPayload());
    Point point = null;
    //@formatter:off
    switch (topic)  {
      case TEMPERATURE_TOPIC: {
        int temperature = Integer.parseInt(payload);
        point = Point.measurement("temperature")
            .time(System.currentTimeMillis(), TimeUnit.MICROSECONDS)
             .addField("value", temperature)
              .build();
        break;
      }
      case ROTATION_TOPIC: {
        int rotation = Integer.parseInt(payload);
        point = Point.measurement("rotation")
            .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .addField("value", rotation).build();
          break;
        }
      case MAF_SENSOR: {
        double maf = Double.parseDouble(payload);
        point = Point.measurement("maf")
            .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .addField("value", maf)
            .build();
        break;
      }
      case TROTTLE_POSITION: {
        int trottle = Integer.parseInt(payload);
        point = Point.measurement("trottle_position")
            .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .addField("value", trottle).build();
        break;
      }
    }
    //@foramatter:on
    if(point!=null) {
      influxDB.write(point);
    }
    LOGGER.info("Message arrived. Topic {} MEssage {}", topic, payload);
  }

  @Override
  public void deliveryComplete(IMqttDeliveryToken token) {
  }

}
