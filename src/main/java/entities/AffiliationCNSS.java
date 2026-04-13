package entities;

import javax.persistence.*;

@Entity
@Table(name = "affiliation_cnss")
public class AffiliationCNSS {

    @Id
    private String numAffiliation;

    private String dateAffiliation;
    private int nbrPoint;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee emp;

    public AffiliationCNSS() {}

    public String getNumAffiliation() { return numAffiliation; }
    public void setNumAffiliation(String numAffiliation) { this.numAffiliation = numAffiliation; }

    public String getDateAffiliation() { return dateAffiliation; }
    public void setDateAffiliation(String dateAffiliation) { this.dateAffiliation = dateAffiliation; }

    public int getNbrPoint() { return nbrPoint; }
    public void setNbrPoint(int nbrPoint) { this.nbrPoint = nbrPoint; }

    public Employee getEmp() { return emp; }
    public void setEmp(Employee emp) { this.emp = emp; }
}