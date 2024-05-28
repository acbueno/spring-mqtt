# Spring MQTT Project

Welcome to the Spring MQTT Project! This project demonstrates how to use Spring Boot with MQTT to simulate automotive electronic injection system sensors, specifically a rotation sensor and a temperature sensor. 
The sensors send MQTT messages to a Mosquitto broker, and a consumer subscribes to these messages for processing.

## Getting Started

### Prerequisites
Before you begin, ensure you have the following installed on your system:

- [Java](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
 
### Installation

Follow these steps to set up the project:

1. **Clone the repository:**

    ```bash
    git clone https://github.com/acbueno/spring-mqtt.git
    cd spring-mqtt
    ```

2. **Build the project:**

    Use Maven to clean and build the project:

    ```bash
    mvn clean install
    ```

3. **Set up and start the Mosquitto broker:**

    Use Docker Compose to start the Mosquitto MQTT broker:

    ```bash
    docker-compose up -d
    ```

### Running the Simulators

You can run the rotation sensor and temperature sensor simulators to start publishing MQTT messages:

1. **Start the rotation sensor simulator:**

    ```bash
    mvn exec:java -Dexec.mainClass="br.acbueno.rotation.sensor.RotationSensor"
    ```

2. **Start the temperature sensor simulator:**

    ```bash
    mvn exec:java -Dexec.mainClass="br.acbueno.temperature.sensor.TemperatureSensor"
    ```

### Consuming the Messages

To consume the messages published by the sensors, run the consumer:

```bash
mvn exec:java -Dexec.mainClass="br.acbueno.mqtt.consumer.MessageConsumer"
```
### Project Structure
Here's an overview of the project's structure:

- src/main/java/br/acbueno/rotation/sensor/RotationSensor.java: Simulates a rotation sensor that publishes rotation data to an MQTT topic.
- src/main/java/br/acbueno/temperature/sensor/TemperatureSensor.java: Simulates a temperature sensor that publishes temperature data to an MQTT topic.
- src/main/java/br/acbueno/mqtt/consumer/MessageConsumer.java: Subscribes to the MQTT topics and processes the incoming sensor data.
- src/main/java/br/acbueno/mqtt/config/ConnectionBroker.java: Manages the MQTT connection configuration, including broker URL, client ID, username, and password.

### Configuration
The MQTT broker connection settings are defined in the ConnectionBroker class. You can adjust these settings as needed:
```java
private static final String BROKER_URL = "tcp://127.0.0.1:1883";
private static final String CLIENT_ID = "mqttClient";
private static final String USERNAME = "your-username";
private static final String PASSWORD = "your-password";
```
Make sure to replace your-username and your-password with the actual credentials configured for your Mosquitto broker.

### Contributing
Contributions are welcome! If you would like to contribute to this project, please follow these steps:
  1. Fork the repository
  2. Create a new feature branch (git checkout -b feature/your-feature)
  3. Make your changes and commit them (git commit -am 'Add new feature')
  4. Push to the branch (git push origin feature/your-feature)
  5. Open a new Pull Request
### License
This project is licensed under the MIT License. See the LICENSE file for details.
