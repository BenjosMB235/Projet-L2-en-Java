package window;

import java.util.ArrayList;

public class Restaurant {
	private String nom;
	private String specialite;
	private Proprietaire proprietaire;
	private int codePostal;
	ArrayList<Menu> MenusResto=new ArrayList<Menu>();
	protected static ArrayList<Restaurant> LesRestaurants=new ArrayList<Restaurant>();

	public Restaurant(String nom, String specialite, Proprietaire proprietaire, int codePostal){
		this.nom=nom;
		this.specialite=specialite;
		this.proprietaire=proprietaire;
		this.codePostal=codePostal;
		LesRestaurants.add(this);
	}
	public String toString(){
		return "Restaurant : nom="+nom+", Specialité="+specialite+", " +
				"Code Postal="+codePostal+", Propriétaire:"+proprietaire.toString();
	}
	public String getNom() {

		return nom;
	}
	public void setNom(String nom) {

		this.nom = nom;
	}
	public String getSpecialite() {

		return specialite;
	}
	public void setSpecialite(String specialite) {

		this.specialite = specialite;
	}
	public Proprietaire getProprietaire() {

		return proprietaire;
	}
	public void setProprietaire(Proprietaire proprietaire) {

		this.proprietaire = proprietaire;
	}
	public int getCodePostal() {

		return codePostal;
	}
	public void setCodePostal(int codePostal) {

		this.codePostal = codePostal;
	}
	public ArrayList<Menu> getMenusResto(){
		for(Menu mn: Menu.menus){
			if (mn.getResto()==Restaurant.this){
				MenusResto.add(mn);
			}

		}
		return MenusResto;
	}

	
}
