public class Joueur {
    private String pseudo;
    private Role role;
    private int voted;
    private boolean agonise;
    private boolean soin;
    private boolean enVie;

    public Joueur(String pseudo){
        this.pseudo = pseudo;
        this.role = null;
        this.voted = 0;
        agonise = false;
        soin = false;
        enVie = true;
    }

    /* ACCESSEURS */

    public String getPseudo(){
        return this.pseudo;
    }

    public Role getRole(){
        return role;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public int getVoted(){
        return voted;
    }

    /* METHODES */

    public boolean estVivant(){
        return enVie;
    }

    public void AlAgonie(){
        agonise = true;
    }

    public boolean Agonise(){
        if(agonise == true && soin == false){
            SeMeurt();
            return true;
        }
        if(soin == true){
            soin = false;
        }
        agonise = false;
        return false;
            
    }

    public void SeSoigne(){
        soin = true;
    }

    public void SeMeurt(){
        enVie = false;
    }

    public void Voted(){
        this.voted += 1;
    }
    public void Voter(Joueur j){
        j.Voted();
    }
    public void RAZ(){
        this.voted = 0;
    }

    /*public int RoleAleatoire(int nbJoueurs){
        if(nbJoueurs < 4 || nbJoueurs > 5)
            return -1;
        Role r1 = new Villageois();
        Role r2 = new Loup(true);
        Role r3 = new Villageois();
        Role r4 = new Villageois();
        Role [] lesRoles = { r1, r2, r3, r4};
        if (nbJoueurs >= 5){
            Role r5 = new Sorciere();
            lesRoles[4] = r5;
        }
        Role [] lesRolesMelanges = {};
        int count = 0;
        for (Role r : lesRoles) {
            // génération d'un nombre >= 0 et < nbJoueurs
            Random aleatoire = new Random();
            int n = aleatoire.nextInt(nbJoueurs); 
            while(lesRolesMelanges[n] != null){
                n = aleatoire.nextInt(nbJoueurs); 
            }
            if(lesRolesMelanges[n] != null){
                lesRolesMelanges[n] = r;
                count++;
            }
        }
        return 1;
        
    }*/
}
