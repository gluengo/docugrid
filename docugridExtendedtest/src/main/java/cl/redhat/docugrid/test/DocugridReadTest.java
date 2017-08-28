package cl.redhat.docugrid.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import cl.redhat.docugrid.main.DocugridResponse;
import cl.redhat.docugrid.util.DocugridUtil;

/**
 * Clase de prueba para Leer documento
 * 
 * <P>
 * Ingresa un id de documento e invoca
 * DocugridUtil.leerDocumento(docugridResponse, urlRest);
 * <P>
 * La salida la escribe en un archivo en disco
 * 
 * @author Luz Mestre
 *
 */
public class DocugridReadTest {

	public static void main(String[] args) {
		// datos de entrada pata leer documento: url y id del documento
		String urlRest = DocugridUtil.docugridProperty("urlleer");
		DocugridResponse docugridResponse = new DocugridResponse();
		docugridResponse.setId("2d89175a-9d6c-4d5b-ab2c-5d882e92eec3");

		try {

			// invoca leer documento
			String response = DocugridUtil.leerDocumento(docugridResponse, urlRest);

			// Genera archivo a partir de la salida para visualizar el archivo
			
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(response);

			String result = (String) obj.get("result");
			if (result.equals("error")) {
				String message = (String) obj.get("errormessage");
				System.out.println(obj.toString());

			} else {

				String bin = (String) obj.get("bin");
				String filename = (String) obj.get("filename");

				System.out.println("result: " + result);
				byte[] bytes = Base64.getDecoder().decode(bin);
				//ruta valida con permisos para escribir archivo
				File file = new File("/opt/target/out/" + filename);
				FileOutputStream fop;
				try {
					fop = new FileOutputStream(file);
					fop.write(bytes);
					fop.flush();
					fop.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			return;

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
}
