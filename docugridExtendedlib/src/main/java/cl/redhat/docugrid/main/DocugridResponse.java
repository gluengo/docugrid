package cl.redhat.docugrid.main;

import java.io.Serializable;

/**
 *  Clase util cliente docugrid
 * 
 * @author Luz Mestre
 *
 */
public class DocugridResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 779771979773941542L;
	private String result = "";
	private String id = "";
	private String errormessage = "";
	private String bin = "";
	private String filename = "";

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

	

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		return "{ "+"\"bin\": \"" + getBin() + "\", \"id\": \"" + getId() 
				+ "\", \"result\": \"" + getResult()
				+ "\", \"filename\": \"" + getFilename()
				+ "\", \"errormessage\": \"" + getErrormessage() +"\"} ";
	}

}
