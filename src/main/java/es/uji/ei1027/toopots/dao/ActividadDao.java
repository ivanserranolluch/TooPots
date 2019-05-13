package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import es.uji.ei1027.toopots.model.Actividad;

public class ActividadDao {
	
	private final String ADD_ACT_SQL = "INSERT INTO Actividad VALUES "
			+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	private final String GET_ALL_ACT_SQL = "SELECT id_actividad, nombre, descripcion, duracionDias, fecha,"
			+ " precio, minAsistentes, maxAsistentes, lugar, puntoEncuento, horaEncuentro, textoCliente,"
			+ " estado, id_tipoactividad FROM Actividad;";
	
			
	
	
	private JdbcTemplate jdbcTemplate;
	
	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	// Añade una nueva actividad
	public void addActividad(Actividad act){
		update(ADD_ACT_SQL, act);
	}
	
	// Realiza operaciones con un objeto actividad
	private void update(String sql, Actividad act){
		jdbcTemplate.update(sql, act.getId_actividad(), act.getNombre(), act.getDescripcion(),
				act.getDuracionDias(), act.getFecha(), act.getPrecio(), act.getMinAsistentes(),
				act.getMaxAsistentes(), act.getLugar(), act.getPuntoEncuentro(), act.getHoraEncuentro(),
				act.getTextoCliente(), act.getEstado(), act.getId_actividad());
	}
	
	public Actividad getActividad(int id) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM actividad WHERE id_actividad=?", new ActividadRowMapper(), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
	
    /* Obté totes les activitats. Torna una llista buida si no n'hi ha cap. */
    public List<Actividad> getActividad() {
        try {
            return jdbcTemplate.query(GET_ALL_ACT_SQL, new ActividadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Actividad>();
        }
    }

}
