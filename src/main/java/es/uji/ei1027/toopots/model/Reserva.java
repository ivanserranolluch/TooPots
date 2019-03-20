package es.uji.ei1027.toopots.model;

import java.sql.Date;

public class Reserva {
	private int id_reserva;
	private String estadoPago;
	private Date fecha;
	private int numAsistentes;
	private int precioPersona;
	private int numTransacciones;
	private int id_actividad;
	private String dni;
	
	public int getId_reserva() {
		return id_reserva;
	}
	public void setId_reserva(int id_reserva) {
		this.id_reserva = id_reserva;
	}
	public String getEstadoPago() {
		return estadoPago;
	}
	public void setEstadoPago(String estadoPago) {
		this.estadoPago = estadoPago;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getNumAsistentes() {
		return numAsistentes;
	}
	public void setNumAsistentes(int numAsistentes) {
		this.numAsistentes = numAsistentes;
	}
	public int getPrecioPersona() {
		return precioPersona;
	}
	public void setPrecioPersona(int precioPersona) {
		this.precioPersona = precioPersona;
	}
	public int getNumTransacciones() {
		return numTransacciones;
	}
	public void setNumTransacciones(int numTransacciones) {
		this.numTransacciones = numTransacciones;
	}
	public int getId_actividad() {
		return id_actividad;
	}
	public void setId_actividad(int id_actividad) {
		this.id_actividad = id_actividad;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	

}
