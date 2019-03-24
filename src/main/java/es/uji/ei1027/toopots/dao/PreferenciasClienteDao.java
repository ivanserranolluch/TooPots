package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.toopots.model.PreferenciasCliente;


@Repository
public class PreferenciasClienteDao {
	
	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* Añade las preferencias de los clientes a la base de datos */
	public void addPreferenciasCliente(PreferenciasCliente preferenciasCliente) {
		jdbcTemplate.update("INSERT INTO preferenciascliente VALUES(?, ?, ?)",
				preferenciasCliente.getDni(), preferenciasCliente.getId_tipoActividad(),
					preferenciasCliente.getFecha());
		
	}
	
	/* Actualiza las preferencias de los clientes a la base de datos */
	public void updatePreferenciasCliente(PreferenciasCliente preferenciasCliente) {
        jdbcTemplate.update("UPDATE preferenciascliente SET id_tipoActividad=?, fecha=? WHERE dni=?",
        		preferenciasCliente.getId_tipoActividad(), preferenciasCliente.getFecha(), preferenciasCliente.getDni());
    }
	
	/* Borra las preferencias de los clientes a la base de datos */
	public void deletePreferenciasCliente(String id) {
        jdbcTemplate.update("DELETE FROM preferenciascliente WHERE dni=?", id);
    }
	
	public PreferenciasCliente getPreferenciasCliente(String id) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM preferenciascliente WHERE dni=?", new PreferenciasClienteRowMapper(), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

	/* Obtiene todas las certificaciones de los tipos de actividad. Devuelve una lista vacia si no encuentra ninguno */
	public List<PreferenciasCliente> getPreferenciasClientes() {
		try {
			return jdbcTemplate.query("SELECT * from preferenciascliente",
					new PreferenciasClienteRowMapper());
		}
		catch(EmptyResultDataAccessException e) {
			return new ArrayList<PreferenciasCliente>();
		}
	}

}
