package com.aquent.crudapp;

import com.aquent.crudapp.client.Client;
import com.aquent.crudapp.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String listClients(Model model) {
        model.addAttribute("clients", clientService.listClients());
        return "clients/list";
    }

    @GetMapping("/new")
    public String showClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "clients/create";
    }

    @PostMapping
    public String createClient(Client client) {
        clientService.createClient(client);
        return "redirect:/clients";
    }

    // Additional methods for updating and deleting clients can be added here
}
