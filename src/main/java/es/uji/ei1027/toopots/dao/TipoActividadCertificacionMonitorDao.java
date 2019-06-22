package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.toopots.model.TipoActividad;
import es.uji.ei1027.toopots.model.TipoActividadCertificacionMonitor;

@Repository
public class TipoActividadCertificacionMonitorDao {

	private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public List<TipoActividadCertificacionMonitor> getTiposActividades(String id) {
        try{
            return jdbcTemplate.query("SELECT tp.id_tipoactividad, tp.nombre, tp.nivel, c.id_certificacion, c.estado, m.id_monitor, m.nombre as nombreMonitor, m.email  FROM tipoactividad tp join certificacionestipoactividad using(id_tipoactividad) join certificacion c using(id_certificacion) join monitor m using(id_monitor) where tp.id_tipoActividad = ?", new TipoActividadCertificacionMonitorRowMapper(), Integer.parseInt(id) );
        }catch (EmptyResultDataAccessException e) {
            return new ArrayList<TipoActividadCertificacionMonitor>();
        }
    }
}
