package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import es.uji.ei1027.toopots.model.ImgAct;
import org.springframework.stereotype.Repository;

@Repository
public class ImgActDao {
	
	private final String TABLE_NAME = "ImgAct";
	private final int NUM_PARAMS = 3;
	
	private final String ADD_ACT_SQL = SqlGenerator.generateAddSql(TABLE_NAME, NUM_PARAMS);
	
	private final String GET_ALL_ACT_SQL = "SELECT id_imagen, id_actividad, url FROM " 
	+ TABLE_NAME + ";";
	
	private JdbcTemplate jdbcTemplate;
	
	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	// Añade una nueva imagen
	public void addActividad(ImgAct img){
		update(ADD_ACT_SQL, img);
	}
	
	// Realiza operaciones con un objeto imagen
	private void update(String sql, ImgAct img){
		jdbcTemplate.update(sql, img.getId_imagen(), img.getId_actividad(), img.getUrl());
	}

	public ImgAct getImageActividad(int id){
		try{
			return jdbcTemplate.queryForObject("SELECT DISTINCT(id_actividad), url, id_imagen FROM imgact where id_actividad=?", new ImgActRowMapper(), id);
		}catch (EmptyResultDataAccessException e){
			e.printStackTrace();
			return null;
		}
	}
	
    /* Obté totes les Imagens. Torna una llista buida si no n'hi ha cap. */
    public List<ImgAct> getActividad() {
        try {
            return jdbcTemplate.query(GET_ALL_ACT_SQL, new ImgActRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<ImgAct>();
        }
    }

}
