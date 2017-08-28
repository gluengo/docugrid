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
		
		docugridResponse.setId("2d89175a-9d6c-4d5b-ab2c-5d882e92eec3");
		docugridResponse.setFilename("2.pdf");
		
		
		File file = new File("/opt/target/2.pdf");

		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(file.toPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String filestring = new String(Base64.getEncoder().encode(bytes));
		docugridResponse.setBin(filestring);
		//docugridResponse.setBin("Formato invalido");
		
		// Invoca actualizar documento
		String response = DocugridUtil.actualizarDocumento(docugridResponse, urlRest);
		//imprime la respuesta
		System.out.println(response);

	}
}
