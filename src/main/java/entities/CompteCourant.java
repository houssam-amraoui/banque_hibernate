package entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CC")
public class CompteCourant extends Compte {

    private double decouvert;

    public CompteCourant() {}

    public double getDecouvert() { return decouvert; }
    public void setDecouvert(double decouvert) { this.decouvert = decouvert; }
}