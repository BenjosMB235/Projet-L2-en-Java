package modele;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Formulaire extends JFrame {

    //Les attributs
    private  JLabel titre, t1, t2, t3, t4, t5, t6;
    private JTextField nom, prenom, ue, ec, noteD, noteE;
    private JButton CalculMoy, Annuler;
    private JPanel p_nord(){
        JPanel p_nord=new JPanel(new FlowLayout());
        titre=new JLabel("Saisie Note Etudiant");
        titre.setFont(new Font("Segoe UI Black", 1, 24));
        titre.setForeground(Color.red);
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        p_nord.add(titre);
        return p_nord;
        
    }
    private JPanel p_centre(){
        JPanel p_centre=new JPanel();
        p_centre.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        p_centre.setLayout(new GridLayout(6,2, 10, 10));
        t1= new JLabel("Nom :");
        p_centre.add(t1);
        nom=new JTextField();
        p_centre.add(nom);
        t2=new JLabel("Prénom :");
        p_centre.add(t2);
        prenom=new JTextField();
        p_centre.add(prenom);
        t3=new JLabel("UE :");
        p_centre.add(t3);
        ue=new JTextField();
        p_centre.add(ue);
        t4=new JLabel("EC :");
        p_centre.add(t4);
        ec=new JTextField();
        p_centre.add(ec);
        t5=new JLabel("NOTE DEVOIR :");
        p_centre.add(t5);
        noteD=new JTextField();
        p_centre.add(noteD);
        t6=new JLabel("NOTE EXAMEN :");
        p_centre.add(t6);
        noteE=new JTextField();
        p_centre.add(noteE);
        return p_centre;
    }
    private JPanel p_sud(){
        JPanel p_sud=new JPanel(new FlowLayout(FlowLayout.CENTER));
        CalculMoy=new JButton("Calculer la moyenne");
        p_sud.add(CalculMoy);
        Annuler=new JButton("Annuler");
        p_sud.add(Annuler);
        return p_sud;
    }

    //Le constructeur
    private Formulaire(int heigth, int width, int x, int y){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Gestion des notes");
        this.setSize(width, heigth);
        this.setLocation(x, y);

        JPanel contentPane=(JPanel) this.getContentPane();
        contentPane.add(p_nord(), BorderLayout.NORTH);
        contentPane.add(p_centre(), BorderLayout.CENTER);
        contentPane.add(p_sud(), BorderLayout.SOUTH);

        //Méthode calcul de moyenne
        CalculMoy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    double cc = Integer.parseInt(noteD.getText());
                    double exam= Integer.parseInt(noteE.getText());

                    double moy=(0.6*exam)+(0.4*cc);
                    String message="Votre moyenne est :"+moy;
                    JOptionPane.showMessageDialog(null, message);
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Erreur Format");
                }

            }
        });

        //Méthode pour annuler
        Annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    //Méthode principale
    public static  void main(String[] args){
        Dimension screenSize= Toolkit.getDefaultToolkit().getScreenSize();

        int width= screenSize.width;
        int height= screenSize.height;
        Formulaire f=new Formulaire(400,600,100,50);
        f.setVisible(true);
    }
}
