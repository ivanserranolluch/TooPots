package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.toopots.model.User;

@Repository
public class UsuariosRegistradosDao {
	
	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public User loadUserByEmail (String username, String password, List<User> listaUsuarios) { 
		
		HashMap<String, User> knownUsers = new HashMap<String, User>();
		
		for (User usuario : listaUsuarios) {
			knownUsers.put(usuario.getEmail(),usuario);
		}
		
		User user = knownUsers.get(username);

		if (user == null) {
			return null; // Usuario no encontrado
		}    
		// Contraseña
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor(); 
		if (passwordEncryptor.checkPassword(password, user.getPassword())) {
			// Es deuria esborrar de manera segura el camp password abans de tornar-lo
			return user; 
		} 
		else {
			return null; // bad login!
		}
	}
	
	/* Añade un usuario a la base de datos */
    public void addUsuario(User usuariosRegistrados) {
        jdbcTemplate.update("INSERT INTO usuariosregistrados VALUES(?, ?, ?)",
        		usuariosRegistrados.getEmail(), usuariosRegistrados.getPassword(), usuariosRegistrados.getTipoUsuario());
    }
	
	public List<User> getUsuarios() {
        try{
            return jdbcTemplate.query("SELECT * FROM usuariosregistrados", new UserRowMapper());
        }catch (EmptyResultDataAccessException e) {
            return new ArrayList<User>();
        }
    }


}
