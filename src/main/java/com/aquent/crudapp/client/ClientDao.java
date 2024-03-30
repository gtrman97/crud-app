package com.aquent.crudapp.client;

import java.util.List;

public interface ClientDao {

    List<Client> listClients();

    Integer createClient(Client client);

    Client readClient(Integer clientId);

    void updateClient(Client client);

    void deleteClient(Integer clientId);
}
