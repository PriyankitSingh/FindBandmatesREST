package nz.ac.auckland.musician.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Band {
	@Id @GeneratedValue 
	private int id;
	
	private String bandName;
	
	@OneToMany(mappedBy="band")
	private List<Musician> members = new ArrayList<Musician>();
	
	
	public Band() {
		super();
	}


	public Band(String bandName) {
		super();
		this.bandName = bandName;
	}


	public Band(int id, String bandName, List<Musician> members) {
		super();
		this.id = id;
		this.bandName = bandName;
		this.members = members;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getBandName() {
		return bandName;
	}
	
	
	public void setBandName(String bandName) {
		this.bandName = bandName;
	}
	
	
	public List<Musician> getMembers() {
		return members;
	}
	
	// May not need this method
	public void setMembers(List<Musician> members) {
		this.members = members;
	}
	
	public void addMember(Musician newMember){
		members.add(newMember);
	}
	
}