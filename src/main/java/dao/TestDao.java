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

        // read and print all entities ordered by id
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("========== ADRESSES ==========");
        List<Adresse> adresses = session
                .createQuery("from Adresse a order by a.idAdresse", Adresse.class)
                .list();
        adresses.forEach(a -> System.out.println(
                "Adresse{id=" + a.getIdAdresse()
                        + ", ligne='" + a.getAdresseLigne() + '\''
                        + ", ville='" + a.getVille() + "'}"
        ));

        System.out.println("========== CLIENTS ==========");
        List<Client> clients = session
                .createQuery("from Client c order by c.id", Client.class)
                .list();
        clients.forEach(client -> System.out.println(
                "Client{id=" + client.getId()
                        + ", nom='" + client.getFirstName() + " " + client.getLastName() + '\''
                        + ", dateNaissance=" + sdf.format(client.getDateNaissance())
                        + ", adresseVille='" + client.getAdresse().getVille() + "'}"
        ));

        System.out.println("========== COMPTES ==========");
        List<Compte> comptes = session
                .createQuery("from Compte cp order by cp.id", Compte.class)
                .list();
        comptes.forEach(cp -> {
            String details = "Compte{id=" + cp.getId()
                    + ", type=" + cp.getClass().getSimpleName()
                    + ", solde=" + cp.getSolde()
                    + ", dateCreation=" + cp.getDateCreation()
                    + ", clientId=" + (cp.getClient() != null ? cp.getClient().getId() : null);
            if (cp instanceof CompteCourant) {
                details += ", decouvert=" + ((CompteCourant) cp).getDecouvert();
            } else if (cp instanceof CompteEpargne) {
                details += ", taux=" + ((CompteEpargne) cp).getTaux();
            }
            details += "}";
            System.out.println(details);
        });

        System.out.println("========== OPERATIONS ==========");
        List<Operation> operations = session
                .createQuery("from Operation o order by o.id", Operation.class)
                .list();
        operations.forEach(o -> System.out.println(
                "Operation{id=" + o.getId()
                        + ", type='" + o.getTypeOperation() + '\''
                        + ", montant=" + o.getMontant()
                        + ", dateOperation=" + o.getDateOperation()
                        + ", compteId=" + (o.getCompte() != null ? o.getCompte().getId() : null)
                        + "}"
        ));

        System.out.println("========== EMPLOYEES ==========");
        List<Employee> employees = session
                .createQuery("from Employee e order by e.id", Employee.class)
                .list();
        employees.forEach(e -> System.out.println(
                "Employee{id=" + e.getId()
                        + ", name='" + e.getName() + '\''
                        + ", salary=" + e.getSalary()
                        + ", affiliation=" + (e.getAffiliation() != null ? e.getAffiliation().getNumAffiliation() : null)
                        + "}"
        ));

        System.out.println("========== AFFILIATIONS CNSS ==========");
        List<AffiliationCNSS> affiliations = session
                .createQuery("from AffiliationCNSS a order by a.numAffiliation", AffiliationCNSS.class)
                .list();
        affiliations.forEach(a -> System.out.println(
                "AffiliationCNSS{num='" + a.getNumAffiliation() + '\''
                        + ", date='" + a.getDateAffiliation() + '\''
                        + ", points=" + a.getNbrPoint()
                        + ", employeeId=" + (a.getEmp() != null ? a.getEmp().getId() : null)
                        + "}"
        ));

        session.getTransaction().commit();
    }
}
