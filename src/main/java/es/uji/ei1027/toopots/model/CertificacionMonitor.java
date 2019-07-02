package es.uji.ei1027.toopots.model;

public class CertificacionMonitor {
	private int id_certificacion;
	private String estado;
	private String id_monitor;
	private String nombre;
    private String domicilio;
    private String email;
    private String iban;
    private String estadoMonitor;
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getEstadoMonitor() {
		return estadoMonitor;
	}
	public void setEstadoMonitor(String estadoMonitor) {
		this.estadoMonitor = estadoMonitor;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	private String passwd;
}
