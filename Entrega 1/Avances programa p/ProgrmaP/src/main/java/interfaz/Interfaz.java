package interfaz;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import entidad.Countent;

@SuppressWarnings("serial")
public class Interfaz extends JFrame {
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
	public static class Detalle extends JFrame {
		public Detalle(Countent c) {
			setSize(370,200);
			setResizable(true);
			setTitle("Observatorio de colas.");
			setLocationRelativeTo(null);
			getContentPane().setBackground(Color.WHITE);
			this.add(new JLabel("<html><body>Fecha y hora: "+c.timestamp+
					"<br>Informaci�n de la alerta:<br>"
					+ "&nbsp &nbsp Id de la alerta: "+c.alertaId
					+ "<br> &nbsp &nbsp	Mensaje de alerta: "+c.mensajeAlerta
					+ "<br> &nbsp &nbsp Dispositivo: "+c.idDispositivo
					+ "<br> &nbsp &nbsp Ubicaci�n:"
					+ "<br> &nbsp &nbsp &nbsp &nbsp Torre: "+c.torre
					
					+ "<br> &nbsp &nbsp &nbsp &nbsp Apartamento: "+c.apto+"</html></body>"));
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
