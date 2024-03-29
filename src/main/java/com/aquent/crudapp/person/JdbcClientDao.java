package com.aquent.crudapp.person;

import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcClientDao implements ClientDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcClientDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Client> listClients() {
        // Implement method to list all clients
    }

    @Override
    public Integer createClient(Client client) {
        // Implement method to create a new client
    }

    @Override
    public Client readClient(Integer clientId) {
        // Implement method to read a client by ID
    }

    @Override
    public void updateClient(Client client) {
        // Implement method to update an existing client
    }

    @Override
    public void deleteClient(Integer clientId) {
        // Implement method to delete a client by ID
    }
}
