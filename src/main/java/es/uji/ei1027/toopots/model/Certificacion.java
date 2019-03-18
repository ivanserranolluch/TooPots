package es.uji.ei1027.toopots.model;

public class Certificacion {
	private int id_certificacion;
	private String estado;
	private int id_monitor;
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
	public int getId_monitor() {
		return id_monitor;
	}
	public void setId_monitor(int id_monitor) {
		this.id_monitor = id_monitor;
	}
}
