package es.uji.ei1027.toopots.model;

public class TipoActividadCertificacionMonitor {

	private int id; //tipoActividad
    private String nombre;
    private String nivel;
    private int id_certificacion;
	private String estado;
	private int id_monitor;
	private String nombreMonitor;
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
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
	public int getId_monitor() {
		return id_monitor;
	}
	public void setId_monitor(int id_monitor) {
		this.id_monitor = id_monitor;
	}
	public String getNombreMonitor() {
		return nombreMonitor;
	}
	public void setNombreMonitor(String nombreMonitor) {
		this.nombreMonitor = nombreMonitor;
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
	private String domicilio;
    private String email;
    private String iban;
    private String estadoMonitor;
    
}
