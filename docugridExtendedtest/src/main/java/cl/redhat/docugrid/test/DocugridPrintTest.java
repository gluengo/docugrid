package cl.redhat.docugrid.test;

import cl.redhat.docugrid.main.DocugridResponse;
import cl.redhat.docugrid.util.DocugridUtil;

/**
 * 
 * @author Luz Mestre
 *
 */
public class DocugridPrintTest {

	public static void main(String[] args) {
		String urlRest = DocugridUtil.docugridProperty("urlleertodos");
		DocugridResponse docugridResponse = new DocugridResponse();
		docugridResponse.setId("0");

		// invoca listar documentos
		String response = DocugridUtil.listarDocumentos(docugridResponse, urlRest);
		System.out.println(response);

		return;

	}
}
