package br.com.acbueno.mqtt.consumer.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfigBroker {

  private static final String BROKER_URL = "tcp://localhost:1883";
  private static final String CLIENT_ID = "ConsumerMqtt";
  private static final String USER_NAME = "abueno";
  private static final String PASSWORD = "mONoQyKe";

  @Bean
  public MqttClient mqttClient() throws MqttException {
    MqttClient client = new MqttClient(BROKER_URL, CLIENT_ID, new MemoryPersistence());
    MqttConnectOptions options = new MqttConnectOptions();
    options.setUserName(USER_NAME);
    options.setPassword(PASSWORD.toCharArray());
    options.setAutomaticReconnect(true);
    options.setCleanSession(true);
    options.setConnectionTimeout(10);
    options.setKeepAliveInterval(20);
    options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
    client.connect(options);

    return client;
  }

}
