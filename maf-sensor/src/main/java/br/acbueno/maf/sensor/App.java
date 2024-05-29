package br.acbueno.maf.sensor;

import org.eclipse.paho.client.mqttv3.MqttException;
import br.acbueno.maf.sensor.device.MafSensor;

public class App {
  public static void main(String[] args) {
    try {
      MafSensor mafSensor = new MafSensor();
      mafSensor.init(true);
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }
}
