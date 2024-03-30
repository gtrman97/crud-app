package com.aquent.crudapp;

import com.aquent.crudapp.client.Client;
import com.aquent.crudapp.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/edit/{clientId}")
    public String showEditClientForm(@PathVariable Integer clientId, Model model) {
        Client client = clientService.getClient(clientId);
        model.addAttribute("client", client);
        return "clients/edit";
}

    @PostMapping("/edit")
    public String updateClient(Client client) {
        clientService.updateClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/delete/{clientId}")
    public String deleteClient(@PathVariable Integer clientId) {
        clientService.deleteClient(clientId);
        return "redirect:/clients";
}


}
