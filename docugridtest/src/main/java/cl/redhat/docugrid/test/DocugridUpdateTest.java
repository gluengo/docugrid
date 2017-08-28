package cl.redhat.docugrid.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import cl.redhat.docugrid.main.DocugridResponse;
import cl.redhat.docugrid.util.DocugridUtil;

/**
 * Clase de test que permite actualizar un documento en repositorio docugrid
 * @author Luz Mestre
 *
 */
public class DocugridUpdateTest {

	public static void main(String[] args) {
		//datos de entrada necesarios para actualizar un documento: url, id del documento y binario en base64
		String urlRest = DocugridUtil.docugridProperty("urlactualizar");
		DocugridResponse docugridResponse = new DocugridResponse();
		
		docugridResponse.setId("621826e6-0ae2-4d66-8054-a0e78178e2c8");
		
		File file = new File("/home/luz/atta/docugrid/testcases/3.pdf");

		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(file.toPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String filestring = new String(Base64.getEncoder().encode(bytes));
		docugridResponse.setBin(filestring);

		
		// Invoca actualizar documento
		String response = DocugridUtil.actualizarDocumento(docugridResponse, urlRest);
		//imprime la respuesta
		System.out.println(response);

	}
}
