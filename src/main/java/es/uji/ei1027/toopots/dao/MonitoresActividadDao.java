package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import es.uji.ei1027.toopots.model.MonitoresActividad;

public class MonitoresActividadDao {
	private final String TABLE_NAME = "MonitoresActividad";
	private final int NUM_PARAMS = 2;
	
	private final String ADD_ACT_SQL = SqlGenerator.generateAddSql(TABLE_NAME, NUM_PARAMS);
	
	private final String GET_ALL_ACT_SQL = "SELECT id_monitor, id_actividad FROM " 
	+ TABLE_NAME + ";";
	
	private JdbcTemplate jdbcTemplate;
	
	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	// Añade una nueva relacion
	public void addActividad(MonitoresActividad monitor){
		update(ADD_ACT_SQL, monitor);
	}
	
	// Realiza operaciones con un objeto
	private void update(String sql, MonitoresActividad monitor){
		jdbcTemplate.update(sql, monitor.getId_monitor(), monitor.getId_actividad());
	}
	
    /* Obté totes les relacions. Torna una llista buida si no n'hi ha cap. */
    public List<MonitoresActividad> getActividad() {
        try {
            return jdbcTemplate.query(GET_ALL_ACT_SQL, new MonitoresActividadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<MonitoresActividad>();
        }
    }

}
