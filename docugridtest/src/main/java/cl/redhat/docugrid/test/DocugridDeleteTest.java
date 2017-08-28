package cl.redhat.docugrid.test;

import cl.redhat.docugrid.main.DocugridResponse;
import cl.redhat.docugrid.util.DocugridUtil;

/**
 * 
 * Clase de ejemplo para eliminar un documento
 * 
 * @author Luz Mestre
 *
 */
public class DocugridDeleteTest {

	public static void main(String[] args) {
		// datos de entrada: url y id del documento a elliminar
		String urlRest = DocugridUtil.docugridProperty("urldelete");
		DocugridResponse docugridResponse = new DocugridResponse();
		docugridResponse.setId("621826e6-0ae2-4d66-8054-a0e78178e2c8");
		// llamada a eliminar documento
		String response = DocugridUtil.eliminarDocumento(docugridResponse, urlRest);
		//imprimir salida
		System.out.println(response);
	 

	}
}
