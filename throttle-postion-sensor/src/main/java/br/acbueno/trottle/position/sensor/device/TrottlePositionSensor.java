package br.acbueno.trottle.position.sensor.device;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import br.acbueno.trottle.position.sensor.device.config.ConnctionBroker;

public class TrottlePositionSensor {

  private static final String TOPIC = "sensors/trottle";
  private final Random random = new Random();
  private final MqttClient mqttClient;


  public TrottlePositionSensor() throws MqttException {
    ConnctionBroker connctionBroker = new ConnctionBroker();
    this.mqttClient = connctionBroker.connect();
  }

  public void init(boolean start) {
    startPublished(start);
  }

  private void startPublished(boolean running) {
    while (running) {
      int postion = generateTrottlePosition();
      try {
        publishedPostion(postion);
        Thread.sleep(5000);
      } catch (MqttException | InterruptedException e) {
        handleException(e);
      }
    }
  }

  private void handleException(Exception e) {
    System.err.println(String.format("Error during published %s", e.getMessage()));
  }


  private int generateTrottlePosition() {
    return 0 + random.nextInt(100);
  }

  private void publishedPostion(int postion) throws MqttPersistenceException, MqttException {
    String payload = String.valueOf(postion);
    MqttMessage message = new MqttMessage(payload.getBytes());
    mqttClient.publish(TOPIC, message);
    System.out.println(String.format("Published postion %s", postion));
  }

}
