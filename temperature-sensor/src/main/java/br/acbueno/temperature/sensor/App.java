package br.acbueno.temperature.sensor;

import org.eclipse.paho.client.mqttv3.MqttException;
import br.acbueno.temperature.sensor.device.TemperatureSensor;

public class App {
  public static void main(String[] args) {
    try {
      TemperatureSensor temperatureSensor = new TemperatureSensor();
      temperatureSensor.init(true);
    } catch (MqttException e) {
      e.printStackTrace();
    }

  }
}
