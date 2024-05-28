package br.com.acbueno.mqtt.consumer.service;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class MqttConsumerService implements MqttCallback {

  private static final String TEMPERATURE_TOPIC = "sensors/temperature";
  private static final String ROTATION_TOPIC = "sensors/rotation";
  private static final Logger LOGGER = LoggerFactory.getLogger(MqttConsumerService.class);

  @Autowired
  private MqttClient mqttClient;

  @PostConstruct
  public void subscribeToTopic() throws MqttException {
    mqttClient.setCallback(this);
    mqttClient.subscribe(TEMPERATURE_TOPIC);
    mqttClient.subscribe(ROTATION_TOPIC);
  }

  @Override
  public void connectionLost(Throwable cause) {
    LOGGER.warn("Connection Lost {}", cause.getMessage());
    // System.out.println("Connection Lost: " + cause.getMessage());

  }

  @Override
  public void messageArrived(String topic, MqttMessage message) throws Exception {
    LOGGER.info("Message arrived. Topic {} MEssage {}", topic, new String(message.getPayload()));
    // System.out
    // .println("Message arrived. Topic " + topic + "Message " + new String(message.getPayload()));

  }

  @Override
  public void deliveryComplete(IMqttDeliveryToken token) {
    // TODO Auto-generated method stub
  }



}
