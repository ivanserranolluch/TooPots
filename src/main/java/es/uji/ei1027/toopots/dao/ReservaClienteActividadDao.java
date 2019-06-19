package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.toopots.model.Reserva;
import es.uji.ei1027.toopots.model.ReservaClienteActividad;

@Repository
public class ReservaClienteActividadDao {
	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
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
	public ReservaClienteActividad getReservaClienteActividad() {
        try{
            return jdbcTemplate.queryForObject("SELECT id_actividad, a.nombre as nombreActividad, a.descripcion, a.duraciondias, a.fecha as fechaActividad, a.precio, a.minasistentes, a.maxasistentes, a.lugar, a.puntoencuento, a.textocliente, a.estado, a.id_tipoactividad, c.nombre as nombreCliente, c.dni, c.email, c.fechanacimiento, c.sexo, r.id_Reserva, r.estadopago, r.numasistentes, r.preciopersona FROM cliente c join reserva r using(dni) join actividad a using(id_Actividad)", new ReservaClienteActividadRowMapper());
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
	/* Obtiene una reserva de la base de datos */
	public ReservaClienteActividad getReservaClienteActividadPendientes() {
        try{
            return jdbcTemplate.queryForObject("SELECT id_actividad, a.nombre as nombreActividad, a.descripcion, a.duraciondias, a.fecha as fechaActividad, a.precio, a.minasistentes, a.maxasistentes, a.lugar, a.puntoencuento, a.textocliente, a.estado, a.id_tipoactividad, c.nombre as nombreCliente, c.dni, c.email, c.fechanacimiento, c.sexo, r.id_Reserva, r.estadopago, r.numasistentes, r.preciopersona FROM cliente c join reserva r using(dni) join actividad a using(id_Actividad) where r.estadoPago='pendiente'", new ReservaClienteActividadRowMapper());
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
	/* Obtiene una reserva de la base de datos */
	public ReservaClienteActividad getReservaClienteActividadAceptadas() {
        try{
            return jdbcTemplate.queryForObject("SELECT id_actividad, a.nombre as nombreActividad, a.descripcion, a.duraciondias, a.fecha as fechaActividad, a.precio, a.minasistentes, a.maxasistentes, a.lugar, a.puntoencuento, a.textocliente, a.estado, a.id_tipoactividad, c.nombre as nombreCliente, c.dni, c.email, c.fechanacimiento, c.sexo, r.id_Reserva, r.estadopago, r.numasistentes, r.preciopersona FROM cliente c join reserva r using(dni) join actividad a using(id_Actividad) where r.estadoPago='aceptada'", new ReservaClienteActividadRowMapper());
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
