package es.uji.ei1027.toopots.model;

import java.sql.Date;

public class PreferenciasCliente {
	private int id_tipoActividad;
	private String dni;
	private Date fecha;
	
	public int getId_tipoActividad() {
		return id_tipoActividad;
	}
	public void setId_tipoActividad(int id_tipoActividad) {
		this.id_tipoActividad = id_tipoActividad;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
