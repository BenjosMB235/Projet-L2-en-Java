package window;
public  abstract  class Personne {
	private String nom;
	private int point;
	private int id;
	private static int idc=0;

	public Personne(String nom) {
		this.nom=nom;
		this.point=0;
		++idc;
		this.id=idc;
	}
	String getNom(){
		return nom;
	}
	int getPoint(){
		return point; }
	int getId(){
		return id; }
	void setNom(String nom){
		this.nom=nom; }
	void setPoint(int point){
		this.point=point;
	}
	void setId(int id){
		this.id=id;
	}
	public abstract void bonus(Commande commande);

	public String toString(){
		return "Id= "+id+", nom= "+nom+", point="+point;
	}

}
