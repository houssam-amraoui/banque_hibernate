package dao;

import entities.*;
import util.HibernateUtil;
import org.hibernate.Session;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class TestDao {
    public static void main(String[] args) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        // save adresse
        Adresse adresse = new Adresse("hay elhassania", "sale");
        session.save(adresse);

        // save client
        Client c = new Client();
        c.setFirstName("houssam");
        c.setLastName("amraoui");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        c.setDateNaissance(sdf.parse("17/04/1999"));
        c.setAdresse(adresse);
        session.save(c);

        // save compte courant
        CompteCourant compte = new CompteCourant();
        compte.setSolde(1000);
        compte.setDecouvert(500);
        compte.setDateCreation(new Date());
        compte.setClient(c);
        c.getComptes().add(compte);
        session.save(compte);

        // save compte epargne
        CompteEpargne compteEpargne = new CompteEpargne();
        compteEpargne.setSolde(2500);
        compteEpargne.setTaux(2.5);
        compteEpargne.setDateCreation(new Date());
        compteEpargne.setClient(c);
        c.getComptes().add(compteEpargne);
        session.save(compteEpargne);

        // save operations for compte courant
        Operation op1 = new Operation();
        op1.setTypeOperation("Versement");
        op1.setMontant(200);
        op1.setDateOperation(new Date());
        op1.setCompte(compte);
        compte.getOperations().add(op1);
        session.save(op1);

        Operation op2 = new Operation();
        op2.setTypeOperation("Retrait");
        op2.setMontant(-300);
        op2.setDateOperation(new Date());
        op2.setCompte(compte);
        compte.getOperations().add(op2);
        session.save(op2);

        // save operation for compte epargne
        Operation op3 = new Operation();
        op3.setTypeOperation("Versement");
        op3.setMontant(800);
        op3.setDateOperation(new Date());
        op3.setCompte(compteEpargne);
        compteEpargne.getOperations().add(op3);
        session.save(op3);

        // save employee + affiliation CNSS
        Employee employee = new Employee();
        employee.setName("imane");
        employee.setSalary(7200);

        AffiliationCNSS affiliation = new AffiliationCNSS();
        affiliation.setNumAffiliation("CNSS-2026-001");
        affiliation.setDateAffiliation("13/04/2026");
        affiliation.setNbrPoint(54);
        affiliation.setEmp(employee);
        employee.setAffiliation(affiliation);
        session.save(employee);

        session.getTransaction().commit();

        // read and print relation graphs:
        // 1) client and everything related to it
        // 2) employee and everything related to it
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("========== CLIENT GRAPH (Client + Adresse + Comptes + Operations) ==========");
        List<Client> clients = session
                .createQuery("from Client c order by c.id", Client.class)
                .list();
        clients.forEach(client -> {
            System.out.println("Client{id=" + client.getId()
                    + ", nom='" + client.getFirstName() + " " + client.getLastName() + '\''
                    + ", dateNaissance=" + sdf.format(client.getDateNaissance())
                    + "}");

            Adresse a = client.getAdresse();
            if (a != null) {
                System.out.println("  Adresse{id=" + a.getIdAdresse()
                        + ", ligne='" + a.getAdresseLigne() + '\''
                        + ", ville='" + a.getVille() + "'}");
            }

            client.getComptes().stream()
                    .sorted((c1, c2) -> Long.compare(c1.getId(), c2.getId()))
                    .forEach(cp -> {
                        String compteDetails = "  Compte{id=" + cp.getId()
                                + ", type=" + cp.getClass().getSimpleName()
                                + ", solde=" + cp.getSolde()
                                + ", dateCreation=" + cp.getDateCreation();
                        if (cp instanceof CompteCourant) {
                            compteDetails += ", decouvert=" + ((CompteCourant) cp).getDecouvert();
                        } else if (cp instanceof CompteEpargne) {
                            compteDetails += ", taux=" + ((CompteEpargne) cp).getTaux();
                        }
                        compteDetails += "}";
                        System.out.println(compteDetails);

                        cp.getOperations().stream()
                                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                                .forEach(o -> System.out.println(
                                        "    Operation{id=" + o.getId()
                                                + ", type='" + o.getTypeOperation() + '\''
                                                + ", montant=" + o.getMontant()
                                                + ", dateOperation=" + o.getDateOperation()
                                                + "}"
                                ));
                    });
            System.out.println();
        });

        System.out.println("========== EMPLOYEE GRAPH (Employee + AffiliationCNSS) ==========");
        List<Employee> employees = session
                .createQuery("from Employee e order by e.id", Employee.class)
                .list();
        employees.forEach(e -> {
            System.out.println("Employee{id=" + e.getId()
                    + ", name='" + e.getName() + '\''
                    + ", salary=" + e.getSalary()
                    + "}");

            AffiliationCNSS a = e.getAffiliation();
            if (a != null) {
                System.out.println("  AffiliationCNSS{num='" + a.getNumAffiliation() + '\''
                        + ", date='" + a.getDateAffiliation() + '\''
                        + ", points=" + a.getNbrPoint()
                        + "}");
            }
            System.out.println();
        });

        session.getTransaction().commit();
    }
}
