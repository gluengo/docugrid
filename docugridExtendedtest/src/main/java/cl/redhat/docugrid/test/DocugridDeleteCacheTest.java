package cl.redhat.docugrid.test;

import cl.redhat.docugrid.main.DocugridResponse;
import cl.redhat.docugrid.util.DocugridUtil;

/**
 * 
 * @author Luz Mestre
 *
 */
public class DocugridDeleteCacheTest {

	public static void main(String[] args) {
		String urlRest = DocugridUtil.docugridProperty("urlborrarcache");
		DocugridResponse docugridResponse = new DocugridResponse();
		docugridResponse.setId("0");

		String response = DocugridUtil.eliminarcache(docugridResponse, urlRest);
		System.out.println(response);

	}
}
