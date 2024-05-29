package br.acbueno.maf.sensor.device;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import br.acbueno.maf.sensor.device.config.ConnectionBroker;

public class MafSensor {

  private static final String TOPIC = "sensors/maf";
  private final Random random = new Random();
  private final MqttClient mqttClient;

  public MafSensor() throws MqttException {
    ConnectionBroker connectionBroker = new ConnectionBroker();
    this.mqttClient = connectionBroker.connect();
  }

  public void startPublished(boolean running) {
    while (running) {
      double maf = generateMaf();
      try {
        publishedMaf(maf);
        Thread.sleep(5000);
      } catch (MqttException | InterruptedException e) {
        handleException(e);
      }
    }
  }

  public void init(boolean start) {
    startPublished(start);
  }

  private Double generateMaf() {
    Double min = 0.0; // Set To Your Desired Min Value
    Double max = 10.0; // Set To Your Desired Max Value
    double x = (Math.random() * ((max - min) + 1)) + min; // This Will Create A Random Number
                                                          // Inbetween Your Min And Max.
    double xrounded = Math.round(x * 100.0) / 100.0;
    return xrounded;
  }

  private void handleException(Exception e) {
    System.err.println(String.format("Error during published %s", e.getMessage()));
  }

  private void publishedMaf(Double maf) throws MqttPersistenceException, MqttException {
    String payload = String.valueOf(maf);
    MqttMessage message = new MqttMessage(payload.getBytes());
    mqttClient.publish(TOPIC, message);
    System.out.println(String.format("Published maf %s", maf));
  }

}
