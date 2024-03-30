package com.aquent.crudapp.client;

import com.aquent.crudapp.client.Client;
import com.aquent.crudapp.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    public static final String COMMAND_DELETE = "Delete";

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
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("clients/create");
        mav.addObject("client", new Client());
        mav.addObject("errors", new ArrayList<String>());
        return mav;
    }

    @PostMapping
    public ModelAndView create(Client client) {
        List<String> errors = clientService.validateClient(client);
        if (errors.isEmpty()) {
            clientService.createClient(client);
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
        mav.addObject("client", clientService.getClient(clientId));
        mav.addObject("errors", new ArrayList<String>());
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
}
