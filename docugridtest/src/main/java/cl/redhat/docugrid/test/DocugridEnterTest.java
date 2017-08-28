package cl.redhat.docugrid.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import cl.redhat.docugrid.main.Document;
import cl.redhat.docugrid.util.DocugridUtil;

/**
 * Clase de prueba para ingresar documento
 * Se debe modificar la ruta al archivo
 * @author Luz Mestre
 *
 */
public class DocugridEnterTest {

	public static void main(String[] args) {
		// preparacion de la entrada a docugrid
		String urlRest = DocugridUtil.docugridProperty("urlingresar");
		Document document = new Document();
		//ruta valida a archivos de prueba
		File file = new File("/home/luz/atta/docugrid/testcases/1.pdf");
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(file.toPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String filestring = new String(Base64.getEncoder().encode(bytes));
		document.setBin(filestring);

		// invoca insertar documento
		String respuesta = DocugridUtil.insertarDocumento(document, urlRest);
		//imprimir salida
		System.out.println("salida:"+respuesta);

	}
}
