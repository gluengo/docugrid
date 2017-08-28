package cl.redhat.docugrid.main;

import java.io.Serializable;

/**
 * Contiene la informacion basica de un documento
 * 
 * @author Luz Mestre
 *
 */
public class Document implements Serializable {

	private String bin = "";
	private String version = "";
	private String filename = "";

	public String toString() {
		return "{ \"bin\": \"" + getBin() + "\", \"version\": \"" + getVersion() + "\", \"filename\": \""
				+ getFilename() + "\"} ";
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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
