package dao;

import entities.Client;
import entities.Compte;
import entities.Operation;
import util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class DaoImpl implements IDao {

    @Override
    public void addClient(Client cl) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(cl);
        session.getTransaction().commit();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Client> getAllClient() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Client> cls = session.createQuery("from Client").list();
        session.getTransaction().commit();
        return cls;
    }

    @Override
    public Client getClientById(Long id) {
        Session ses = HibernateUtil.getSessionFactory().getCurrentSession();
        ses.beginTransaction();
        Client cl = ses.get(Client.class, id);
        ses.getTransaction().commit();
        return cl;
    }

    @Override
    public void deleteClient(Long id) {
        Session ses = HibernateUtil.getSessionFactory().getCurrentSession();
        ses.beginTransaction();
        Client cl = ses.get(Client.class, id);
        if (cl != null) {
            ses.delete(cl);
        }
        ses.getTransaction().commit();
    }

    @Override
    public void updateClient(Long id, Client cl) {
        Session ses = HibernateUtil.getSessionFactory().getCurrentSession();
        ses.beginTransaction();
        ses.update(cl);
        ses.getTransaction().commit();
    }

    @Override
    public void addCompte(Compte cp, Long codeClient) {
        Session ses = HibernateUtil.getSessionFactory().getCurrentSession();
        ses.beginTransaction();
        Client cl = ses.get(Client.class, codeClient);
        cl.getComptes().add(cp);
        cp.setClient(cl);
        ses.save(cp);
        ses.getTransaction().commit();
    }

    @Override
    public void addOperation(Operation op, Long numCompte) {
        Session ses = HibernateUtil.getSessionFactory().getCurrentSession();
        ses.beginTransaction();

        Compte cp = ses.get(Compte.class, numCompte);

        if ("Versement".equals(op.getTypeOperation())) {
            cp.setSolde(cp.getSolde() + op.getMontant());
        }

        if ("Retrait".equals(op.getTypeOperation())) {
            if (cp.getSolde() >= op.getMontant()) {
                cp.setSolde(cp.getSolde() - op.getMontant());
            } else {
                System.out.println("Solde insuffisant !");
                ses.getTransaction().rollback();
                return;
            }
        }

        cp.getOperations().add(op);
        op.setCompte(cp);
        ses.save(op);
        ses.getTransaction().commit();
    }
}