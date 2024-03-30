package com.aquent.crudapp.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service
public class DefaultClientService implements ClientService {

    private final ClientDao clientDao;
    private final Validator validator;


    @Autowired
    public DefaultClientService(ClientDao clientDao, Validator validator) {
        this.clientDao = clientDao;
        this.validator = validator;
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

    @Override
    public List<String> validateClient(Client client) {
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        List<String> errors = new ArrayList<>(violations.size());
        for (ConstraintViolation<Client> violation : violations) {
            errors.add(violation.getMessage());
        }
        Collections.sort(errors);
        return errors;
    }
}
