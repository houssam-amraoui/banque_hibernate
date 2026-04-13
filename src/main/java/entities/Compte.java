package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_compte")
@Table(name = "comptes")
public abstract class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected double solde;
    protected Date dateCreation;

    @ManyToOne
    @JoinColumn(name = "client_id")
    protected Client client;

    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL)
    protected ArrayList<Operation> operations = new ArrayList<>();

    public Compte() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Set<Operation> getOperations() { return operations; }
    public void setOperations(ArrayList<Operation> operations) { this.operations = new HashSet<>(operations); }
}