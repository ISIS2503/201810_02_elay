package progama;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.mail.smtp.SMTPTransport;



public class Client {

	public static ArrayList<LinkedList<Countent>> lista;
	public Interfaz interfaz;
	static {
		lista = new ArrayList<LinkedList<Countent>>();
		for(int i = 0; i < 4; i++) lista.add(new LinkedList<Countent>());
	}

	public Client(Interfaz i) {
		this.interfaz = i;
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
		add(topic, new Countent(elmPrin.get("timestamp").getAsString(), new Countent.Info(info.get("alertaId").getAsInt(),
				info.get("mensajeAlerta").getAsString(),
				info.get("idDispositivo").getAsInt(),
				info.get("torre").getAsInt(),
				info.get("apto").getAsInt())));
	}

	private void add(String topic, Countent temp) {
		new EnvialMail(temp).start();
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

	public static class EnvialMail extends Thread{
		private Countent c;
		public EnvialMail(Countent c) { this.c = c; }
		@Override
		public void run() {
			try { MailSender.enviarCorreo("Mensajed de alerta", "ws.duarte@uniandes.edu.co", c.info.mensajeAlerta); }
			catch (Exception e) { e.printStackTrace(); }
		}
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

	public static void main(String[] args) throws Exception {
		new Client(new Interfaz());
	}

	// El código no es mio, pero no encuentro el Link de donde lo saque.
	public static class MailSender{

		public static void enviarCorreo(String asunto, String destinatarios,  String contenido) throws Exception {
			Properties properties = new Properties();
			properties.load(new FileReader(new File("./data/hotmail.properties")));
			Session session = Session.getInstance(properties, null);
			Message mensaje = new MimeMessage(session);
			mensaje.setFrom(new InternetAddress(properties.getProperty("mail.from")));
			StringTokenizer emailsSt = new StringTokenizer(destinatarios,";,");
			while (emailsSt.hasMoreTokens()) {
				String email=emailsSt.nextToken();
				try{
					mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
				}catch(Exception ex){ex.printStackTrace();}
			}
			mensaje.setSubject(asunto);
			mensaje.setSentDate(new Date(1,1,1)); 
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(contenido);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			mensaje.setContent(multipart);
			SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
			try {
				//conectar al servidor
				transport.connect(properties.getProperty("mail.user"), properties.getProperty("mail.password"));
				//enviar el mensaje
				transport.sendMessage(mensaje, mensaje.getAllRecipients());
			} finally {
				//cerrar la conexión 
				transport.close();
			}
		}
	}
}
