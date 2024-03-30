package com.aquent.crudapp.person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

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
    public Integer createClient(Client client) {
        String sql = "INSERT INTO client (company_name, website_uri, phone_number, address) VALUES (:companyName, :websiteUri, :phoneNumber, :address)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(client), keyHolder);
        return keyHolder.getKey().intValue();
}

    @Override
    public Client readClient(Integer clientId) {
        String sql = "SELECT * FROM client WHERE client_id = :clientId";
        return namedParameterJdbcTemplate.queryForObject(sql, Collections.singletonMap("clientId", clientId), new ClientRowMapper());
}

    @Override
    public void updateClient(Client client) {
        String sql = "UPDATE client SET company_name = :companyName, website_uri = :websiteUri, phone_number = :phoneNumber, address = :address WHERE client_id = :clientId";
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(client));
}

    @Override
    public void deleteClient(Integer clientId) {
        String sql = "DELETE FROM client WHERE client_id = :clientId";
        namedParameterJdbcTemplate.update(sql, Collections.singletonMap("clientId", clientId));
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


}
