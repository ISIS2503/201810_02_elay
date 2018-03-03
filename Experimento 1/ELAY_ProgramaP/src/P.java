
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class P  {

	
	//Tomado de:
	// https://stackoverflow.com/questions/42378119/how-to-subscribe-to-a-mqtt-topic-and-print-received-messages-on-eclipse-java
	
	public static void main(String[] args) {
		String myTopic = "propietario/#";
		try {
			MqttClient sampleClient = new MqttClient("tcp://172.24.42.23:8083", "0");
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);

			sampleClient.setCallback(new MqttCallback() {
				public void connectionLost(Throwable cause) {}

				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println(message.toString());
				}
				public void deliveryComplete(IMqttDeliveryToken token) {}
			});

			sampleClient.connect(connOpts);
			sampleClient.subscribe(myTopic, 0);

		} catch(MqttException e) { e.printStackTrace(); }
	}
}