package br.com.mackenzie.tcc.message.broker;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * A sample application that demonstrates how to use the Paho MQTT v3.1 Client blocking API.
 */
public class Subscriber {

	private static StringBuilder receivedMessage = new StringBuilder("");
	
	private static IMqttClient subscriber;
	private String publisherId;
	private String topic;
	
	Subscriber(String topic) {
		this.publisherId = "SUB_ID_TCC"; //UUID.randomUUID().toString();
		this.topic = topic;
	}

	public static String initSubscriberClient(String topic, boolean closeable) throws MqttException {
		new Subscriber(topic).init(closeable);
		return receivedMessage.toString();
	}
	
	public void init(boolean closeable) throws MqttException {
		MqttCallback mqttCallback = createMqttCallback();
		try {
			subscriber = getLocalClient();
			subscriber.connect(createConnectOptions());
			subscriber.setCallback(mqttCallback);
			subscriber.subscribeWithResponse(topic);
		} catch (MqttException e) {
			System.out.println("Erro de Conexão MQTT");
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
	
	public static MqttCallback createMqttCallback() {
		return new MqttCallback() {
			
			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				receivedMessage.append(" Topic<" + topic + "> \n");
				receivedMessage.append(" Message: " + new String(message.getPayload()) + " \n");
			}
			
			@Override
			public void connectionLost(Throwable e) {
				e = new Exception("Conexão Perdida!");
				e.printStackTrace();
			}

			@Override
			public void deliveryComplete(IMqttDeliveryToken deliveryToken) {
				System.out.println("Pacote entregue...");
			}
			
			@Override
			public String toString() {
				return receivedMessage.toString();
			}
		};
	}
	
	public MqttConnectOptions createConnectOptions() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(true);
		options.setCleanSession(true);
		options.setConnectionTimeout(10);
		return options;
	}
	
	public static String getReceivedMessage() {
		return receivedMessage.toString();
	}

	public static void close() throws MqttException {
		subscriber.disconnect();
		subscriber.close();
	}

	
}
