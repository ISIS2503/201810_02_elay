package programa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class Disparador {

	public static ArrayList<LinkedList<Countent>> lista;
	public Interfaz interfaz;
	static {
		lista = new ArrayList<LinkedList<Countent>>();
		for(int i = 0; i < 4; i++) lista.add(new LinkedList<Countent>());
	}

	public Disparador(Interfaz i) {
		this.interfaz = i;
		try {
			String myTopic = "#";
			MqttClient sampleClient = new MqttClient("tcp://172.24.42.23:8083", "0");
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			sampleClient.setCallback(new MqttCallback() {
				public void connectionLost(Throwable cause) {}
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					new Speed(message.toString()).start();
					imprimir(topic, message.toString()); 
				}
				public void deliveryComplete(IMqttDeliveryToken token) {}
			});
			sampleClient.connect(connOpts);
			sampleClient.subscribe(myTopic, 0);
		} catch(MqttException e) { e.printStackTrace(); }
	}

	private void imprimir(String topic, String msg) {
		JsonObject elmPrin = new JsonParser().parse(msg).getAsJsonObject();
		JsonObject info = elmPrin.get("info").getAsJsonObject();
		add(topic, new Countent(elmPrin.get("timestamp").getAsString(), new Countent.Info(info.get("alertaId").getAsInt(),
				info.get("mensajeAlerta").getAsString(),
				info.get("idDispositivo").getAsInt(),
				info.get("torre").getAsInt(),
				info.get("apto").getAsInt())));
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

	public static class Countent {
		String timestamp;
		Info info;

		public Countent(String time, Info info) {
			super();
			this.timestamp = time;
			this.info = info;
		}

		public static class Info {
			int alertaId;
			String mensajeAlerta;
			int idDispositivo;
			int torre;
			int apto;
			public Info(int alertaId, String mensajeAlerta, int idDispositivo, int torre, int apto) {
				super();
				this.alertaId = alertaId;
				this.mensajeAlerta = mensajeAlerta;
				this.idDispositivo = idDispositivo;
				this.torre = torre;
				this.apto = apto;
			}
			@Override
			public String toString() { return mensajeAlerta; }
			public String toText() { return "[alertaId=" + alertaId + ", mensajeAlerta=" + mensajeAlerta + ", idDispositivo="+ idDispositivo + ", torre=" + torre + ", apto=" + apto + "]"; }
		}
		@Override
		public String toString() { return timestamp + ": [ " + info + " ]"; }
		public String toText() { return "[timestamp=" + timestamp + ", info=" + info.toText() + "]"; }
	}

	public class Agregar extends Thread {
		private Countent c;
		private int i;
		public Agregar(int i, Countent c) { this.c = c; this.i = i; }
		@Override
		public void run() { interfaz.add(i, c);	}
	}

	@SuppressWarnings("serial")
	public static class Interfaz extends JFrame {
		private JTabbedPane pestanias=new JTabbedPane();
		private JPanel[] paneles;
		private JList<Countent>[] listas;
		@SuppressWarnings("unchecked")
		public Interfaz() {
			setSize(420,694);
			setResizable(true);
			setTitle("Observatorio de colas.");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			getContentPane().setBackground(Color.WHITE);
			listas = new JList[4];
			paneles = new JPanel[4];
			for(int i = 0; i < listas.length; i++) {
				paneles[i] = new JPanel();
				paneles[i].setLayout(new BorderLayout());
				paneles[i].add(new JScrollPane(listas[i] = crearLista()),BorderLayout.CENTER);
			}
			pestanias.addTab("Propietario", paneles[0]);
			pestanias.addTab("Seguridad", paneles[1]);
			pestanias.addTab("Administracion", paneles[2]);
			pestanias.addTab("Yale", paneles[3]);
			getContentPane().add(pestanias);
			setVisible(true);
		}
		public synchronized void add(int i, Countent c) {
			DefaultListModel<Countent> model = (DefaultListModel<Countent>) listas[i].getModel();
			model.addElement(c);
			this.repaint();
		}

		public JList<Countent> crearLista() {
			JList<Countent> ret = new JList<Countent>(new DefaultListModel<Countent>());
			ret.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {}			
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2) new Detalle(ret.getSelectedValue());
				}
			});
			return ret;
		}

		@SuppressWarnings("serial")
		public class Detalle extends JFrame {
			public Detalle(Countent c) {
				setSize(370,200);
				setResizable(true);
				setTitle("Observatorio de colas.");
				setLocationRelativeTo(null);
				getContentPane().setBackground(Color.WHITE);
				this.add(new JLabel("<html><body>Fecha y hora: "+c.timestamp+
						"<br>Informaci�n de la alerta:<br>"
						+ "&nbsp &nbsp Id de la alerta: "+c.info.alertaId
						+ "<br> &nbsp &nbsp	Mensaje de alerta: "+c.info.mensajeAlerta
						+ "<br> &nbsp &nbsp Dispositivo: "+c.info.idDispositivo
						+ "<br> &nbsp &nbsp Ubicaci�n:"
						+ "<br> &nbsp &nbsp &nbsp &nbsp Torre: "+c.info.torre

						+ "<br> &nbsp &nbsp &nbsp &nbsp Apartamento: "+c.info.apto+"</html></body>"));
				setVisible(true);
				this.addFocusListener(new FocusListener() {
					@Override
					public void focusLost(FocusEvent e) { dispose(); }
					@Override
					public void focusGained(FocusEvent e) {}
				});
			}
		}
	}

	public static class Speed extends Thread{
		private String msg;
		public Speed(String msg) { this.msg = msg; }
		@Override
		public void run() {	HTTP.post(msg); }
	}

	public static class HTTP {
		private static final String URL_SPEED = "http://172.24.42.23:8080/speed";
		private static final String URL_BATCH = "";
		public static void post (String msg) {
			try {
				URL url = new URL(URL_SPEED);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json");
				con.setDoOutput(true);
				OutputStream os = con.getOutputStream();
				os.write(msg.getBytes());
				os.flush();
				os.close();
				int responseCode = con.getResponseCode();
				System.out.println("Response Code :" + responseCode);
				BufferedReader reader = null;
				String json = null;
				reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuilder jsonSb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) jsonSb.append(line);
				json = jsonSb.toString();
				System.out.println(json);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new Disparador(new Interfaz());
	}

}
