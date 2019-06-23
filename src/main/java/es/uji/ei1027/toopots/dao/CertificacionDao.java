package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import es.uji.ei1027.toopots.model.Certificacion;
import es.uji.ei1027.toopots.model.Monitor;
import es.uji.ei1027.toopots.model.Reserva;

public class CertificacionDao {

	private final String TABLE_NAME = "Certificacion";
	private final int NUM_PARAMS = 4;
	
	private final String ADD_ACT_SQL = SqlGenerator.generateAddSql(TABLE_NAME, NUM_PARAMS);
	
	private final String GET_ALL_ACT_SQL = "SELECT id_certificacion, estado, id_monitor, rutacertificado FROM " 
	+ TABLE_NAME + ";";
	
	private JdbcTemplate jdbcTemplate;
	
	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	// Añade una nueva Certificacion
	public void addCertificacion(Certificacion cert){
		update(ADD_ACT_SQL, cert);
	}
	public void updateCertificacion(Certificacion cert) {
        jdbcTemplate.update("UPDATE certificacion SET estado=? WHERE id_certificacion=?", cert.getEstado(), cert.getId_certificacion());
    }
	
	public void insertCertificacion(Certificacion cert) {
		jdbcTemplate.update("Insert into certificacion values((select nextval('sec_certificacion')),?,?,?)",cert.getEstado(),cert.getId_monitor(), cert.getRutaCertificado());
	}
	
	
	
	// Realiza operaciones con un objeto Certificacion
	private void update(String sql, Certificacion cert){
		jdbcTemplate.update(sql, cert.getId_certificacion(), cert.getEstado(), cert.getId_monitor(), cert.getRutaCertificado());
	}
	
    /* Obté totes les Certificacions. Torna una llista buida si no n'hi ha cap. */
    public List<Certificacion> getCertificacion() {
        try {
            return jdbcTemplate.query(GET_ALL_ACT_SQL, new CertificacionRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Certificacion>();
        }
    }
    
    public Certificacion getCertificacion(int id) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM certificacion WHERE id_certificacion=?", new CertificacionRowMapper(), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
