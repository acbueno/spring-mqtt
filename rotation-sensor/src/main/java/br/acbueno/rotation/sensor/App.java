package br.acbueno.rotation.sensor;

import org.eclipse.paho.client.mqttv3.MqttException;
import br.acbueno.rotation.sensor.device.RotationSensor;


public class App {
  public static void main(String[] args) {
    try {
      RotationSensor rotationSensor = new RotationSensor();
      rotationSensor.init(true);
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }
}
