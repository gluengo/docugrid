package cl.redhat.docugrid.main;

/**
 * Contiene los parametros de entrada y salida de la apir REST docugrid 
 * @author Luz Mestre
 *
 */
public class DocugridResponse {
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
		String out = "";
		if (!getId().equals("")) {
			out = out + "\"id\": \"" + getId();
		}
		if (!getResult().equals("")) {
			if (out.equals("")) {
				out = out + "\"result\": \"" + getId();
			} else {
				out = out + ",\"result\": \"" + getId();
			}
		}
		if (!getErrormessage().equals("")) {
			if (out.equals("")) {
				out = out + "\"errormessage\": \"" + getId();
			} else {
				out = out + ",\"errormessage\": \"" + getId();
			}
		}
		if (!getBin().equals("")) {
			if (out.equals("")) {
				out = out + "\"bin\": \"" + getId();
			} else {
				out = out + ",\"bin\": \"" + getId();
			}
		}
		if (!getFilename().equals("")) {
			if (out.equals("")) {
				out = out + "\"filename\": \"" + getFilename();
			} else {
				out = out + ",\"filename\": \"" + getFilename();
			}
		}

		return "{ " + out + "\"} ";
	}

}
