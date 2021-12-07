
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Test {
    static int jmax = 5;
    static int jmin = 4;
    
    /*
    *
    *
    */
    public static void main(String[] args) 
    { 
        /* NOMBRE DE JOUEURS */
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir le nombre de joueurs (entre "+jmin+" et "+jmax+") :");
        String str = "" ;//= sc.nextLine();
        int nbJoueurs = 5;//Integer.parseInt(str);
        while(nbJoueurs < jmin || nbJoueurs > jmax){
            System.out.println("Erreur le nombre de joueurs doit être entre "+jmin+" et "+jmax+", vous avez écrit : " + str);
            str = sc.nextLine();
            nbJoueurs = Integer.parseInt(str);
        }

        /* CREATION JOUEURS */
        List<Joueur> listJ = new ArrayList<Joueur>();
        System.out.println("Entrer le pseudo de chaque joueur : ");
        listJ.add(new Joueur("One Punch"));
        listJ.add(new Joueur("Thorfinn"));
        listJ.add(new Joueur("Jarvan IV"));
        listJ.add(new Joueur("Yasuo"));
        listJ.add(new Joueur("Monkey D. Luffy"));
        /*for (int i=0; i<nbJoueurs; i++){
            str = sc.nextLine();
            listJ.add(new Joueur(str));
        }*/
        RoleAleatoire(nbJoueurs, listJ); // Mélange des roles et distribution d'un role à chaque joueur
        
        /* DEBUT JEU */
        boolean Fini = false;
        int tour = 1;
        while(Fini != true)
        {
            //jour
            for(int i=0; i<listJ.size(); i++) //debut tour joueur
            {
                if(listJ.get(i).estVivant()){
                    System.out.print("\033[H\033[2J");
                    System.out.println("Tour de " + listJ.get(i).getPseudo());
                    System.out.print("OK");
                    str = sc.nextLine();
                    System.out.println("Votre role : " + listJ.get(i).getRole().getRole());
                    System.out.println("Jour n°" + tour);
                    AffichageVoteJour(nbJoueurs, listJ, i);
                    System.out.println("Votez qui mettre au bûcher (écrire un numéro) :");
                    listJ.get(verifNb( sc,  str,  nbJoueurs,  i,  listJ)).Voted();
                }
                    
            }  //fin tour joueur

            System.out.print("\033[H\033[2J");
            int x = DecisionDuVote(listJ);
            //System.out.println(x);
            if(x == -1){
                System.out.println("Egalité");
            }
            else{
                listJ.get(x).SeMeurt();
                System.out.println("Joueur tué : "+listJ.get(x).getPseudo());
                System.out.println("Joueurs en vie : ");
                for(int j=0; j<nbJoueurs; j++){
                    if(listJ.get(j).estVivant())
                        System.out.println("Joueur n°"+j+" : "+listJ.get(j).getPseudo()); 
                }
            }
            str = sc.nextLine();
            //fin jour 
        
            int countLG = 0;
            int countVilla = 0;
            for(int i=0; i<listJ.size(); i++) {
                if(listJ.get(i).getRole().getCamp() == "Loup Garou" && listJ.get(i).estVivant())
                    countLG++;
                else if(listJ.get(i).getRole().getCamp() == "Village" && listJ.get(i).estVivant())
                    countVilla++;
            }

            //Vérifier si la partie doit continuer :
            //si il n'y a plus de loup STOP
            //si il n'y a plus assez de villageois STOP
            if(countVilla == 0 && countLG == 0 || countVilla > 0 && countLG == 0 || countVilla == 0 && countLG > 0){
                Fini = true;
                if(countVilla == 0 && countLG == 0){
                    System.out.println("Egalité ! Tout le monde est mort.");
                }
                if(countVilla > 0 && countLG == 0){
                    System.out.println("Les villageois ont triomphés du mal ! GG !");
                }
                if(countVilla == 0 && countLG > 0){
                    System.out.println("Les Loups Garou ont gagnés !! Ahouuuu");
                }

                str = sc.nextLine();
            }
                
            if(Fini == false){
                for(int i=0; i<listJ.size(); i++)//nuit
                {
                    if(listJ.get(i).estVivant()){ //debut tour joueur
                        System.out.print("\033[H\033[2J");
                        System.out.println("Tour de " + listJ.get(i).getPseudo());
                        System.out.print("OK");
                        str = sc.nextLine();
                        System.out.println("Votre role : " + listJ.get(i).getRole().getRole());
                        System.out.println("Nuit n°" + tour);
                        
                        if(listJ.get(i).getRole().getRole() == "Loup"){//debut loup
                            AffichageVoteNuitLG(nbJoueurs, listJ, i);
                            System.out.println("Votez qui manger (écrire un numéro) :");
                            listJ.get(verifNb( sc,  str,  nbJoueurs,  i,  listJ)).Voted();
                        }//fin loup
                        else if(listJ.get(i).getRole().getRole() == "Sorcière"){ //debut sorciere
                            //System.out.println("Joueurs en vie : ");
                            boolean error;
                            do{
                                error = false;
                                AffichageSansVote(nbJoueurs, listJ, i);
                                System.out.println("Quelle potion allez vous utiliser?");
                                if(((Sorciere) listJ.get(i).getRole()).getPotionDispoVie() && ((Sorciere) listJ.get(i).getRole()).getPotionDispoMort())
                                    System.out.println("(v pour vie, m pour mort, a pour aucune)");
                                else if(((Sorciere) listJ.get(i).getRole()).getPotionDispoVie())
                                    System.out.println("(v pour vie, a pour aucune)");
                                else if(((Sorciere) listJ.get(i).getRole()).getPotionDispoMort())
                                    System.out.println("(m pour mort, a pour aucune)");
                                else{
                                    System.out.println("(a pour aucune)");
                                }
                                str = sc.nextLine();
                                char carac = str.charAt(0);
                                //System.out.println("DEBUT"+carac+"FIN");
                                //str = sc.nextLine();
                                if(carac == 'v'){
                                    if(((Sorciere) listJ.get(i).getRole()).getPotionDispoVie()){
                                        System.out.println("Sauver qui? (écrire numéro)");
                                    ((Sorciere) listJ.get(i).getRole()).utiliserPotionVie(listJ.get(verifNb(sc, str, nbJoueurs, i, listJ)));
                                    }else{
                                        System.out.print("\033[H\033[2J");
                                        System.out.println("Vous avez déjà utilisé votre potion de vie.");
                                        error =true;
                                    }
                                    
                                }
                                else if(carac == 'm'){
                                    if(((Sorciere) listJ.get(i).getRole()).getPotionDispoMort()){
                                        System.out.println("Tuer qui? (écrire numéro)");
                                        ((Sorciere) listJ.get(i).getRole()).utiliserPotionMort(listJ.get(verifNb(sc, str, nbJoueurs, i, listJ)));
                                    }
                                    else{
                                        System.out.print("\033[H\033[2J");
                                        System.out.println("Vous avez déjà utilisé votre potion de mort.");
                                        error = true;
                                    }
                                }
                            }while(error == true);
                        }//fin sorciere
                        else{ //autre (villageois)
                            /*System.out.println("Joueurs en vie : ");
                            for(int j=0; j<nbJoueurs; j++){
                                if(listJ.get(j).estVivant() && listJ.get(j) != listJ.get(i) && listJ.get(i).getRole().getRole() != "Loup")
                                    System.out.println("Joueur n°"+j+" : "+listJ.get(j).getPseudo()); 
                            }*/
                            System.out.println("Vous n'avez rien à faire cette nuit");
                            System.out.print("OK");
                            str = sc.nextLine();
                        } // fin autre
                    }
                } //fin tour joueur


                System.out.print("\033[H\033[2J");
                x = DecisionDuVote(listJ);
                boolean mort = false;
                //System.out.println(x);
                if(x == -1){
                    System.out.println("Les loups n'ont pas pu décider qui tuer (égalité des votes).");
                }else{
                    //Manger ;
                    listJ.get(x).AlAgonie();
                }
                for(int j=0; j<nbJoueurs; j++){
                    if(listJ.get(j).Agonise()){
                        System.out.println("Joueur tué : "+listJ.get(j).getPseudo());
                        mort = true;
                    }       
                }
                if(mort == false){
                    System.out.println("Personne n'est mort cette nuit");
                }
                System.out.println("Joueurs en vie : ");
                for(int j=0; j<nbJoueurs; j++){
                    if(listJ.get(j).estVivant())
                        System.out.println("Joueur n°"+j+" : "+listJ.get(j).getPseudo()); 
                }
                //fin nuit

                countLG = 0;
                countVilla = 0;
                for(int i=0; i<listJ.size(); i++) {
                    if(listJ.get(i).getRole().getCamp() == "Loup Garou" && listJ.get(i).estVivant())
                        countLG++;
                    else if(listJ.get(i).getRole().getCamp() == "Village" && listJ.get(i).estVivant())
                        countVilla++;
                }

                //Vérifier si la partie doit continuer :
                //si il n'y a plus de loup STOP
                //si il n'y a plus assez de villageois STOP
                if(countVilla == 0 && countLG == 0 || countVilla > 0 && countLG == 0 || countVilla == 0 && countLG > 0){
                    Fini = true;
                    if(countVilla == 0 && countLG == 0){
                        System.out.println("Egalité ! Tout le monde est mort.");
                    }
                    if(countVilla > 0 && countLG == 0){
                        System.out.println("Les villageois ont triomphés du mal ! GG !");
                    }
                    if(countVilla == 0 && countLG > 0){
                        System.out.println("Les Loups Garou ont gagnés !! Ahouuuu");
                    }
                }
                str = sc.nextLine();            
            }
        }
        sc.close();
    }
    

    /**
     * 
     * 
     */
    public static int DecisionDuVote(List<Joueur> list){
        int max = 0;
        boolean egalite = false;
        int tmp = -1;
        for(int i=0; i<list.size(); i++){
            if(list.get(i).getVoted() > max){
                max = list.get(i).getVoted();
                tmp = i;
                egalite = false;
            }
            else if(list.get(i).getVoted() == max){
                egalite = true;
            }   
        }

        if(egalite == true)
            max = -1;
        
        for(int i=0; i<list.size(); i++){
            list.get(i).RAZ();
        }

        if(max == -1)
            return -1;
        else
            return tmp;      
    }


    /**
     * 
     * 
     */
    public static void RoleAleatoire(int nbJoueurs, List<Joueur> listJr){

        /* ROLES DE LA PARTIE */

        List<Role> list = new ArrayList<Role>();
        Villageois R1 = new Villageois();
        list.add(R1);
        list.add(new Loup(true));
        list.add(new Villageois());
        list.add(new Villageois());
        if (nbJoueurs >= 5){
            list.add(new Sorciere());
        }


        /* MELANGE DES ROLES */

        List<Role> listM = new ArrayList<Role>();
        for (int i=0; i<list.size(); i++) 
        {
            Random aleatoire = new Random(); // Génération d'un nombre aléatoire 
            int n = aleatoire.nextInt(nbJoueurs); // entre 0 et nbJoueurs moins 1
            while(listM.contains(list.get(n))){
                n = aleatoire.nextInt(nbJoueurs);   
            }
            if(!listM.contains(n))
                listM.add(list.get(n));
        }

        for(int i=0; i<listJr.size(); i++){
            listJr.get(i).setRole(listM.get(i));
        }

        /* AFFICHAGE */

        /*for(int i=0; i<listM.size(); i++) {
            //System.out.println(listM.get(i).Afficher()); 
        }*/

    }
    
    public static int verifNb(Scanner sc, String str, int nbJoueurs, int i, List<Joueur> listJ){
        boolean valide = false;
        int numero = -1;
        while(valide == false) {
            str = sc.nextLine();
            try{
                numero = Integer.parseInt(str);
                if(numero >= 0 && numero < nbJoueurs && numero != i && listJ.get(numero).estVivant()){
                    valide = true;
                }
                else{
                    System.out.println("Numéro non valide, en choisir un parmi ceux disponible au dessus : ");
                }
            } 
            catch(NumberFormatException e){
                System.out.println("Erreur, veuillez écrire seulement un chiffre parmi les numéros figurant au dessus: ");
            }
            
        }
        return numero;
    }

    public static int verifChar(Scanner sc, String str, int nbJoueurs, int i){
        boolean valide = false;
        char carac = 'a';
        while(valide == false) {
            str = sc.nextLine();
            try{
                carac = str.charAt(0);
                valide = true;
            } 
            catch(NumberFormatException e){
                System.out.println("Erreur, veuillez écrire seulement un caractère.");         
            }
            
        }
        return carac;
    }

    public static void AffichageSansVote(int nbJoueurs, List<Joueur> listJ, int i){
        System.out.println("Joueurs en vie : ");
        for(int j=0; j<nbJoueurs; j++){
            if(listJ.get(j).estVivant() && listJ.get(j) != listJ.get(i)){
                int n = listJ.get(j).getVoted();
                System.out.println("Joueur n°"+j+" : "+listJ.get(j).getPseudo());
            }
            
        }
    }

    public static void AffichageVoteJour(int nbJoueurs, List<Joueur> listJ, int i){
        System.out.println("Joueurs en vie : ");
        for(int j=0; j<nbJoueurs; j++){
            if(listJ.get(j).estVivant() && listJ.get(j) != listJ.get(i)){
                int n = listJ.get(j).getVoted();
                System.out.println("Joueur n°"+j+" : "+listJ.get(j).getPseudo()+"("+n+" votes)");
            }
            
        }
    }
    

    public static void AffichageVoteNuitLG(int nbJoueurs, List<Joueur> listJ, int i){
        
        System.out.println("Joueurs en vie : ");
        for(int j=0; j<nbJoueurs; j++){
            if(listJ.get(j).estVivant() && listJ.get(j) != listJ.get(i)){
                if(listJ.get(j).getRole().getCamp() != "Loup"){
                    int n = listJ.get(j).getVoted();
                    System.out.println("Joueur n°"+j+" : "+listJ.get(j).getPseudo()+"("+n+" votes)");
                }
                else{
                    System.out.println("Joueur n°"+j+" : "+listJ.get(j).getPseudo()+ " " + listJ.get(j).getRole().getRole());
                }
                
            }        
        }
    }
}