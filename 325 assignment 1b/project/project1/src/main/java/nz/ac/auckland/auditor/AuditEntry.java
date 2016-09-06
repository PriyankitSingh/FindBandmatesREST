package nz.ac.auckland.auditor;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import nz.ac.auckland.musician.domain.Musician;

@Entity
public class AuditEntry {
	
	public enum CrudOperation {Create, Retrieve, Update, Delete}
	
	@Id @GeneratedValue private Long _id;
	
	private CrudOperation _crudOperator;
	
	private String _uri;
	
	@JoinColumn(name="User_Id", nullable=false)
	@ManyToOne(fetch = FetchType.EAGER, cascade = {javax.persistence.CascadeType.PERSIST})
	private User _user;
	
	@JoinColumn(name="Musician_Id", nullable=false)
	@ManyToOne(fetch = FetchType.EAGER, cascade = {javax.persistence.CascadeType.PERSIST})
	private Musician musician;
	
	private Date _timestamp;
	
	public AuditEntry(CrudOperation crudOp, String uri, User user) {
		_crudOperator = crudOp;
		_uri = uri;
		_user = user;
		_timestamp = new Date();
		this.musician = new Musician();
	}
	
	public AuditEntry(CrudOperation crudOp, String uri, Musician musician) {
		_crudOperator = crudOp;
		_uri = uri;
		musician = musician;
		_timestamp = new Date();
	}
	
	protected AuditEntry() {}
	
	public Long getId() {
		return _id;
	}
	
	public CrudOperation getCrudOperation() {
		return _crudOperator;
	}
	
	public String getUri() {
		return _uri;
	}
	
	public User getUser() {
		return _user;
	}
	
	public Date getTimestamp() {
		return _timestamp;
	}
}