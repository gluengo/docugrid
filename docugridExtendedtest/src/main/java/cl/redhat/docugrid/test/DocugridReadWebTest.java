package cl.redhat.docugrid.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
public class DocugridReadWebTest {

	public static void main(String[] args) {
		String urlRest = DocugridUtil.docugridProperty("urlleerversiones");
		DocugridResponse docugridResponse = new DocugridResponse();
		docugridResponse.setId("2d89175a-9d6c-4d5b-ab2c-5d882e92eec3");

		try {

			// invoca leer documento
			String response = DocugridUtil.leerVersiones(docugridResponse, urlRest);

			// Genera archivo a partir de la salida
			
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(response);

			String result = (String) obj.get("result");
			if (result.equals("error")) {

				System.out.println(obj.toString());

			} else {
				JSONArray jsonArray = (JSONArray) obj.get("versiones");
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject_i = (JSONObject) jsonArray.get(i);

					String bin = (String) jsonObject_i.get("bin");
					byte[] bytes = Base64.getDecoder().decode(bin);
					String version = (String) jsonObject_i.get("version");
					String filename = (String) jsonObject_i.get("filename");
                    System.out.println("Filename:"+filename+" version:"+version);
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
			}
			return;

		} catch (ParseException e1) {

			e1.printStackTrace();
		}

	}
}
