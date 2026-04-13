package entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CE")
public class CompteEpargne extends Compte {

    private double taux;

    public CompteEpargne() {}

    public double getTaux() { return taux; }
    public void setTaux(double taux) { this.taux = taux; }
}