package cl.redhat.docugrid.main;

import java.io.Serializable;

/**
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
