package cl.redhat.docugrid.main;

import java.io.Serializable;

/**
 * Clase Document, contiene la informacion almacenada del tipo Document
 * 
 * @author Luz Mestre
 *
 */
public class Document implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 251370920655903719L;
	/**
	 * 
	 */

	private String bin = "";
	private String version = "";

	public String toString() {
		return "{ \"bin\": \"" + getBin() + "\", \"version\": \"" + getVersion() + "\"} ";
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
