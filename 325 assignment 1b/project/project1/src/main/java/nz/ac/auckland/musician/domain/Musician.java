package nz.ac.auckland.musician.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

import nz.ac.auckland.parolee.domain.Gender;

@Entity
public class Musician {
	
	@Id @GeneratedValue private int id;
	private String lastname;
	private String firstname;
	private Gender gender;
	private Experience skillLevel;
	private DateTime dateOfBirth;
	private Instrument mainInstrument;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bandName")
	private Band band;
	
	public Musician() {
		super();
	}


	public Musician(String firstname) {
		super();
		this.firstname = firstname;
	}


	public Musician(int id, String lastname, String firstname, Gender gender, Experience skillLevel,
			DateTime dateOfBirth, Instrument mainInstrument) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.gender = gender;
		this.skillLevel = skillLevel;
		this.dateOfBirth = dateOfBirth;
		this.mainInstrument = mainInstrument;
	}


	public int getId() {
		return id;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getLastname() {
		return lastname;
	}
	
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	
	public String getFirstname() {
		return firstname;
	}
	
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	
	public Gender getGender() {
		return gender;
	}
	
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	
	public Experience getSkillLevel() {
		return skillLevel;
	}


	public void setSkillLevel(Experience skillLevel) {
		this.skillLevel = skillLevel;
	}


	public DateTime getDateOfBirth() {
		return dateOfBirth;
	}
	
	
	public void setDateOfBirth(DateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
	public Instrument getMainInstrument() {
		return mainInstrument;
	}
	
	
	public void setMainInstrument(Instrument mainInstrument) {
		this.mainInstrument = mainInstrument;
	}


	public Band getBand() {
		return band;
	}


	public void setBand(Band band) {
		this.band = band;
	}
}