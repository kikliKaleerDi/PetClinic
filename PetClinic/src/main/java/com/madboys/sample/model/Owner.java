package com.madboys.sample.model;

/**
* This is a sample Application written for practice with Spring framework and also other
* frameworks used in web development. This application references the pet clinic application
* on springframework tutorials.This application can be reused by anyone who wants to use
* it for learning purpose.
*
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;


import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;



/**
 * @author t_chalc
 * 
 * A simple bean representing owner of a pet
 *
 */


@Entity
@Table(name="owners")
public class Owner extends Person{
	@Column(name="address")
	@NotEmpty
	private String address;
	
	@Column(name="city")
	@NotEmpty
	private String city;
	
	@Column(name="telephone")
	@NotEmpty
	@Digits(fraction=0,integer=10)
	private String telephone;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="owner")
	private Set<Pet>pets;
	
	public String getAddress(){
		return address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public String getCity(){
		return city;
	}
	
	public String getTelephone(){
		return telephone;
	}
	public void setTelephone(String telephone){
		this.telephone = telephone;
	}
	
	protected void setPetsInternal(Set<Pet> pets){
		this.pets = pets;
	}
	
	protected Set<Pet> getPetsInternal(){
		if(this.pets == null)
			pets = new HashSet<Pet>();
		return pets;
	}
	
	public List<Pet> getPets(){
		List<Pet> sortedPets = new ArrayList<Pet>(getPetsInternal());
		PropertyComparator.sort(sortedPets,new MutableSortDefinition("name",true,true));
		return Collections.unmodifiableList(sortedPets);
	}
	
	public void addPet(Pet pet){
		getPetsInternal().add(pet);
		pet.setOwner(this);
	}
	
	
	/**
	 * Return the Pet for given name, null if none found for this owner
	 * @param name
	 * @return
	 */
	public Pet getPet(String name){
		return getPet(name,false);
	}
	
	public Pet getPet(String name,boolean ignoreNew){
		name = name.toLowerCase();
		for(Pet pet:getPetsInternal()){
			if(!ignoreNew || !pet.isNew()){
				String compName = pet.getName();
				compName = compName.toLowerCase();
				if(compName.equals(name)){
					return pet;
				}
			}
		}
		return null;
	}
	
	
	
	@Override
	public String toString(){
		return new ToStringCreator(this).append("id",this.getId())
				.append("new",this.isNew())
				.append("lastName",this.getLastName())
				.append("firstName",this.getFirstName())
				.append("address",this.address)
				.append("city",this.city)
				.append("telephone",this.telephone).toString();
		
	}
	
}

