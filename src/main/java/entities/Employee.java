package entities;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double salary;

    @OneToOne(mappedBy = "emp", cascade = CascadeType.ALL)
    private AffiliationCNSS affiliation;

    public Employee() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public AffiliationCNSS getAffiliation() { return affiliation; }
    public void setAffiliation(AffiliationCNSS affiliation) { this.affiliation = affiliation; }
}