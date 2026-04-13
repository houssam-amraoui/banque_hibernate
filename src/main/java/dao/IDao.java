package dao;

import entities.Client;
import entities.Compte;
import entities.Operation;
import java.util.List;

public interface IDao {
    void addClient(Client cl);
    List<Client> getAllClient();
    Client getClientById(Long id);
    void deleteClient(Long id);
    void updateClient(Long id, Client cl);
    void addCompte(Compte cp, Long codeClient);
    void addOperation(Operation op, Long numCompte);
}