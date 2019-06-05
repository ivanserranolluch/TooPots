package es.uji.ei1027.toopots.model;

import java.util.Date;

public class Cliente {
	private String dni;
	private String nombre;
	private String email;
	private Date fechaNacimiento;
	private String strFechaNacimiento;
	private String sexo;
	private String passwd;
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
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
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getStrFechaNacimiento() {
		return strFechaNacimiento;
	}
	@SuppressWarnings("deprecation")
	public void setStrFechaNacimiento(String strFechaNacimiento) {
		this.strFechaNacimiento = strFechaNacimiento;
		String[] date = strFechaNacimiento.split("-");
		this.fechaNacimiento = new Date(Integer.valueOf(date[0]), 
				Integer.valueOf(date[1]), 
				Integer.valueOf(date[2]));
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
}
