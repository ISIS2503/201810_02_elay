package programa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;

public class HttpClientTutorial {

	private static String url = "http://www.apache.org/";
	
	public static void get() {
		HttpURLConnection connection = null;
	    BufferedReader reader = null;
	    String json = null;
	    try {
	      URL resetEndpoint = new URL(url);
	      connection = (HttpURLConnection) resetEndpoint.openConnection();
	      connection.setRequestMethod("GET");

	      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      StringBuilder jsonSb = new StringBuilder();
	      String line = null;
	      while ((line = reader.readLine()) != null) {
	        jsonSb.append(line);
	      }
	      json = jsonSb.toString();
	      System.out.println(json);
	      int responseCode = connection.getResponseCode();
	      System.out.println(responseCode);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	static String prueba = "{\"timestamp\":\"2018-03-22T18:11:31.638Z\",\"info\":{\"alertaId\":\"3\",\"mensajeAlerta\":\"Detección de persona sospechosa.\",\"idDispositivo\":\"5\",\"torre\":\"5\",\"apto\":\"5\"},\"usser\":[1,1,0,0]}";
	
	public static void post () {
		try {
		      URL url = new URL("http://localhost:8080/speed");
		      HttpURLConnection con = (HttpURLConnection) url.openConnection();
		      con.setRequestMethod("POST");
		      con.setRequestProperty("Content-Type", "application/json");
		      con.setDoOutput(true);

		      OutputStream os = con.getOutputStream();

		      os.write(prueba.getBytes());
		      os.flush();
		      os.close();

		      int responseCode = con.getResponseCode();

		      System.out.println("Response Code :" + responseCode);
		      BufferedReader reader = null;
			    String json = null;
		      reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		      StringBuilder jsonSb = new StringBuilder();
		      String line = null;
		      while ((line = reader.readLine()) != null) {
		        jsonSb.append(line);
		      }
		      json = jsonSb.toString();
		      System.out.println(json);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		post();
	}
}

