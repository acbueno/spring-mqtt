package br.acbueno.temperature.sensor.device.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ConnectionBroker {

  private final static String BROKER_URL = "tcp://localhost:1883";
  private final static String CLIENT_ID = "TemperatureSensor";
  private static final String USERNAME = "abueno";
  private static final String PASSWORD = "mONoQyKe";

  public MqttClient connect() throws MqttException {
    MqttClient client = new MqttClient(BROKER_URL, CLIENT_ID, new MemoryPersistence());
    MqttConnectOptions options = new MqttConnectOptions();
    options.setUserName(USERNAME);
    options.setPassword(PASSWORD.toCharArray());
    options.setConnectionTimeout(10);
    options.setKeepAliveInterval(20);
    options.setAutomaticReconnect(true);
    client.connect(options);

    return client;
  }


}
