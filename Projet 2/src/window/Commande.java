package window;

import javax.swing.*;
import java.util.ArrayList;

public class Commande {
	private Client client;
	private Livreur livreur;
	private ArrayList<Menu> menusChoisis;
	private int note;

	public Commande(Client client) {
		this.client = client;
		this.livreur = null;
		this.note = 0;
		this.menusChoisis = new ArrayList<>();
	}

	public Client getClient() {
		return client;
	}

	public Livreur getLivreur() {
		return livreur;
	}

	public ArrayList<Menu> getMenusChoisis() {
		return menusChoisis;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public void ajouterNewMenu(Menu menu) {
		menusChoisis.add(menu);
	}

	public void supprimerMenu(Menu menu) {
		menusChoisis.remove(menu);
	}

	@Override
	public String toString() {
		return "Commande \nClient: " + (client != null ? client.toString() : "Aucun client") +
				"\nLivreur : " + (livreur != null ? livreur.toString() : "Aucun livreur") +
				"\nMenus Choisis: " + menusChoisis + "\n";
	}

	public void choisirLivreur(ArrayList<Livreur> livreurs) {
		boolean res = false;
		String nm = "";
		for (Livreur lv : livreurs) {
			if (lv.estCompatible(client) && lv.getEstLibre()) {
				lv.setEstLibre(false);
				livreur = lv;
				res = true;
				nm = lv.getNom();
				break;
			}
		}
		if (res) {
			JOptionPane.showMessageDialog(null, "Le livreur de la commande est " + nm, "Livreur", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Aucun livreur disponible", "Livreur", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public int calculTempLivraison() {
		int total = 0;
		for (Menu mn : menusChoisis) {
			total += mn.getTempPrepa();
		}
		total += 10;
		return total;
	}

	public float aPayer() {
		float total = 0;
		for (Menu mn : menusChoisis) {
			total += mn.getPrix();
		}
		return total;
	}

	public void finaliser(int n) {
		setNote(n);
		if (livreur != null) {
			livreur.setEstLibre(true);
		}
	}
}
