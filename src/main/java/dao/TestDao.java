package dao;

import entities.*;
import util.HibernateUtil;
import org.hibernate.Session;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TestDao {
    public static void main(String[] args) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        //save adresse
        Adresse adresse = new Adresse("hay elhassania", "sale");
        session.save(adresse);

        //save client
        Client c = new Client();
        c.setFirstName("houssam");
        c.setLastName("amraoui");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        c.setDateNaissance(sdf.parse("17/04/1999"));
        c.setAdresse(adresse);
        session.save(c);

        //save compte
        CompteCourant compte = new CompteCourant();
        compte.setSolde(1000);
        compte.setDecouvert(500);
        compte.setDateCreation(new Date());
        compte.setClient(c);
        c.getComptes().add(compte);
        session.save(compte);

        //save operation versement
        Operation op = new Operation();
        op.setTypeOperation("Versement");
        op.setMontant(200);
        op.setDateOperation(new Date());
        op.setCompte(compte);
        compte.getOperations().add(op);
        session.save(op);

        //save operation retrait
        Operation op = new Operation();
        op.setTypeOperation("retrait");
        op.setMontant(-300);
        op.setDateOperation(new Date());
        op.setCompte(compte);
        compte.getOperations().add(op);
        session.save(op);

        session.getTransaction().commit();

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.createQuery("from Client", Client.class)
                .list()
                .forEach(client -> System.out.println(
                        client.getFirstName() + " " + client.getLastName() +
                                " - " + client.getAdresse().getVille()
                ));

        session.getTransaction().commit();
    }
}