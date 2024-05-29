package br.acbueno.trottle.position.sensor;

import org.eclipse.paho.client.mqttv3.MqttException;
import br.acbueno.trottle.position.sensor.device.TrottlePositionSensor;

public class App {
  public static void main(String[] args) {
    try {
      TrottlePositionSensor trottlePositionSensor = new TrottlePositionSensor();
      trottlePositionSensor.init(true);
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }
}
