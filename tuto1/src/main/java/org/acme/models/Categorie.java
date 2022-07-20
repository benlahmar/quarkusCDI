package org.acme.models;

public class Categorie {

	long id;
	String libelle;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public Categorie() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Categorie(long id, String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
	}
	
	
}
