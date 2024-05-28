package br.acbueno.rotation.sensor.device;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import br.acbueno.rotation.sensor.device.config.ConnectionBroker;

public class RotationSensor {

  private final static String TOPIC = "sensors/rotation";
  private final Random random = new Random();
  private final MqttClient mqttClient;


  public RotationSensor() throws MqttException {
    ConnectionBroker connectionBroker = new ConnectionBroker();
    this.mqttClient = connectionBroker.connect();
  }

  public void init(boolean start) {
    startPublished(start);
  }

  public void stop() {
    startPublished(false);
  }

  private void startPublished(boolean running) {
    while (running) {
      int rotation = generateRotation();
      try {
        publishedRotation(rotation);
        Thread.sleep(5000);
      } catch (MqttException | InterruptedException e) {
        handleExeception(e);
      }
    }
  }

  private int generateRotation() {
    return 1000 + random.nextInt(2000);
  }

  private void handleExeception(Exception e) {
    System.err.println(String.format("Error during published %s", e.getMessage()));
    e.printStackTrace();
  }

  private void publishedRotation(int rotation) throws MqttException {
    String payload = String.valueOf(rotation);
    MqttMessage message = new MqttMessage(payload.getBytes());
    mqttClient.publish(TOPIC, message);
    System.out.println(String.format("Published rotation %s", rotation));
  }

}
