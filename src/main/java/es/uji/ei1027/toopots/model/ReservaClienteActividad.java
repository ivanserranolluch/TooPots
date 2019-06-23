package es.uji.ei1027.toopots.model;

import java.sql.Time;
import java.sql.Date;

public class ReservaClienteActividad {
	
	private String dni;
	private String nombreCliente;
	

	private String email;
	private Date fechaNacimiento;
	private String strFechaNacimiento;
	private String sexo;
	
	private int id_reserva;
	private String estadoPago;
	private int numAsistentes;
	private int precioPersona;
	private int id_actividad;

	
	private String nombreActividad;
	private String descripcion;
	private int duracionDias;
	private Date fechaActividad;
	private int precio;
	private int minAsistentes;
	private int maxAsistentes;
	private String lugar;
	private String puntoEncuentro;
	private Time horaEncuentro;
	private String textoCliente;
	private String estado;
	private int id_tipoActividad;

	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getStrFechaNacimiento() {
		return strFechaNacimiento;
	}
	public void setStrFechaNacimiento(String strFechaNacimiento) {
		this.strFechaNacimiento = strFechaNacimiento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
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
	public int getId_actividad() {
		return id_actividad;
	}
	public void setId_actividad(int id_actividad) {
		this.id_actividad = id_actividad;
	}
	public String getNombreActividad() {
		return nombreActividad;
	}
	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}
	public Date getFechaActividad() {
		return fechaActividad;
	}
	public void setFechaActividad(Date fechaActividad) {
		this.fechaActividad = fechaActividad;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public int getMinAsistentes() {
		return minAsistentes;
	}
	public void setMinAsistentes(int minAsistentes) {
		this.minAsistentes = minAsistentes;
	}
	public int getMaxAsistentes() {
		return maxAsistentes;
	}
	public void setMaxAsistentes(int maxAsistentes) {
		this.maxAsistentes = maxAsistentes;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getPuntoEncuentro() {
		return puntoEncuentro;
	}
	public void setPuntoEncuentro(String puntoEncuentro) {
		this.puntoEncuentro = puntoEncuentro;
	}
	public Time getHoraEncuentro() {
		return horaEncuentro;
	}
	public void setHoraEncuentro(Time horaEncuentro) {
		this.horaEncuentro = horaEncuentro;
	}
	public String getTextoCliente() {
		return textoCliente;
	}
	public void setTextoCliente(String textoCliente) {
		this.textoCliente = textoCliente;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
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
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getDuracionDias() {
		return duracionDias;
	}
	public void setDuracionDias(int duracionDias) {
		this.duracionDias = duracionDias;
	}
}
