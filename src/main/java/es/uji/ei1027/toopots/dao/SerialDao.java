package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.toopots.model.Certificacion;
import es.uji.ei1027.toopots.model.Serial;

@Repository
public class SerialDao {

	
	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/* Obté totes les Certificacions. Torna una llista buida si no n'hi ha cap. */
    public Serial obtenerIdCertUltimo() {
        try {
            return  jdbcTemplate.queryForObject("Select last_Value as sec_certificacion from sec_certificacion", new SerialRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new Serial();
        }
    }
   
}
