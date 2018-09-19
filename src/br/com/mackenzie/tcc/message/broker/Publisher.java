package br.com.mackenzie.tcc.message.broker;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publisher {

	private static IMqttClient publisher;
	private String publisherId;
	private String topic;
	private String message;
	
	Publisher(String topic, String message) {
		 this.publisherId = "PUB_ID_TCC"; //UUID.randomUUID().toString();
		 this.topic = topic; //"PUB:TCC";
		 this.message = message;//"HELLO WORLD";
	}
	
	public static void initPublisherClient(String topic, String msg, boolean closeable) throws MqttException {
		new Publisher(topic, msg).init(closeable);
	}
	
	public void init(boolean closeable) throws MqttException {
		try {
			MqttMessage mqttMsg = createMessage(message);
			MqttConnectOptions options = getConnection();
			
			publisher = getLocalClient(); 
			publisher.connect(options);
			publisher.publish(topic, mqttMsg);
			
		} catch (MqttException e) {
			e.printStackTrace();
		} finally {
			if (closeable) {
				close();
			}
		}
	}
	
	public IMqttClient getEclipseClient() throws MqttException {
		return new MqttClient("tcp://iot.eclipse.org:1883", publisherId);
	}
	
	public IMqttClient getLocalClient() throws MqttException {
		return new MqttClient("tcp://localhost:1883", publisherId);
	}
	
	public IMqttClient getClient(String host) throws MqttException {
		return new MqttClient("tcp://" + host + ":1883", publisherId);
	}
	
	public MqttConnectOptions getConnection() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(true);
		options.setCleanSession(true);
		options.setConnectionTimeout(10);
		return options;
	}
	
	public MqttMessage createMessage(String msg) {
		MqttMessage mqttMsg = new MqttMessage();
		mqttMsg.setPayload(msg.getBytes());
		mqttMsg.setQos(1);
		mqttMsg.setId(1);
		mqttMsg.setRetained(true);
		return mqttMsg;
	}

	public static void close() throws MqttException {
		publisher.disconnect();
		publisher.close();
	}
	
}
