package es.uji.ei1027.toopots.model;

import java.sql.Date;

public class Actividad {
	private int id_actividad;
	private String nombre;
	private String descripcion;
	private int duracionDias;
	private Date fecha;
	private int precio;
	private int minAsistentes;
	private int maxAsistentes;
	private String lugar;
	private String puntoEncuentro;
	private int horaEncuentro;
	private String textoCliente;
	private String estado;
	public int getId_actividad() {
		return id_actividad;
	}
	public void setId_actividad(int id_actividad) {
		this.id_actividad = id_actividad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
	public int getHoraEncuentro() {
		return horaEncuentro;
	}
	public void setHoraEncuentro(int horaEncuentro) {
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
}
