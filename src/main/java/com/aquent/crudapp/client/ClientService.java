package com.aquent.crudapp.client;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ClientService {

    List<Client> listClients();

    Client createClient(Client client, List<Integer> contactIds);

    Client getClient(Integer clientId);

    Client updateClient(Client client);

    void deleteClient(Integer clientId);
    
    List<String> validateClient(Client client);
    
}
