package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import es.uji.ei1027.toopots.model.PreferenciasCliente;
import es.uji.ei1027.toopots.model.Reserva;

public class PreferenciasClienteDao {
	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* Añade las certificaciones de los tipos de actividades a la base de datos */
	public void addPreferenciasCliente(Reserva reserva) {
		jdbcTemplate.update("INSERT INTO PreferenciasCliente VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
				reserva.getId_reserva(),reserva.getEstadoPago(),reserva.getFecha(),reserva.getNumAsistentes(), 
				reserva.getPrecioPersona(), reserva.getNumAsistentes(),
				reserva.getId_actividad(), reserva.getDni());
		
	}

	/* Obtiene todas las certificaciones de los tipos de actividad. Devuelve una lista vacia si no encuentra ninguno */
	public List<PreferenciasCliente> getPreferenciasClientes() {
		try {
			return jdbcTemplate.query("SELECT * from PreferenciasCliente",
					new PreferenciasClienteRowMapper());
		}
		catch(EmptyResultDataAccessException e) {
			return new ArrayList<PreferenciasCliente>();
		}
	}

}
