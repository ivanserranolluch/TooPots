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
        jdbcTemplate.update("INSERT INTO tipoactividad VALUES((select nextval('sec_id_tipoactividad')), ?, ?)",
                 tAct.getNombre(), tAct.getNivel());
    }

    public void updateTipoActividad(TipoActividad tAct) {
        jdbcTemplate.update("UPDATE tipoactividad SET nombre=?, nivel=? WHERE id_tipoActividad=?",
                tAct.getNombre(), tAct.getNivel(), tAct.getId());
    }

    public void deleteTipoActividad(String id) {
        jdbcTemplate.update("DELETE FROM tipoactividad WHERE id_tipoActividad=?", Integer.parseInt(id));
    }

    public TipoActividad getTipoActividad(String id) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM tipoactividad WHERE id_tipoactividad=?", new TipoActividadRowMapper(), Integer.parseInt(id));
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

    public List<TipoActividad> getTiposActividadesCertificadas(String id_monitor){
        try{
            return jdbcTemplate.query("select ta.id_tipoactividad, ta.nombre, ta.nivel from monitor m join certificacion c USING(id_monitor) " +
                    "JOIN certificacionestipoactividad cta using(id_certificacion) join tipoactividad ta using(id_tipoactividad) WHERE m.id_monitor=? AND c.estado = 'aceptada'",
                    new TipoActividadRowMapper(), id_monitor);
        }catch (EmptyResultDataAccessException e){
            return  new ArrayList<TipoActividad>();
        }
    }

    public List<TipoActividad> getTiposActividadesNoVacios() {
        try{
            return jdbcTemplate.query("select DISTINCT(t.id_tipoactividad), t.nombre, t.nivel from actividad a join tipoactividad t using(id_tipoactividad);",
                    new TipoActividadRowMapper());
        }catch (EmptyResultDataAccessException e){
            return  new ArrayList<TipoActividad>();
        }
    }
}