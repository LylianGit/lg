
public class Loup extends Role{
    boolean aFaim;

    public Loup(boolean aFaim){
        super.role = "Loup";
        super.camp = "Loup Garou";
        super.pouvoir = "Manger un villageois";
        this.aFaim = aFaim;
    }

   /* public void Manger(Joueur j){
        if(aFaim == true){
            j.AlAgonie();
        }
    }*/
}
