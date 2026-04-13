package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "adresses")
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAdresse;

    private String adresseLigne;
    private String ville;

    @OneToMany(mappedBy = "adresse", cascade = CascadeType.ALL)
    private Set<Client> clients = new HashSet<>();

    public Adresse() {}
    public Adresse(String adresseLigne, String ville) {
        this.adresseLigne = adresseLigne;
        this.ville = ville;
    }

    public int getIdAdresse() { return idAdresse; }
    public void setIdAdresse(int idAdresse) { this.idAdresse = idAdresse; }

    public String getAdresseLigne() { return adresseLigne; }
    public void setAdresseLigne(String adresseLigne) { this.adresseLigne = adresseLigne; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public Set<Client> getClients() { return clients; }
    public void setClients(Set<Client> clients) { this.clients = clients; }

    @Override
    public String toString() {
        return "Adresse [idAdresse=" + idAdresse + ", adresseLigne=" + adresseLigne + ", ville=" + ville + "]";
    }
}