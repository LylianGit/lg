
public class Sorciere extends Villageois{
    private boolean potionDispoVie;
    private boolean potionDispoMort;

    public Sorciere() {
        super.role = "Sorcière";
        super.pouvoir = "Peut confectionner une potion (soit de vie, soit de mort)";
        potionDispoVie = true;
        potionDispoMort = true;
    }

    public boolean getPotionDispoVie(){
        return potionDispoVie;
    }
    public boolean getPotionDispoMort(){
        return potionDispoMort;
    }
    public void utiliserPotionVie(Joueur j){
        if(potionDispoVie == true){
            j.SeSoigne();
            potionDispoVie = false;
        }
    }

    public void utiliserPotionMort(Joueur j){
        if(potionDispoMort == true){
            j.AlAgonie();
            potionDispoMort = false;
        }
    }
    


}
