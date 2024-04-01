package com.aquent.crudapp.person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aquent.crudapp.client.Client;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring JDBC implementation of {@link PersonDao}.
 */
@Component
public class JdbcPersonDao implements PersonDao {

    private static final String SQL_LIST_PEOPLE = "SELECT * FROM person ORDER BY first_name, last_name, person_id";
    private static final String SQL_READ_PERSON = "SELECT * FROM person WHERE person_id = :personId";
    private static final String SQL_DELETE_PERSON = "DELETE FROM person WHERE person_id = :personId";
    private static final String SQL_UPDATE_PERSON = "UPDATE person SET first_name = :firstName, last_name = :lastName, email_address = :emailAddress, street_address = :streetAddress, city = :city, state = :state, zip_code = :zipCode, client_id = :clientId WHERE person_id = :personId";

    private static final String SQL_CREATE_PERSON = "INSERT INTO person (first_name, last_name, email_address, street_address, city, state, zip_code)"
                                                  + " VALUES (:firstName, :lastName, :emailAddress, :streetAddress, :city, :state, :zipCode, :clientId)";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcPersonDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, NamedParameterJdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Person> listPeople() {
        return namedParameterJdbcTemplate.getJdbcOperations().query(SQL_LIST_PEOPLE, new PersonRowMapper());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Person readPerson(Integer personId) {
    String sql = "SELECT * FROM person WHERE person_id = :personId";
    Map<String, Object> params = new HashMap<>();
    params.put("personId", personId);
    return namedParameterJdbcTemplate.queryForObject(sql, params, new PersonRowMapper());
}

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void deletePerson(Integer personId) {
        namedParameterJdbcTemplate.update(SQL_DELETE_PERSON, Collections.singletonMap("personId", personId));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void updatePerson(Person person) {
        namedParameterJdbcTemplate.update(SQL_UPDATE_PERSON, new BeanPropertySqlParameterSource(person));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public Integer createPerson(Person person) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_PERSON, new BeanPropertySqlParameterSource(person), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public List<Integer> findContactIdsByClientId(Integer clientId) {
    String sql = "SELECT person_id FROM person WHERE client_id = :clientId";
    Map<String, Object> params = new HashMap<>();
    params.put("clientId", clientId);
    return namedParameterJdbcTemplate.queryForList(sql, params, Integer.class);
}


    @Override
    public List<Person> listPeopleWithClients() {
        String sql = "SELECT p.*, c.company_name FROM person p LEFT JOIN client c ON p.client_id = c.client_id";
        return namedParameterJdbcTemplate.query(sql, new PersonWithClientRowMapper());
    }

    @Override
    public List<Person> findPersonsByIds(List<Integer> personIds) {
    String sql = "SELECT * FROM person WHERE person_id IN (:personIds)";
    Map<String, Object> params = new HashMap<>();
    params.put("personIds", personIds);
    return namedParameterJdbcTemplate.query(sql, params, new PersonRowMapper());
}


    private static class PersonWithClientRowMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person();
            // Set person fields from the result set
            person.setPersonId(rs.getInt("person_id"));
            person.setFirstName(rs.getString("first_name"));
            person.setLastName(rs.getString("last_name"));
            person.setEmailAddress(rs.getString("email_address"));
    
            Client client = new Client();
            client.setCompanyName(rs.getString("company_name")); // Set the company name from the result set
            person.setClient(client); // Set the associated Client object
    
            return person;
        }
    }
    

    /**
     * Row mapper for person records.
     */
    private static final class PersonRowMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person();
            person.setPersonId(rs.getInt("person_id"));
            person.setFirstName(rs.getString("first_name"));
            person.setLastName(rs.getString("last_name"));
            person.setEmailAddress(rs.getString("email_address"));
            person.setStreetAddress(rs.getString("street_address"));
            person.setCity(rs.getString("city"));
            person.setState(rs.getString("state"));
            person.setZipCode(rs.getString("zip_code"));
            person.setClientId(rs.getObject("client_id") != null ? rs.getInt("client_id") : null);
            return person;
        }
    }
}
