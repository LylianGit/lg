
public abstract class Role{
    protected String role;
    protected String camp;
    protected String pouvoir;
    //private boolean enVie;

    public Role(){
        this.role = "Aucun";
        this.camp = "Aucun";
        this.pouvoir = "Aucun";
        //this.enVie = true;
    }

    /* ACCESSEURS */

    /*public boolean getEnVie(){
        return enVie;
    }*/

    public String getCamp(){
        return camp;
    }

    public String getRole(){
        return role;
    }

    /* METHODES */

    public String Afficher(){
        return "Role:"+role+" Camp:"+camp+" Pouvoir:"+pouvoir;//+" Vie:"+enVie;
    }

    /*public void SeMeurt(){
        if(enVie == true)
            enVie = false;
    }*/

    /*public void Ressucite(){
        if(enVie == false)
            enVie = true;
    }*/





}

