package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.toopots.model.Cliente;


@Repository
public class ClienteDao {

	private JdbcTemplate jdbcTemplate;
	
	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	/* Afegeix el nadador a la base de dades */
    public void addCliente(Cliente cliente) {
        jdbcTemplate.update("INSERT INTO Nadador VALUES(?, ?, ?, ?, ?)",
                cliente.getDni(),cliente.getNombre(),cliente.getEmail(),
                cliente.getFechaNacimiento(),cliente.getSexo());
    }
	
    /* Obté tots els nadadors. Torna una llista buida si no n'hi ha cap. */
    public List<Cliente> getClientes() {
        try {
            return jdbcTemplate.query("SELECT * from Cliente",
                    new ClienteRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Cliente>();
        }
    }
}
