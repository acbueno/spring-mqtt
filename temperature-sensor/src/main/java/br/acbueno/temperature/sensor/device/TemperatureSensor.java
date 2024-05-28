package br.acbueno.temperature.sensor.device;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import br.acbueno.temperature.sensor.device.config.ConnectionBroker;

public class TemperatureSensor {

  private static final String TOPIC = "sensors/temperature";
  private final Random random = new Random();
  private final MqttClient mqttClient;


  public TemperatureSensor() throws MqttException {
    ConnectionBroker connectionBroker = new ConnectionBroker();
    this.mqttClient = connectionBroker.connect();
  }

  private void startPublised(boolean running) {
    while (running) {
      int temprature = genertaTemperature();
      try {
        publishedTemperature(temprature);
        Thread.sleep(5000);
      } catch (MqttException | InterruptedException e) {
        handleException(e);
      }
    }
  }

  public void init(boolean start) {
    startPublised(start);
  }

  private void handleException(Exception e) {
    System.err.println(String.format("Error during published %s", e.getMessage()));
  }

  private void publishedTemperature(int temprature) throws MqttException {
    String payloadd = String.valueOf(temprature);
    MqttMessage message = new MqttMessage(payloadd.getBytes());
    mqttClient.publish(TOPIC, message);
    System.out.println(String.format("Published temperature %s", temprature));
  }

  private int genertaTemperature() {
    return 20 + random.nextInt(15);
  }

}

