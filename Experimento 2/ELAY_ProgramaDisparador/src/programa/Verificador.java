package programa;

public class Verificador implements Runnable{

	private boolean activo;
	private int time;
	private int cantLost;
	private int actLost;
	private Reporte reporte;
	private Notificador notificador;
	
	public Verificador(int time, int cantLost, Reporte reporte, Notificador notificador) {
		activo = true;
		actLost = 0;
		this.time = time;
		this.cantLost = cantLost;
		this.reporte = reporte;
		this.notificador = notificador;
	}
	
	@Override
	public void run() {
		while(activo) {
			if(reporte.Reportar(time)) actLost = 0; else actLost++;
			if(actLost == cantLost) {
				activo = false;
				notificador.notificar();
			}
		}
		System.gc();
	}
	
}
