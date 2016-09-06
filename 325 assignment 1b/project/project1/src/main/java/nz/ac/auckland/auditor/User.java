package nz.ac.auckland.auditor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.ws.rs.Path;

@Entity
public class User {

	@Id @GeneratedValue private Long _id;
	
	@Column(unique=true)
	private String _username;
	
	private String _lastname;
	
	private String _firstname;
	
	public User(String username, String lastname, String firstname) {
		_username = username;
		_lastname = lastname;
		_firstname = firstname;
	}
	
	public User(String username) {
		this(username, null, null);
	}
	
	protected User() {}
	
	public Long getId() {
		return _id;
	}
	
	public String getUsername() {
		return _username;
	}
	
	public String getLastname() {
		return _lastname;
	}
	
	public String getFirstname() {
		return _firstname;
	}
}
