package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.toopots.model.CertificacionMonitor;
import es.uji.ei1027.toopots.model.Reserva;

@Repository
public class CertificacionMonitorDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	public List<CertificacionMonitor> listCertificacionMonitor() {
		try {
            return jdbcTemplate.query("SELECT c.id_certificacion, c.estado, c.id_monitor, m.nombre, m.email, m.iban, m.domicilio from monitor m join certificacion c using(id_monitor) where c.estado='pendiente'",
                    new CertificacionMonitorRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<CertificacionMonitor>();
        }
    }
	
	public List<CertificacionMonitor> listCertificacionMonitorAceptadas() {
		try {
            return jdbcTemplate.query("SELECT c.id_certificacion, c.estado, c.id_monitor, m.nombre, m.email, m.iban, m.domicilio from monitor m join certificacion c using(id_monitor) where c.estado='aceptada'",
                    new CertificacionMonitorRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<CertificacionMonitor>();
        }
    }
	
	public CertificacionMonitor getCertificacionMonitor(String email, int idCert) {
        try{
            return jdbcTemplate.queryForObject("SELECT c.id_certificacion, c.estado, c.id_monitor, m.nombre, m.email, m.iban, m.domicilio from monitor m join certificacion c using(id_monitor) where m.email=? and id_certificacion=?", new CertificacionMonitorRowMapper(), email, idCert);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
	public CertificacionMonitor getCertificacionMonitor(int idCert) {
        try{
            return jdbcTemplate.queryForObject("SELECT c.id_certificacion, c.estado, c.id_monitor, m.nombre, m.email, m.iban, m.domicilio from monitor m join certificacion c using(id_monitor) where  id_certificacion=?", new CertificacionMonitorRowMapper(),idCert);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
