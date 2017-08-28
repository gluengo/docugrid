package cl.redhat.docugrid.model;

import java.io.Serializable;

/**
 * 
 * @author Luz Mestre
 *
 */
public class Document implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String nombre="";
	private String tipo="";

	private String fecha="";
	private String usuario="";
	private String bin="";
	private String version="";

	public String toString() {
		return "{ \"nombre\": \"" + getNombre() + "\", \"tipo\": \"" + getTipo()
				+ "\", \"fecha\": \"" + getFecha() + "\", \"usuario\": \"" + getUsuario()
				+ "\", \"bin\": \"" + getBin()
				+ "\", \"version\": \"" + getVersion() + "\"} ";
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
