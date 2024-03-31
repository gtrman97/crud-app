package com.aquent.crudapp.client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.aquent.crudapp.person.Person;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;


@Component
public class JdbcClientDao implements ClientDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcClientDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Client> listClients() {
        String sql = "SELECT * FROM client ORDER BY company_name, client_id";
        return namedParameterJdbcTemplate.query(sql, new ClientRowMapper());
    }

    @Override
    public Integer createClient(Client client, List<Integer> contactIds) {
    // Insert the client
    String sql = "INSERT INTO client (company_name, website_uri, phone_number, address) VALUES (:companyName, :websiteUri, :phoneNumber, :address)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(client), keyHolder);
    Integer clientId = keyHolder.getKey().intValue();
    
    // Associate the client with its contacts
    String associationSql = "INSERT INTO client_person (client_id, person_id) VALUES (:clientId, :personId)";
    for (Integer contactId : contactIds) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("clientId", clientId);
        paramMap.put("personId", contactId);
        namedParameterJdbcTemplate.update(associationSql, paramMap);
    }
    
    return clientId;
}

@Override
public Client readClient(Integer clientId) {
    String sql = "SELECT c.*, p.person_id, p.first_name, p.last_name FROM client c " +
                 "LEFT JOIN client_person cp ON c.client_id = cp.client_id " +
                 "LEFT JOIN person p ON cp.person_id = p.person_id " +
                 "WHERE c.client_id = :clientId";
    
    List<Client> clients = namedParameterJdbcTemplate.query(sql, Collections.singletonMap("clientId", clientId), new ClientRowMapper());
    if (!clients.isEmpty()) {
        return clients.get(0);
    } else {
        return null;
    }
}

public Client getClientWithContacts(Integer clientId) {
    String sql = "SELECT c.*, p.person_id, p.first_name, p.last_name FROM client c " +
                 "LEFT JOIN client_person cp ON c.client_id = cp.client_id " +
                 "LEFT JOIN person p ON cp.person_id = p.person_id " +
                 "WHERE c.client_id = :clientId";
    List<Client> clients = namedParameterJdbcTemplate.query(sql, Collections.singletonMap("clientId", clientId), new ClientWithContactsRowMapper());
    return clients.isEmpty() ? null : clients.get(0);
}


    @Override
    public void updateClient(Client client) {
        String sql = "UPDATE client SET company_name = :companyName, website_uri = :websiteUri, phone_number = :phoneNumber, address = :address WHERE client_id = :clientId";
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(client));
    }

    @Override
    public void deleteClient(Integer clientId) {
        // Set client_id to NULL for all persons associated with the client
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("clientId", clientId);
        namedParameterJdbcTemplate.update("UPDATE person SET client_id = NULL WHERE client_id = :clientId", params);

        // Delete the client
        namedParameterJdbcTemplate.update("DELETE FROM client WHERE client_id = :clientId", params);
    }

    private static final class ClientRowMapper implements RowMapper<Client> {
        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            Client client = new Client();
            client.setClientId(rs.getInt("client_id"));
            client.setCompanyName(rs.getString("company_name"));
            client.setWebsiteUri(rs.getString("website_uri"));
            client.setPhoneNumber(rs.getString("phone_number"));
            client.setAddress(rs.getString("address"));
            return client;
        }
    }

    private static final class ClientWithContactsRowMapper implements RowMapper<Client> {
        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            Client client = new Client();
            client.setClientId(rs.getInt("client_id"));
            client.setCompanyName(rs.getString("company_name"));
            client.setWebsiteUri(rs.getString("website_uri"));
            client.setPhoneNumber(rs.getString("phone_number"));
            client.setAddress(rs.getString("address"));
    
            List<Integer> contactIds = new ArrayList<>();
            do {
                int personId = rs.getInt("person_id");
                if (personId != 0) {
                    contactIds.add(personId);
                }
            } while (rs.next() && rs.getInt("client_id") == client.getClientId());
    
            client.setContacts(contactIds);
    
            return client;
        }
    }
    
}

