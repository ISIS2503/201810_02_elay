package elay.ProgrmaP;

import java.util.ArrayList;
import java.util.LinkedList;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import entidad.Countent;
import interfaz.Interfaz;

public class Client {

	public static ArrayList<LinkedList<Countent>> lista;
	public Interfaz interfaz;
	
	static {
		lista = new ArrayList<LinkedList<Countent>>();
		for(int i = 0; i < 4; i++) lista.add(new LinkedList<Countent>());
	}

	public Client(Interfaz interfaz) {
		this.interfaz = interfaz;
		try {
			String myTopic = "#";
			MqttClient sampleClient = new MqttClient("tcp://172.24.42.23:8083", "0");
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			sampleClient.setCallback(new MqttCallback() {
				public void connectionLost(Throwable cause) {}
				public void messageArrived(String topic, MqttMessage message) throws Exception { imprimir(topic, message.toString()); }
				public void deliveryComplete(IMqttDeliveryToken token) {}
			});
			sampleClient.connect(connOpts);
			sampleClient.subscribe(myTopic, 0);
		} catch(MqttException e) { e.printStackTrace(); }
	}

	private void imprimir(String topic, String msg) {
		JsonObject elmPrin = new JsonParser().parse(msg).getAsJsonObject();
		JsonObject info = elmPrin.get("info").getAsJsonObject();
		add(topic, new Countent("", elmPrin.get("timestamp").getAsString(), 
				info.get("alertaId").getAsInt(), 
				info.get("mensajeAlerta").getAsString(), 
				info.get("idDispositivo").getAsInt(), 
				info.get("torre").getAsInt(), 
				info.get("apto").getAsInt()));
	}

	private void add(String topic, Countent temp) {
		String[] s = topic.split("/");
		if(s[0].equals("propietario")) {
			lista.get(0).add(temp);
			new Agregar(0,temp).start();
		}
		if(s[0].equals("seguridad")) {
			lista.get(1).add(temp);
			new Agregar(1,temp).start();
		}
		if(s[0].equals("administrador")) {
			lista.get(2).add(temp);
			new Agregar(2,temp).start();
		}
		if(s[0].equals("yale")) {
			lista.get(3).add(temp);
			new Agregar(3,temp).start();
		}
		System.out.println(s[0].toUpperCase()+": \n"+temp.toText());
	}

	

	public class Agregar extends Thread {
		private Countent c;
		private int i;
		public Agregar(int i, Countent c) { this.c = c; this.i = i; }
		@Override
		public void run() { interfaz.add(i, c);	}
	}
	
}
