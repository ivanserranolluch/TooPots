package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MonitorDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addMonitor(Monitor monitor) {
        jdbcTemplate.update("INSERT INTO monitor VALUES(?, ?, ?, ?, ?, ?)",
                monitor.getId(), monitor.getNombre(), monitor.getEmail(), monitor.getIban(),
                monitor.getEstado(), monitor.getDomicilio());
    }

    public void updateMonitor(Monitor monitor) {
        jdbcTemplate.update("UPDATE monitor SET nombre=?, email=?, iban=?, estado=?, domicilio=? WHERE id_monitor=?",
                monitor.getNombre(), monitor.getEmail(),
                monitor.getIban(), monitor.getEstado(), monitor.getDomicilio(), monitor.getId());
    }

    public void deleteMonitor(String id) {
        jdbcTemplate.update("DELETE FROM monitor WHERE id_monitor=?", id);
    }

    public Monitor getMonitor(String id) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM monitor WHERE id_monitor=?", new MonitorRowMapper(), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Monitor> getMonitores() {
        try{
            return jdbcTemplate.query("SELECT * FROM monitor", new MonitorRowMapper());
        }catch (EmptyResultDataAccessException e) {
            return new ArrayList<Monitor>();
        }
    }

    public List<Monitor> getMonitoresPendientes() {
        try{
            return jdbcTemplate.query("SELECT * FROM monitor WHERE LOWER(estado)='pendiente'", new MonitorRowMapper());
        }catch (EmptyResultDataAccessException e) {
            return new ArrayList<Monitor>();
        }
    }

    public List<Monitor> getMonitoresRegistrados() {
        try{
            return jdbcTemplate.query("SELECT * FROM monitor WHERE LOWER(estado) = 'aceptada'", new MonitorRowMapper());
        }catch (EmptyResultDataAccessException e) {
            return new ArrayList<Monitor>();
        }
    }
    
    public List<Monitor> getMonitoresRechazados() {
        try{
            return jdbcTemplate.query("SELECT * FROM monitor WHERE LOWER(estado) = 'rechazada'", new MonitorRowMapper());
        }catch (EmptyResultDataAccessException e) {
            return new ArrayList<Monitor>();
        }
    }


    public Integer getNumPendientes() {
        try{
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM monitor WHERE LOWER(estado)='pendiente'", Integer.class);
        }catch(EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public Integer getNumMonitores() { // son los aceptados
        try{
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM monitor WHERE LOWER(estado) = 'aceptada'", Integer.class);
        }catch(EmptyResultDataAccessException e) {
            return 0;
        }
    }
    
    public Integer getNumRechazados() {
        try{
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM monitor WHERE LOWER(estado) = 'rechazada'", Integer.class);
        }catch(EmptyResultDataAccessException e) {
            return 0;
        }
    }
    
    
    
    
}
