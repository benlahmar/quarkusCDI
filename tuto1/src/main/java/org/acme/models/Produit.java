package org.acme.models;

public class Produit {

	long id;
	String desg;
	double prix;
	int quantity;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDesg() {
		return desg;
	}
	public void setDesg(String desg) {
		this.desg = desg;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Produit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Produit(long id, String desg, double prix, int quantity) {
		super();
		this.id = id;
		this.desg = desg;
		this.prix = prix;
		this.quantity = quantity;
	}
	
	
}
