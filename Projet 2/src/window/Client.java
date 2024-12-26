package window;

public class Client extends Personne{
	private int codePostal;
	
	public Client(String nom, int codePostal) {
		super(nom);
		this.codePostal=codePostal;
	}
	public void bonus(Commande commande){
		if(super.getNom()==commande.getClient().getNom()){
			super.setPoint(1);
		}
	}
	
	int getCodePostal(){
		return codePostal;
	}
	void setCodePostal(int codePostal){
		this.codePostal=codePostal;
	}
	public String toString(){
		return super.toString()+", Code postal="+codePostal+"\n";
	}
}
