package com.aquent.crudapp.person;

import java.util.List;

public interface ClientDao {

    List<Client> listClients();

    Integer createClient(Client client);

    Client readClient(Integer clientId);

    void updateClient(Client client);

    void deleteClient(Integer clientId);
}
