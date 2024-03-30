package com.aquent.crudapp.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultClientService implements ClientService {

    private final ClientDao clientDao;

    @Autowired
    public DefaultClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public List<Client> listClients() {
        return clientDao.listClients();
    }

    @Override
    public Client createClient(Client client) {
        Integer clientId = clientDao.createClient(client);
        return clientDao.readClient(clientId);
    }

    @Override
    public Client getClient(Integer clientId) {
        return clientDao.readClient(clientId);
    }

    @Override
    public Client updateClient(Client client) {
        clientDao.updateClient(client);
        return clientDao.readClient(client.getClientId());
    }

    @Override
    public void deleteClient(Integer clientId) {
        clientDao.deleteClient(clientId);
    }
}
