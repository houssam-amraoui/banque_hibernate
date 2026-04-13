package entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeOperation;
    private double montant;
    private Date dateOperation;

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private Compte compte;

    public Operation() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTypeOperation() { return typeOperation; }
    public void setTypeOperation(String typeOperation) { this.typeOperation = typeOperation; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public Date getDateOperation() { return dateOperation; }
    public void setDateOperation(Date dateOperation) { this.dateOperation = dateOperation; }

    public Compte getCompte() { return compte; }
    public void setCompte(Compte compte) { this.compte = compte; }

    @Override
    public String toString() {
        return "Operation [id=" + id + ", type=" + typeOperation + ", montant=" + montant + "]";
    }
}