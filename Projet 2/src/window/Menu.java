package window;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Menu {
	private final int id;
	private static int idcompt = 0;
	private String description;
	private float prix;
	private float poids;
	private int tempPrepa;
	private Restaurant resto;
	protected static final List<Menu> menus = new ArrayList<>();

	public Menu(String description, float prix, float poids, int tempPrepa) {
		synchronized (Menu.class) {
			++idcompt;
			this.id = idcompt;
		}
		this.description = description;
		this.prix = prix;
		this.poids = poids;
		this.tempPrepa = tempPrepa;
		menus.add(this);
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Menu: Description=" + description + ", Prix=" + prix + ", Poids=" + poids +
				", Temps de préparation=" + tempPrepa + ", " +
				(resto != null ? resto.toString() : "Aucun restaurant assigné") + "\n";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public float getPoids() {
		return poids;
	}

	public void setPoids(float poids) {
		this.poids = poids;
	}

	public int getTempPrepa() {
		return tempPrepa;
	}

	public void setTempPrepa(int tempPrepa) {
		this.tempPrepa = tempPrepa;
	}

	public Restaurant getResto() {
		return resto;
	}

	public void setResto(Restaurant resto) {
		this.resto = resto;
	}

	public static List<Menu> getMenus() {
		return Collections.unmodifiableList(menus);
	}
}

