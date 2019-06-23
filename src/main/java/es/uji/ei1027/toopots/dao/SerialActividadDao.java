package es.uji.ei1027.toopots.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.toopots.model.Serial;
import es.uji.ei1027.toopots.model.SerialActividad;

@Repository
public class SerialActividadDao {

	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	 public SerialActividad obtenerIdActUltima() {
	        try {
	            return  jdbcTemplate.queryForObject("Select last_Value as sec_actividad from sec_actividad", new SerialActividadRowMapper());
	        }
	        catch(EmptyResultDataAccessException e) {
	            return new SerialActividad();
	        }
	    }
	
}
