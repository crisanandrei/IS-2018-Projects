package com.is.AutoShop.controllers;

import com.is.AutoShop.model.Client;

import com.is.AutoShop.services.ClientServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    private ClientServiceInterface clientServiceInterface;

    public LoginController(ClientServiceInterface clientServiceI) {
        this.clientServiceInterface = clientServiceI;
    }


    @RequestMapping(value = {"","/", "/login", "login"}, method = RequestMethod.GET)
    public String firstPrint(){
        return "login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String firstPost(@ModelAttribute(name = "loginClient") Client client, Model model){
        String userName = client.getUserName();
        String password = client.getPassword();
        Client loggedClient = clientServiceInterface.findClient(userName);

        if(loggedClient != null && loggedClient.getPassword().equals(password)){

            clientServiceInterface.setLoggedClient(loggedClient);
            System.out.println(loggedClient.isAdmin());
            if (loggedClient.isAdmin()) return "redirect:/adminHome";
            return "redirect:/home";
        }

        model.addAttribute("invalidLogin",true);
        return "login";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String regClient(@ModelAttribute(name = "regClient") Client client, Model model){
        String userName = client.getUserName();
        String password = client.getPassword();
        String email = client.getEmail();
        String firstName = client.getFirstName();
        String lastName= client.getLastName();

        Client registeredClient = clientServiceInterface.findClient(userName);

        if(registeredClient != null){
            model.addAttribute("invalidRegister",true);
            return "login";
        }
        else{
            clientServiceInterface.addNewClient(new Client(userName,password,email,firstName,lastName));
            System.out.println("REGISTERED");
            return "login";
        }

    }
}
