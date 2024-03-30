package com.aquent.crudapp.person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ApplicationContext;

import com.aquent.crudapp.Application;

import javax.sql.DataSource;

@SpringBootTest(classes = Application.class)
public class ClientDaoTest {

    @Autowired
    private ClientDao clientDao;

    @Test
    public void testCreateClient() {
            // Create a new client
            Client newClient = new Client();
            newClient.setCompanyName("Test Company");
            newClient.setWebsiteUri("http://www.testcompany.com");
            newClient.setPhoneNumber("123-456-7890");
            newClient.setAddress("123 Main St, Anytown, USA");

            Integer clientId = clientDao.createClient(newClient);
            System.out.println("Created client with ID: " + clientId);

            // Read the client
            Client client = clientDao.readClient(clientId);
            System.out.println("Read client: " + client.getCompanyName());

            // Update the client
            client.setCompanyName("Updated Company");
            clientDao.updateClient(client);

            Client updatedClient = clientDao.readClient(clientId);
            System.out.println("Updated client name: " + updatedClient.getCompanyName());

            // List all clients
            System.out.println("Listing all clients:");
            for (Client c : clientDao.listClients()) {
                System.out.println(c.getCompanyName());
            }

            // Delete the client
            clientDao.deleteClient(clientId);
            System.out.println("Deleted client with ID: " + clientId);
    }
}
