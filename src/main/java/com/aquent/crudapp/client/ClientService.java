package com.aquent.crudapp.client;

import java.util.List;

public interface ClientService {

    List<Client> listClients();

    Client createClient(Client client);

    Client getClient(Integer clientId);

    Client updateClient(Client client);

    void deleteClient(Integer clientId);
    
}
