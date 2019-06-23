package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.Time;

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
		//act.setHoraEncuentro(new Time(10));
		update(ADD_ACT_SQL, act);
	}
	
	
	
	// Realiza operaciones con un objeto actividad
	private void update(String sql, Actividad act){
		jdbcTemplate.update(sql, act.getId_actividad(), act.getNombre(), act.getDescripcion(),
				act.getDuracionDias(), act.getFecha(), act.getPrecio(), act.getMinAsistentes(),
				act.getMaxAsistentes(), act.getLugar(), act.getPuntoEncuentro(), act.getHoraEncuentro(),
				act.getTextoCliente(), act.getEstado(), act.getId_tipoActividad());
	}
	
	public void updateActividad(Actividad act) {
		jdbcTemplate.update("Update actividad set nombre=?, descripcion=?, duraciondias=?,fecha=?,precio=?,minasistentes=?,maxasistentes=?,lugar=?,puntoencuento=?,horaencuentro=?,textocliente=?,estado=?, id_tipoactividad=? where id_actividad =?", act.getNombre(), act.getDescripcion(),
				act.getDuracionDias(), act.getFecha(), act.getPrecio(), act.getMinAsistentes(),
				act.getMaxAsistentes(), act.getLugar(), act.getPuntoEncuentro(), new Time(10),
				act.getTextoCliente(), act.getEstado(), act.getId_tipoActividad(),act.getId_actividad());
	}
	
	public void deleteActividad(int id) {
	        jdbcTemplate.update("DELETE FROM actividad WHERE id_actividad=?", id);
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

	public List<Actividad> getActividadPorTipo(String tipo) {
		try {
			return jdbcTemplate.query("SELECT id_actividad, a.nombre, descripcion, duracionDias, fecha," +
					" precio, minAsistentes, maxAsistentes, lugar, puntoEncuento, horaEncuentro, textoCliente," +
					"estado, id_tipoactividad FROM actividad a JOIN tipoactividad t USING(id_tipoactividad) WHERE LOWER(t.nombre)=LOWER(?)", new ActividadRowMapper(),tipo);
		}
		catch(EmptyResultDataAccessException e) {
			return new ArrayList<Actividad>();
		}
	}

	public List<Actividad> getActividadesMonitor(String id) {
		try {
			return jdbcTemplate.query("SELECT id_actividad, nombre, descripcion, duracionDias, fecha," +
					" precio, minAsistentes, maxAsistentes, lugar, puntoEncuento, horaEncuentro, textoCliente," +
					"estado, id_tipoactividad FROM actividad WHERE id_", new ActividadRowMapper(), id);
		}
		catch(EmptyResultDataAccessException e) {
			return new ArrayList<Actividad>();
		}
	}

}
