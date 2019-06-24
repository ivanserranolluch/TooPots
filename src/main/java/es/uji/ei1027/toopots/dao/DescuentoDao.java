package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.toopots.model.Descuento;

@Repository
public class DescuentoDao {
	
private JdbcTemplate jdbcTemplate;
	
	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	public void addDescuento(Descuento descuento) {
		jdbcTemplate.update("INSERT INTO Descuento (campo, valor, discount ) VALUES (?, ?, ?);",
				descuento.getField(), descuento.getValue(), descuento.getDiscountValue());
	}
	
	public List<Descuento> getClientes() {
        try {
            return jdbcTemplate.query("SELECT * from Descuento",
                    new DescuentoRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Descuento>();
        }
    }

}
