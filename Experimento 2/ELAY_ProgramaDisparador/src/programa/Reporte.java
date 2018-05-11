package programa;

@FunctionalInterface 
public interface Reporte 
{
	 public boolean Reportar(int time);
	 
	 public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
}
