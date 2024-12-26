package window;

public class Proprietaire extends Personne{
	
	public Proprietaire(String nom) {
		super(nom);
	}
	public void bonus(Commande commande){
		if(commande.getNote()<2){
			super.setPoint(0);
		}else{
			super.setPoint(1);
		}
	}

	public String toString() {
		return super.toString();
	}

}
