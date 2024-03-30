package com.aquent.crudapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Simple controller to redirect to the person listing.  In the future, we could
 * add other landing page behavior here if we were writing a real application.
 */
@Controller
public class ClientController {
    /**
     * Redirect to the client list.
     *
     * @return redirect to the client list
     */
    @GetMapping("/clients")
    public String index() {
        return "redirect:/client/list";
    }
}
