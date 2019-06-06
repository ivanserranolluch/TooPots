package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.toopots.model.Reserva;

@Repository
public class ReservaDao {
	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* Añade las reservas a la base de datos */
	public void addReserva(Reserva reserva) {
		jdbcTemplate.update("INSERT INTO reserva VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
				reserva.getId_reserva(),reserva.getEstadoPago(),reserva.getFecha(),reserva.getNumAsistentes(), 
				reserva.getPrecioPersona(), reserva.getNumTransacciones(),
				reserva.getId_actividad(), reserva.getDni());
		
	}
	
	/* Actualiza las reservas a la base de datos */
	public void updateReserva(Reserva reserva) {
        jdbcTemplate.update("UPDATE reserva SET estadoPago=?, fecha=?, numAsistentes=?, precioPersona=?,"
        		+ "numTransacciones=?, id_actividad=?, dni=?  WHERE id_reserva=?",
        		reserva.getEstadoPago(), reserva.getFecha(), reserva.getNumAsistentes(), reserva.getPrecioPersona(),
        			reserva.getNumTransacciones(), reserva.getId_actividad(), reserva.getDni(), reserva.getId_reserva());
    }
	
	/* Borra las reservas a la base de datos */
	public void deleteReserva(String id) {
        jdbcTemplate.update("DELETE FROM Reserva WHERE id_reserva=?", id);
    }
	
	/* Obtiene una reserva de la base de datos */
	public Reserva getReserva(String id) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM reserva WHERE id_reserva=?", new ReservaRowMapper(), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

	/* Obtiene todas las certificaciones de los tipos de actividad. Devuelve una lista vacia si no encuentra ninguno */
	public List<Reserva> getReservas() {
		try {
			return jdbcTemplate.query("SELECT * from Reserva",
					new ReservaRowMapper());
		}
		catch(EmptyResultDataAccessException e) {
			return new ArrayList<Reserva>();
		}
	}

	public Integer getNumReservas() {
		try{
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reserva", Integer.class);
		}catch(EmptyResultDataAccessException e) {
			return 0;
		}
	}

	public Integer getNumClientes() {
		try{
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cliente", Integer.class);
		}catch(EmptyResultDataAccessException e) {
			return 0;
		}
	}

	public Integer getNumActividades() {
		try{
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM actividad", Integer.class);
		}catch(EmptyResultDataAccessException e) {
			return 0;
		}
	}

}
