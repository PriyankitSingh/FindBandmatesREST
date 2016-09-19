package nz.ac.auckland.musician.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Musician {
	
	@Id  private int id;
	private String lastname;
	private String firstname;
	private Gender gender;
	private Experience skillLevel;
	private Calendar dateOfBirth;
	private Instrument mainInstrument;
	
	@ManyToMany(cascade = CascadeType.PERSIST) 
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "MUSICIAN_BAND", 
		joinColumns = @JoinColumn(name = "MUSICIAN_ID"),
		inverseJoinColumns = @JoinColumn(name = "BAND_ID")     
	)
	private List<Band> bands = new ArrayList<Band>();
	
	


	public Musician() {
		super();
	}
	
	public List<Band> getBands() {
		return bands;
	}


	public void setBands(List<Band> bands) {
		this.bands = bands;
	}
	
	public void addBand(Band band){
		bands.add(band);
	}
	
	public void leaveBand(Band band){
		for (int i = 0 ; i < bands.size() ; i++){
			Band currBand = bands.get(i);
			if (currBand.getBandName() == band.getBandName()){
				bands.remove(i);
				return;
			}
		}
	}


	public Musician(String firstname) {
		super();
		this.firstname = firstname;
	}


	public Musician(int id, String lastname, String firstname, Gender gender, Experience skillLevel,
			Calendar dateOfBirth, Instrument mainInstrument) {
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


	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}
	
	
	public void setDateOfBirth(Calendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
	public Instrument getMainInstrument() {
		return mainInstrument;
	}
	
	
	public void setMainInstrument(Instrument mainInstrument) {
		this.mainInstrument = mainInstrument;
	}


}
