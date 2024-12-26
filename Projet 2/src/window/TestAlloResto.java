package window;
import window.Menu;
import window.ModelTableMenu;
import window.EditeButton;
import window.RendreButton;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class TestAlloResto extends JFrame {
    private CardLayout cardLayout;
    private  JPanel cardPanel, panel1, panel2, panel3, panel4, panel5,contenuPanel, panel6;
    private JLabel titre;
    private JTextArea codePostaux, contentPanier;
    private JTextField nom, codePostal, nomr, nomp, spec, codePostalRes, noml;
    private JTextPane msg;
    private JButton buttonCmd, buttonQt1, buttonQt2,buttonQt3,buttonQt4, SelectMenu, RecherchLiv,ValideCom,retourPro, DevLiv, DevPro, buttonCliV, buttonPro, retourLiv, buttonLiv, valideNote;
    private ModelTableMenu tableModel;
    private Map<String, JPanel> restaurantPanels;
    private Map<String, JPanel> livreurPanels;
    private static Client client1;
    private static Commande cmd1;
    private static Proprietaire propri;
    private static Livreur liv;
    private static Restaurant rest;


    private JPanel p_nord1(){
        JPanel p_nord1=new JPanel(new FlowLayout());
        titre=new JLabel("BIENVENU SUR ALLORESTO");
        titre.setFont(new Font("Segoe UI Black", 1, 24));
        titre.setForeground(Color.red);
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        p_nord1.add(titre);
        return p_nord1;
    }
    private  JPanel message(){
        JPanel message=new JPanel(new FlowLayout());
        msg=new JTextPane();
        msg.setContentType("text/html");
        msg.setText("<html><body style='text-align:justify;width: 400px;'>"+"Bienvenue sur AlloResto, le meilleur des application de livraison de nourritures en ligne."
                +" Vous pouvez passer vos commandes maintenant et profiter d'un service haut de gamme avec les meilleurs livreurs possibles."
                +"Faites votre choix !"+"</body></html>");
        msg.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(msg);
        add(scrollPane);
        message.add(msg);
        return message;
    }
    private JPanel buttons1(){
        JPanel buttons1=new JPanel(new FlowLayout());
        buttonCmd=new JButton("Passez une commande");
        buttonQt1=new JButton("Quitter");
        buttons1.add(buttonCmd);
        buttons1.add(buttonQt1);
        return buttons1;
    }
    private  JPanel contenu(){
        JPanel contenu=new JPanel(new BorderLayout());
        contenu.add(message(), BorderLayout.CENTER);
        contenu.add(buttons1(), BorderLayout.SOUTH);
        return contenu;
    }
    private JPanel bas_page(){
        JPanel bas_page=new JPanel(new FlowLayout());
        titre=new JLabel("Copyright - Tout droit réservé !");
        titre.setFont(new Font("Segoe UI Black", 3, 10));
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setPreferredSize(new Dimension(400, 30));
        bas_page.add(titre);
        return bas_page;
    }
    private JPanel bas_pageP(){
        JPanel bas_pageP=new JPanel(new BorderLayout());
        buttonQt3=new JButton("Quitter");
        buttonQt3.setHorizontalAlignment(SwingConstants.CENTER);
        buttonQt3.setPreferredSize(new Dimension(120, 20));
        bas_pageP.add(buttonQt3, BorderLayout.EAST);
        bas_pageP.add(Propri_liv(), BorderLayout.WEST);
        return bas_pageP;
    }
    private JPanel listes(){
        JPanel listes=new JPanel();
        listes.setLayout(new BorderLayout());
        DefaultMutableTreeNode principale = new DefaultMutableTreeNode("AlloResto");

        DefaultMutableTreeNode restaurants = new DefaultMutableTreeNode("Nos Restaurants");
        DefaultMutableTreeNode livreurs = new DefaultMutableTreeNode("Nos Livreurs");

        for(Restaurant res:Restaurant.LesRestaurants){
            restaurants.add(new DefaultMutableTreeNode(res.getNom()));
        }
        for(Livreur lv:Livreur.LesLivreur){
            livreurs.add(new DefaultMutableTreeNode(lv.getNom()));
        }

        principale.add(restaurants);
        principale.add(livreurs);

        JTree tree = new JTree(principale);
        tree.setCellRenderer(new RendreButton());
        tree.setCellEditor(new EditeButton(tree));
        tree.setEditable(true);

        JScrollPane scrollPane = new JScrollPane(tree);
        listes.add(scrollPane, BorderLayout.CENTER);

        tree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                TreePath path = tree.getPathForLocation(evt.getX(), evt.getY());
                if (path != null) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    if (node != null && node.isLeaf()) {
                        String nodeName=node.getUserObject().toString();
                        JPanel panel;
                        if(restaurantPanels.containsKey(nodeName)){
                            panel=restaurantPanels.get(nodeName);
                        }else if(livreurPanels.containsKey(nodeName)){
                            panel=livreurPanels.get(nodeName);
                        }else {
                            return;
                        }
                        contenuPanel.removeAll();
                        contenuPanel.add(panel, BorderLayout.CENTER);
                        contenuPanel.revalidate();
                        contenuPanel.repaint();
                    }
                }
            }
        });
        return listes;
    }
    private JToolBar Propri_liv(){
        JToolBar jt=new JToolBar();
        DevLiv=new JButton("Devenir livreur");
        DevLiv.setPreferredSize(new Dimension(120, 20));
        DevPro=new JButton("Devenir Propriétaire");
        DevPro.setPreferredSize(new Dimension(120, 20));
        jt.add(DevLiv);
        jt.add(DevPro);
        return jt;
    }
    private JPanel panier(){
        JPanel panier = new JPanel(new FlowLayout());
        JLabel msg = new JLabel("Panier :");

        contentPanier = new JTextArea(4, 10);
        contentPanier.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        contentPanier.setEditable(true);

        SelectMenu = new JButton("Ajouter au Panier");
        SelectMenu.setFont(new Font("Arial", Font.BOLD, 10));
        SelectMenu.setPreferredSize(new Dimension(120, 30));
        SelectMenu.setMargin(new Insets(2, 2, 2, 2));

        SelectMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (client1 != null) {
                    cmd1 = new Commande(client1);
                    try {
                        System.out.println("Contenu de contentPanier avant récuparation : " + contentPanier.getText().trim());
                        String mnPanier = contentPanier.getText().trim();
                        System.out.println("Vos menus :"+mnPanier);
                        if (mnPanier.isEmpty()) {
                            JOptionPane.showMessageDialog(TestAlloResto.this, "Aucun menu choisi", "Panier", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        String[] menus = mnPanier.split("\\n");

                        int[] tab = new int[menus.length];
                        for (int i = 0; i < menus.length; i++) {
                            try {
                                tab[i] = Integer.parseInt(menus[i].trim());
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(TestAlloResto.this, "Erreur Format dans la ligne: " + menus[i], "Erreur", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        for (int i : tab) {
                            boolean menuFound = false;
                            for (Menu mn : Menu.menus) {
                                if (i == mn.getId()) {
                                    cmd1.ajouterNewMenu(mn);
                                    menuFound = true;
                                    break;
                                }
                            }
                            if (!menuFound) {
                                JOptionPane.showMessageDialog(TestAlloResto.this, "Menu avec ID: " + i + " n'existe pas", "Erreur", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }

                        JOptionPane.showMessageDialog(TestAlloResto.this, "Ces menus ont bien été ajoutés à  votre commande.", "Menus Choisis", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(TestAlloResto.this, "Une erreur est survenue: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                    contenuPanel.revalidate();
                    contenuPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(TestAlloResto.this, "Le client n'est pas défini. Veuillez entrer les informations du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panier.add(msg);
        panier.add(contentPanier);
        panier.add(SelectMenu);

        panier.revalidate();
        panier.repaint();
        return panier;
    }
    private JPanel TableMenu(ArrayList<Menu> menus) {
        JPanel contenu = new JPanel(new BorderLayout());

        // CrÃ©er et configurer le modÃ¨le de table
        tableModel = new ModelTableMenu(menus);
        JTable table = new JTable(tableModel);

        // Ajouter la table dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(480, 200));
        contenu.add(scrollPane, BorderLayout.CENTER);
        contenu.add(panier(), BorderLayout.SOUTH);
        return contenu;
    }
    private JPanel buttonsResto(){
        // CrÃ©er les boutons et configurer leurs propriÃ©tÃ©s
        RecherchLiv = new JButton("Rechercher livreur");
        RecherchLiv.setFont(new Font("Arial", Font.BOLD, 10));
        RecherchLiv.setPreferredSize(new Dimension(120, 30));
        RecherchLiv.setMargin(new Insets(2, 2, 2, 2));

        ValideCom = new JButton("Valide La Commande");
        ValideCom.setFont(new Font("Arial", Font.BOLD, 10));
        ValideCom.setPreferredSize(new Dimension(120, 30));
        ValideCom.setMargin(new Insets(2, 2, 2, 2));

        // Ajouter les boutons dans un JPanel avec un FlowLayout alignÃ© Ã  droite
        JPanel Buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        Buttons.add(RecherchLiv);
        Buttons.add(ValideCom);

        return Buttons;
    }

    private JPanel titreCli(){
        JPanel contenu=new JPanel(new FlowLayout());
        JLabel rest=new JLabel("Remplissez ce formulaire pour\n passer votre commande");
        rest.setFont(new Font("Segoe UI Black", 1, 18));
        rest.setHorizontalAlignment(SwingConstants.CENTER);
        contenu.add(rest);
        return contenu;
    }
    private JPanel titrePro(){
        JPanel contenu=new JPanel(new FlowLayout());
        JLabel rest=new JLabel("Remplissez ce formulaire pour ajouter votre restaurant à  Alloresto");
        rest.setFont(new Font("Segoe UI Black", 1, 14));
        rest.setHorizontalAlignment(SwingConstants.CENTER);
        contenu.add(rest);
        return contenu;
    }
    private JPanel formulaire(){
        JPanel formulaire=new JPanel(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(10,10,10,10);
        JLabel t1=new JLabel("Nom :");
        nom=new JTextField(40);
        nom.setPreferredSize(new Dimension(200, 30));
        JLabel t2=new JLabel("Code Postal :");
        codePostal=new JTextField(40);
        codePostal.setPreferredSize(new Dimension(200, 30));

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.anchor=GridBagConstraints.EAST;
        formulaire.add(t1, gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        formulaire.add(nom, gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.anchor=GridBagConstraints.EAST;
        gbc.fill=GridBagConstraints.NONE;
        formulaire.add(t2, gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        formulaire.add(codePostal, gbc);

        buttonCliV=new JButton("Valider");
        buttonQt2=new JButton("Quitter");
        JPanel buttonsCli=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsCli.add(buttonCliV);
        buttonsCli.add(buttonQt2);

        gbc.gridx=1;
        gbc.gridy=2;
        gbc.anchor=GridBagConstraints.EAST;
        gbc.fill=GridBagConstraints.NONE;
        formulaire.add(buttonsCli, gbc);

        return formulaire;
    }

    private JPanel contenuCli(){
        JPanel contenu=new JPanel(new BorderLayout());
        contenu.add(titreCli(), BorderLayout.NORTH);
        contenu.add(formulaire(), BorderLayout.CENTER);
        return contenu;
    }
    private JPanel formulairePro(){
        JPanel formulaire=new JPanel(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(10,10,10,10);
        JLabel t1=new JLabel("Nom du Restaurant:");
        nomr=new JTextField();
        nomr.setPreferredSize(new Dimension(300, 30));
        JLabel t2=new JLabel("Spécialité du restaurant :");
        spec=new JTextField();
        spec.setPreferredSize(new Dimension(300, 30));
        JLabel t3=new JLabel("Nom du propriétaire :");
        nomp=new JTextField();
        nomp.setPreferredSize(new Dimension(300, 30));
        JLabel t4=new JLabel("Code Postal :");
        codePostalRes=new JTextField();
        codePostalRes.setPreferredSize(new Dimension(300, 30));

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.anchor=GridBagConstraints.EAST;
        formulaire.add(t1, gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        formulaire.add(nomr, gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.anchor=GridBagConstraints.EAST;
        gbc.fill=GridBagConstraints.NONE;
        formulaire.add(t2, gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        formulaire.add(spec, gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        gbc.anchor=GridBagConstraints.EAST;
        gbc.fill=GridBagConstraints.NONE;
        formulaire.add(t3, gbc);

        gbc.gridx=1;
        gbc.gridy=2;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        formulaire.add(nomp, gbc);

        gbc.gridx=0;
        gbc.gridy=3;
        gbc.anchor=GridBagConstraints.EAST;
        gbc.fill=GridBagConstraints.NONE;
        formulaire.add(t4, gbc);

        gbc.gridx=1;
        gbc.gridy=3;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        formulaire.add(codePostalRes, gbc);


        buttonPro=new JButton("Valider");
        retourPro=new JButton("Retour");
        JPanel buttonsPro=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPro.add(buttonPro);
        buttonsPro.add(retourPro);

        gbc.gridx=1;
        gbc.gridy=4;
        gbc.anchor=GridBagConstraints.EAST;
        gbc.fill=GridBagConstraints.NONE;
        formulaire.add(buttonsPro, gbc);

        return formulaire;
    }
    private JPanel contenuPro(){
        JPanel contenu=new JPanel(new BorderLayout());
        contenu.add(titrePro(), BorderLayout.NORTH);
        contenu.add(formulairePro(), BorderLayout.CENTER);
        return contenu;
    }
    private JPanel titreLiv(){
        JPanel contenu=new JPanel(new FlowLayout());
        JLabel rest=new JLabel("Remplissez ce formulaire pour devenir un livreur à  Alloresto");
        rest.setFont(new Font("Segoe UI Black", 1, 14));
        rest.setHorizontalAlignment(SwingConstants.CENTER);
        contenu.add(rest);
        return contenu;
    }
    private JPanel formulaireLiv(){
        JPanel formulaire=new JPanel(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(10,10,10,10);
        JLabel t1=new JLabel("Nom du livreur :");
        noml=new JTextField();
        noml.setPreferredSize(new Dimension(300, 30));
        JLabel t2=new JLabel("Les Codes Postaux du livreur:");
        codePostaux=new JTextArea(5, 20);

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.anchor=GridBagConstraints.EAST;
        formulaire.add(t1, gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        formulaire.add(noml, gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.anchor=GridBagConstraints.EAST;
        gbc.fill=GridBagConstraints.NONE;
        formulaire.add(t2, gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        formulaire.add(codePostaux, gbc);

        buttonLiv=new JButton("Valider");
        retourLiv=new JButton("Retour");

        JPanel buttonsLiv=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsLiv.add(buttonLiv);
        buttonsLiv.add(retourLiv);

        gbc.gridx=1;
        gbc.gridy=2;
        gbc.anchor=GridBagConstraints.EAST;
        gbc.fill=GridBagConstraints.NONE;
        formulaire.add(buttonsLiv, gbc);

        return formulaire;
    }
    private JPanel contenuLiv(){
        JPanel contenu=new JPanel(new BorderLayout());
        contenu.add(titreLiv(), BorderLayout.NORTH);
        contenu.add(formulaireLiv(), BorderLayout.CENTER);
        return contenu;
    }
    private JPanel p_nord(){
        JPanel p_nord1=new JPanel(new FlowLayout());
        titre=new JLabel("ALLORESTO");
        titre.setFont(new Font("Segoe UI Black", 1, 24));
        titre.setForeground(Color.red);
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        p_nord1.add(titre);
        return p_nord1;
    }


    public TestAlloResto(){
        Proprietaire propri1=new Proprietaire("Donei");
        Proprietaire propri2=new Proprietaire("Benjos");
        Proprietaire propri3=new Proprietaire("Messi");

        int[] tab1={75001, 75002, 75005};
        Livreur liv1=new Livreur("Barbe",tab1);
        int[] tab2={75001, 75002, 75003};
        Livreur liv2=new Livreur("Serge", tab2 );

        Restaurant rest1=new Restaurant("Le Bon Resto", "Chinoise", propri1, 75002);
        Restaurant rest2=new Restaurant("La main sûr", "Française", propri2, 75003);
        Restaurant rest3=new Restaurant("Le Goût de la réussite", "Argentin", propri3, 75004);

        Menu menu1=new Menu("carottes aux gingembres", 9.5F, 250.3F, 15);
        Menu menu2=new Menu("Dos de Saint Marin", 14.5F, 350.2F, 20);
        Menu menu3=new Menu("Pigeonneau de racan roti", 16.5F, 265, 25);
        Menu menu4=new Menu("Haricot", 10.5F, 250, 22);
        Menu menu5=new Menu("Soupe de Porc", 65.5F, 450, 50);
        Menu menu6=new Menu("Croissant aux huiles de porc", 40.5F, 155, 15);
        Menu menu7=new Menu("Haricot blanc", 32.5F, 125, 40);

        menu1.setResto(rest1);
        menu2.setResto(rest1);

        menu3.setResto(rest1);
        menu4.setResto(rest2);
        menu5.setResto(rest2);
        menu6.setResto(rest3);
        menu7.setResto(rest3);

        setTitle("AlloResto");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        restaurantPanels=new HashMap<>();
        livreurPanels=new HashMap<>();

        panel1 = new JPanel(new BorderLayout());
        panel1.add(p_nord1(), BorderLayout.NORTH);
        panel1.add(contenu(), BorderLayout.CENTER);
        panel1.add(bas_page(), BorderLayout.SOUTH);

        buttonQt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonCmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Panel2");
            }
        });

        panel2=new JPanel(new BorderLayout());
        panel2.add(p_nord1(), BorderLayout.NORTH);
        panel2.add(contenuCli(), BorderLayout.CENTER);
        panel2.add(bas_page(), BorderLayout.SOUTH);

        buttonQt2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonCliV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if (nom.getText().trim().isEmpty() || codePostal.getText().trim().isEmpty()){
                        JOptionPane.showMessageDialog(TestAlloResto.this, "Veuillez remplir tous les champs", "Champs manquantes", JOptionPane.WARNING_MESSAGE);
                    } else{
                        String nm=nom.getText();
                        int cp= Integer.parseInt(codePostal.getText());
                        client1=new Client(nm, cp);
                        cardLayout.show(cardPanel, "Panel3");
                    }

                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Erreur Format");
                }


            }
        });


        panel3 = new JPanel(new BorderLayout());
        panel3.add(p_nord(), BorderLayout.NORTH);
        JPanel listesPanel = listes();
        listesPanel.setPreferredSize(new Dimension(200, 0));
        panel3.add(listesPanel, BorderLayout.WEST);
        panel3.add(bas_pageP(), BorderLayout.SOUTH);

        contenuPanel=new JPanel(new BorderLayout());
        panel3.add(contenuPanel, BorderLayout.CENTER);

        panel6=new JPanel(new BorderLayout());
        panel6.add(p_nord(), BorderLayout.NORTH);

        for(Restaurant res:Restaurant.LesRestaurants){
           JPanel panelResto=new JPanel(new BorderLayout());
           JLabel tit=new JLabel("Restaurant "+res.getNom(), SwingConstants.CENTER);
           tit.setFont(new Font("Segoe UI Black", 1, 18));
           panelResto.add(tit, BorderLayout.NORTH);
           panelResto.add(Box.createVerticalStrut(30));
           panelResto.add(TableMenu(res.getMenusResto()), BorderLayout.CENTER);
           panelResto.add(buttonsResto(), BorderLayout.SOUTH);

           cmd1 = new Commande(client1);

           RecherchLiv.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   if (cmd1 != null && client1 != null) {
                       cmd1.choisirLivreur(Livreur.LesLivreur);
                       contenuPanel.revalidate();
                       contenuPanel.repaint();
                   } else {
                       JOptionPane.showMessageDialog(TestAlloResto.this, "La commande ou le client n'est pas dÃ©fini. Veuillez entrer les informations du client et sÃ©lectionner des menus.", "Erreur", JOptionPane.ERROR_MESSAGE);
                   }
               }
           });

           ValideCom.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   if (cmd1 != null && cmd1.getClient() != null){
                       if(cmd1.getLivreur() != null){
                           if(cmd1.getMenusChoisis() != null){
                               float prix=cmd1.aPayer();
                               JOptionPane.showMessageDialog(TestAlloResto.this, "Votre commande est valide.\nle prix de la Commande est "+prix,"Commande valide", JOptionPane.INFORMATION_MESSAGE);
                               cardLayout.show(cardPanel, "Panel6");
                               String tt="<html>FELICITATIONS !!!<br>Le livreur sera chez vous dans "+cmd1.calculTempLivraison()+" minutes <br>En attendant, profitez-en pour noter ce service. Merci!!</html>";
                               JLabel msg=new JLabel(tt, SwingConstants.CENTER);
                               msg.setFont(new Font("Segoe UI Black", 3, 18));
                               JPanel note=new JPanel(new FlowLayout(FlowLayout.RIGHT));
                               JLabel t=new JLabel("Votre note :");
                               JTextField n=new JTextField();
                               n.setPreferredSize(new Dimension(250, 30));
                               note.add(t);
                               note.add(n);
                               valideNote=new JButton("Noter");
                               valideNote.setPreferredSize(new Dimension(120, 20));
                               buttonQt4=new JButton("Quitter");
                               buttonQt4.setPreferredSize(new Dimension(120, 20));
                               note.add(valideNote);
                               note.add(buttonQt4);
                               panel6.add(msg, BorderLayout.CENTER);
                               panel6.add(note, BorderLayout.SOUTH);
                               valideNote.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       int nCmd=Integer.parseInt(n.getText());
                                       cmd1.finaliser(nCmd);
                                       JOptionPane.showMessageDialog(TestAlloResto.this, "Merci d'avoir noté la commande\n Vivement Ã  la prochaine commande", "AlloResto", JOptionPane.INFORMATION_MESSAGE);
                                   }
                               });
                               buttonQt4.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       System.exit(0);
                                   }
                               });
                               //JOptionPane.showMessageDialog(Fenetre.this,meuns,"Menus chois", JOptionPane.INFORMATION_MESSAGE);
                           }else{
                               JOptionPane.showMessageDialog(TestAlloResto.this, "Cochez vos menus souhaités", "Erreur", JOptionPane.ERROR_MESSAGE);
                           }
                       }else{
                           JOptionPane.showMessageDialog(TestAlloResto.this, "Choisissez d'abord un livreur", "Erreur", JOptionPane.ERROR_MESSAGE);
                       }
                   }else{
                       JOptionPane.showMessageDialog(TestAlloResto.this, "Erreur lors de la commande. Veuillez ressayer.", "Erreur", JOptionPane.ERROR_MESSAGE);
                   }
                   contenuPanel.revalidate();
                   contenuPanel.repaint();
               }
           });

           restaurantPanels.put(res.getNom(), panelResto);
        }

        for(Livreur lv:Livreur.LesLivreur){
           JPanel panelLv=new JPanel();
           panelLv.setLayout(new BorderLayout());

           JPanel infoLv=new JPanel();
           infoLv.setLayout(new BoxLayout(infoLv, BoxLayout.Y_AXIS));
           JLabel titre=new JLabel("Informations sur le livreur "+lv.getId(), SwingConstants.CENTER);
           titre.setFont(new Font("Segoe UI Black", 1, 18));
           infoLv.add(titre);
           infoLv.add(Box.createVerticalStrut(10));
           JLabel id=new JLabel("ID : "+lv.getId());
           infoLv.add(id);
           JLabel nom=new JLabel("Nom : "+lv.getNom());
           infoLv.add(nom);
           JLabel codep=new JLabel("Codes Postaux : ");
           infoLv.add(codep);
           DefaultListModel<Integer> codePostauxListModel=new DefaultListModel<>();
           for(int cp:lv.getCodePostaux()){
               codePostauxListModel.addElement(cp);
           }
           JList<Integer> codePostauxList=new JList<>(codePostauxListModel);

           JScrollPane scr=new JScrollPane(codePostauxList);
           scr.setPreferredSize(new Dimension(200, 100));
           infoLv.add(scr);

           panelLv.add(infoLv, BorderLayout.CENTER);
           livreurPanels.put(lv.getNom(), panelLv);

       }

        panel3.revalidate();
        panel3.repaint();

        buttonQt3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //Panneau formulaire PropriÃ©taire
        panel4=new JPanel(new BorderLayout());
        panel4.add(p_nord(), BorderLayout.NORTH);
        panel4.add(contenuPro(), BorderLayout.CENTER);
        panel4.add(bas_page(), BorderLayout.SOUTH);

        DevPro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Panel4");
            }
        });
        buttonPro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if (nomr.getText().trim().isEmpty() || spec.getText().trim().isEmpty() || nomp.getText().trim().isEmpty() || codePostalRes.getText().trim().isEmpty()){
                        JOptionPane.showMessageDialog(TestAlloResto.this, "Veuillez remplir tous les champs", "Champs manquantes", JOptionPane.WARNING_MESSAGE);
                    } else{
                        String nmp=nomp.getText();
                        propri=new Proprietaire(nmp);
                        String nmr=nomr.getText();
                        String sp=spec.getText();
                        int cpr= Integer.parseInt(codePostalRes.getText());
                        rest=new Restaurant(nmr, sp, propri, cpr);
                        JOptionPane.showMessageDialog(TestAlloResto.this, "Formulaire validé","validation", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(cardPanel, "Panel3");
                    }

                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Erreur Format");
                }


            }
        });
        retourPro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(TestAlloResto.this, "Etes-vous sûr? Vos informations ne seront pas enregistrer.", "Attention", JOptionPane.OK_OPTION);
                cardLayout.show(cardPanel, "Panel3");
            }
        });

        //Panneau formulaire livreur
        panel5=new JPanel(new BorderLayout());
        panel5.add(p_nord(), BorderLayout.NORTH);
        panel5.add(contenuLiv(), BorderLayout.CENTER);
        panel5.add(bas_page(), BorderLayout.SOUTH);

        DevLiv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Panel5");
            }
        });
        buttonLiv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if (noml.getText().trim().isEmpty() || codePostaux.getText().trim().isEmpty()){
                        JOptionPane.showMessageDialog(TestAlloResto.this, "Veuillez remplir tous les champs", "Champs manquantes", JOptionPane.WARNING_MESSAGE);
                    } else{
                        String nml=nom.getText();
                        String cpp=codePostaux.getText();
                        String[] cds=cpp.split("\\n");

                        int[] tab=new int[cds.length];
                        for(int i=0; i< cds.length; i++){
                            tab[i]=Integer.parseInt(cds[i].trim());
                        }
                        liv=new Livreur(nml ,tab);
                        System.out.println(liv.getNom());
                        JOptionPane.showMessageDialog(TestAlloResto.this, "Formulaire validé","validation", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(cardPanel, "Panel3");
                    }

                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Erreur Format");
                }


            }
        });
        retourLiv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(TestAlloResto.this, "Etes-vous sûr? Vos informations ne seront pas enregistrer.", "Attention", JOptionPane.OK_OPTION);
                cardLayout.show(cardPanel, "Panel3");
            }
        });


        // Ajouter les panneaux au CardLayout
        cardPanel.add(panel1, "Panel1");
        cardPanel.add(panel2, "Panel2");
        cardPanel.add(panel3, "Panel3");
        cardPanel.add(panel4, "Panel4");
        cardPanel.add(panel5, "Panel5");
        cardPanel.add(panel6, "Panel6");

        // Ajouter le CardPanel à  la fenêtre principale
        add(cardPanel);

        // Afficher le premier panneau au dÃ©marrage
        cardLayout.show(cardPanel, "Panel1");
    }

    public static void main(String[] args) {
        // CrÃ©er et afficher la fenêtre principale
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TestAlloResto ff= new TestAlloResto();
                ff.setVisible(true);
            }
        });
    }
}
