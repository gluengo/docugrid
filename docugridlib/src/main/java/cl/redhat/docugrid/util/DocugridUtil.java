package cl.redhat.docugrid.util;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;

import cl.redhat.docugrid.main.DocugridResponse;
import cl.redhat.docugrid.main.Document;

/**
 * Clase util cliente docugrid, expone los metodos eliminarDocumento
 * insertarDocumento leerDocumento actualizarDocumento
 * 
 * @author Luz Mestre
 *
 */
public class DocugridUtil {
	private static final String PROPERTIES_FILE = "docugrid.properties";

	/**
	 * Metodo estatico que lee congiguraciones de docugrid desde la variable de
	 * ambiente JBOSS_HOME. Si se utiliza un editor como eclipse, se debe
	 * declarar en Run Configurations >Environment
	 * 
	 * @param name
	 * @return
	 */
	public static String docugridProperty(String name) {
		Properties props = new Properties();
		try {
			String OS = System.getProperty("os.name").toLowerCase();
			InputStream inputStream;
			String path = "";
			if (OS.indexOf("nux") >= 0) {
				path = System.getenv("JBOSS_HOME") + "/" + PROPERTIES_FILE;
				inputStream = new FileInputStream(path);
			} else {
				path = System.getenv("JBOSS_HOME") + "\\" + PROPERTIES_FILE;
				inputStream = new FileInputStream(path);
			}
			props.load(inputStream);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		return props.getProperty(name);
	}

	/**
	 * Metodo cliente que invoca la insercion de un documento, recibe como
	 * parametro
	 * 
	 * @param documento,
	 *            requiere el documento codificado con java.util.Base64 ejemplo:
	 *            File file = new
	 *            File("/home/luz/atta/docugrid/testcases/1.pdf"); bytes =
	 *            Files.readAllBytes(file.toPath()); Document document = new
	 *            Document(); String filestring = new
	 *            String(Base64.getEncoder().encode(bytes));
	 *            docuemnt.setBin(filestring);
	 * 
	 * @param urlRest,
	 *            url del servicio rest
	 * @return
	 */
	public static String insertarDocumento(Document documento, String urlRest) {
		String response = "";
		try {

			int read = 0;
			byte[] buffer = new byte[1024 * 8];

			URL url;
			url = new URL(urlRest);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");

			connection.setDoOutput(true);
			String str = docugridProperty("username") + ":" + docugridProperty("password");
			String bytesEncoded = Base64.getEncoder().encodeToString(str.getBytes());
			connection.setRequestProperty("Authorization", "Basic " + bytesEncoded);

			BufferedOutputStream output = new BufferedOutputStream(connection.getOutputStream());
			output.write((documento.toString()).getBytes());

			output.close();

			connection.connect();
			InputStream responseBodyStream = connection.getInputStream();
			StringBuffer responseBody = new StringBuffer();
			while ((read = responseBodyStream.read(buffer)) != -1) {
				responseBody.append(new String(buffer, 0, read));
			}
			connection.disconnect();
			response = responseBody.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Metodo cliente que invoca la lectura de un documento en docugrid
	 * 
	 * @param docugridResponse,
	 *            contiene el id del documento
	 * @param urlRest,
	 *            url del servicio rest de lectura
	 * @return
	 */
	public static String leerDocumento(DocugridResponse docugridResponse, String urlRest) {
		String response = "";
		try {

			int read = 0;
			byte[] buffer = new byte[1024 * 8];

			URL url;
			url = new URL(urlRest);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");

			connection.setDoOutput(true);
			String str = docugridProperty("username") + ":" + docugridProperty("password");
			String bytesEncoded = Base64.getEncoder().encodeToString(str.getBytes());
			connection.setRequestProperty("Authorization", "Basic " + bytesEncoded);

			BufferedOutputStream output = new BufferedOutputStream(connection.getOutputStream());

			output.write((docugridResponse.toString()).getBytes());

			output.close();

			connection.connect();
			InputStream responseBodyStream = connection.getInputStream();
			StringBuffer responseBody = new StringBuffer();
			while ((read = responseBodyStream.read(buffer)) != -1) {
				responseBody.append(new String(buffer, 0, read));
			}
			connection.disconnect();
			response = responseBody.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * Metodo cliente que permite eliminar un documento de docugrid
	 * 
	 * @param docugridResponse
	 *            contiene el id del documento a eliminar
	 * @param urlRest,
	 *            url del servicio rest que elimina el documento
	 * @return
	 */
	public static String eliminarDocumento(DocugridResponse docugridResponse, String urlRest) {
		String response = "";
		try {

			int read = 0;
			byte[] buffer = new byte[1024 * 8];

			URL url;
			url = new URL(urlRest);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");

			connection.setDoOutput(true);

			String str = docugridProperty("username") + ":" + docugridProperty("password");
			String bytesEncoded = Base64.getEncoder().encodeToString(str.getBytes());
			connection.setRequestProperty("Authorization", "Basic " + bytesEncoded);

			BufferedOutputStream output = new BufferedOutputStream(connection.getOutputStream());
			output.write((docugridResponse.toString()).getBytes());

			output.close();

			connection.connect();
			InputStream responseBodyStream = connection.getInputStream();
			StringBuffer responseBody = new StringBuffer();
			while ((read = responseBodyStream.read(buffer)) != -1) {
				responseBody.append(new String(buffer, 0, read));
			}
			connection.disconnect();
			response = responseBody.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Metodo cliente que permite actualizar un documento en docugrid
	 * 
	 * @param redhatDocument,
	 *            contiene el id del documento
	 * @param urlRest
	 * @return
	 */
	public static String actualizarDocumento(DocugridResponse docugridResponse, String urlRest) {
		String response = "";
		try {
			int read = 0;
			byte[] buffer = new byte[1024 * 8];

			URL url;
			url = new URL(urlRest);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");

			connection.setDoOutput(true);

			String str = docugridProperty("username") + ":" + docugridProperty("password");
			String bytesEncoded = Base64.getEncoder().encodeToString(str.getBytes());
			connection.setRequestProperty("Authorization", "Basic " + bytesEncoded);

			BufferedOutputStream output = new BufferedOutputStream(connection.getOutputStream());

			output.write((docugridResponse.toString()).getBytes());

			output.close();

			connection.connect();
			InputStream responseBodyStream = connection.getInputStream();
			StringBuffer responseBody = new StringBuffer();
			while ((read = responseBodyStream.read(buffer)) != -1) {
				responseBody.append(new String(buffer, 0, read));
			}
			connection.disconnect();
			response = responseBody.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}

}
