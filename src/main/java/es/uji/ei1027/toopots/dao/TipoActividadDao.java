package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.TipoActividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TipoActividadDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addTipoActividad(TipoActividad tAct) {
        jdbcTemplate.update("INSERT INTO tipoactividad VALUES(?, ?, ?)",
                tAct.getId(), tAct.getNombre(), tAct.getNivel());
    }

    public void updateTipoActividad(TipoActividad tAct) {
        jdbcTemplate.update("UPDATE tipoactividad SET nombre=?, nivel=? WHERE id_tipoActividad=?",
                tAct.getNombre(), tAct.getNivel(), tAct.getId());
    }

    public void deleteTipoActividad(String id) {
        jdbcTemplate.update("DELETE FROM tipoactividad WHERE id_tipoActividad=?", id);
    }

    public TipoActividad getTipoActividad(String id) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM tipoactividad WHERE id_tipoActividad=?", new TipoActividadRowMapper(), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<TipoActividad> getTiposActividades() {
        try{
            return jdbcTemplate.query("SELECT * FROM tipoactividad", new TipoActividadRowMapper());
        }catch (EmptyResultDataAccessException e) {
            return new ArrayList<TipoActividad>();
        }
    }
}