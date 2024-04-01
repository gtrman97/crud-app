package com.aquent.crudapp.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.aquent.crudapp.person.Person;
import com.aquent.crudapp.person.PersonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/clients")
public class ClientController {

    public static final String COMMAND_DELETE = "Delete";
    private final ClientService clientService;
    private final PersonService personService;

    @Autowired
    public ClientController(ClientService clientService, PersonService personService) {
        this.clientService = clientService;
        this.personService = personService;
    }

    @GetMapping
    public String listClients(Model model) {
    List<Client> clients = clientService.listClients();
    for (Client client : clients) {
        List<Person> contacts = personService.getPersonsByIds(client.getContacts());
        System.out.println("Contacts for client " + client.getClientId() + ": " + contacts);
        client.setContactObjects(contacts);
    }
    model.addAttribute("clients", clients);
    return "clients/list";
}



    @GetMapping("/new")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("clients/create");
        mav.addObject("client", new Client());
        mav.addObject("errors", new ArrayList<String>());
        mav.addObject("persons", personService.listPeople()); 
        return mav;
    }

    @PostMapping
    public ModelAndView create(Client client, @RequestParam List<Integer> contactIds) {
        client.setContacts(contactIds);

    // Validate and create the client
    List<String> errors = clientService.validateClient(client);
    if (errors.isEmpty()) {
        clientService.createClient(client, contactIds);
        return new ModelAndView("redirect:/clients");
    } else {
        ModelAndView mav = new ModelAndView("clients/create");
        mav.addObject("client", client);
        mav.addObject("errors", errors);
        return mav;
    }
}

@GetMapping("/edit/{clientId}")
public ModelAndView edit(@PathVariable Integer clientId) {
    ModelAndView mav = new ModelAndView("clients/edit");
    Client client = clientService.getClient(clientId);
    List<Person> allPersons = personService.listPeople(); // Get all persons
    List<Integer> associatedContactIds = clientService.getContactsByClientId(clientId); // Get associated contacts
    mav.addObject("client", client);
    mav.addObject("allPersons", allPersons);
    mav.addObject("associatedContactIds", associatedContactIds);
    return mav;
}

    @PostMapping("/edit")
    public ModelAndView edit(Client client) {
        List<String> errors = clientService.validateClient(client);
        if (errors.isEmpty()) {
            clientService.updateClient(client);
            return new ModelAndView("redirect:/clients");
        } else {
            ModelAndView mav = new ModelAndView("clients/edit");
            mav.addObject("client", client);
            mav.addObject("errors", errors);
            return mav;
        }
    }

    @GetMapping("/delete/{clientId}")
    public ModelAndView delete(@PathVariable Integer clientId) {
        ModelAndView mav = new ModelAndView("clients/delete");
        mav.addObject("client", clientService.getClient(clientId));
        return mav;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String command, @RequestParam Integer clientId) {
        if (COMMAND_DELETE.equals(command)) {
            clientService.deleteClient(clientId);
        }
        return "redirect:/clients";
}

@GetMapping("/{clientId}")
public String viewClient(@PathVariable Integer clientId, Model model) {
    Client client = clientService.getClient(clientId);
    List<Integer> contacts = personService.getContactsByClientId(clientId); 
    client.setContacts(contacts);
    model.addAttribute("client", client);
    return "clients/view"; 
}

}
