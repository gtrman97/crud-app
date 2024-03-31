package com.aquent.crudapp.person;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aquent.crudapp.client.Client;
import com.aquent.crudapp.client.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/person")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    public static final String COMMAND_DELETE = "Delete";

    private final PersonService personService;
    private final ClientService clientService;

    public PersonController(PersonService personService, ClientService clientService) {
        this.personService = personService;
        this.clientService = clientService;
    }

    @GetMapping("/list")
    public ModelAndView list() {
    ModelAndView mav = new ModelAndView("person/list");
    List<Person> persons = personService.listPeopleWithClients();
    mav.addObject("persons", persons);
    return mav;
}

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("person/create");
        mav.addObject("person", new Person());
        mav.addObject("errors", new ArrayList<String>());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView create(Person person) {
        List<String> errors = personService.validatePerson(person);
        if (errors.isEmpty()) {
            personService.createPerson(person);
            return new ModelAndView("redirect:/person/list");
        } else {
            ModelAndView mav = new ModelAndView("person/create");
            mav.addObject("person", person);
            mav.addObject("errors", errors);
            return mav;
        }
    }

    @GetMapping("/edit/{personId}")
    public ModelAndView edit(@PathVariable Integer personId) {
        ModelAndView mav = new ModelAndView("person/edit");
        Person person = personService.readPerson(personId);
        List<Client> clients = clientService.listClients();
        mav.addObject("person", person);
        mav.addObject("clients", clients);
        mav.addObject("errors", new ArrayList<String>());
        return mav;
    }

    @PostMapping("/edit")
    public ModelAndView edit(Person person) {
        List<String> errors = personService.validatePerson(person);
        if (errors.isEmpty()) {
            personService.updatePerson(person);
            return new ModelAndView("redirect:/person/list");
        } else {
            ModelAndView mav = new ModelAndView("person/edit");
            mav.addObject("person", person);
            mav.addObject("errors", errors);
            return mav;
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String command, @RequestParam Integer personId) {
        if (COMMAND_DELETE.equals(command)) {
            personService.deletePerson(personId);
        }
        return "redirect:/person/list";
    }
}
