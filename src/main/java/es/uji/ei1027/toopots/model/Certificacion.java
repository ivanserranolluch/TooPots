package es.uji.ei1027.toopots.model;

public class Certificacion {
	private int id_certificacion;
	private String estado;
	private String id_monitor;
	private String rutaCertificado;
	
	
	public String getRutaCertificado() {
		return rutaCertificado;
	}
	public void setRutaCertificado(String rutaCertificado) {
		this.rutaCertificado = rutaCertificado;
	}
	public int getId_certificacion() {
		return id_certificacion;
	}
	public void setId_certificacion(int id_certificacion) {
		this.id_certificacion = id_certificacion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getId_monitor() {
		return id_monitor;
	}
	public void setId_monitor(String id_monitor) {
		this.id_monitor = id_monitor;
	}
}
