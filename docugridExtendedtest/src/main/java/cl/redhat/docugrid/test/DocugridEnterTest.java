package cl.redhat.docugrid.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import cl.redhat.docugrid.main.Document;
import cl.redhat.docugrid.util.DocugridUtil;

/**
 * Clase de prueba para ingresar documento Se debe modificar la ruta al archivo
 * 
 * @author Luz Mestre
 *
 */
public class DocugridEnterTest {

	public static void main(String[] args) {
		// preparacion de la entrada a docugrid
		String urlRest = DocugridUtil.docugridProperty("urlingresar");
		Document document = new Document();
		// ruta valida a archivos de prueba
		File file = new File("/opt/target/1.pdf");
		document.setFilename("1.pdf");
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(file.toPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String filestring = new String(Base64.getEncoder().encode(bytes));

		document.setBin(filestring);

		for (int i = 0; i < 100; i++) {
			String respuesta = DocugridUtil.insertarDocumento(document, urlRest);
			// imprimir salida
			System.out.println(i+":"+respuesta);
		}
	}
}
