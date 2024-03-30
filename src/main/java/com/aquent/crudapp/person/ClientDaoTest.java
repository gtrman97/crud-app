package com.aquent.crudapp.person;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.aquent.crudapp.Application;

public class ClientDaoTest {

    public static void main(String[] args) {
        // Using try-with-resources to ensure that the ApplicationContext is automatically closed and resources are released
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class)) { 
            ClientDao clientDao = context.getBean(ClientDao.class);

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
}
