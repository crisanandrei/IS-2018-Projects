package com.is.AutoShop.services;

import com.is.AutoShop.model.Client;

import java.util.List;

public interface ClientServiceInterface {
    void setLoggedClient(Client client);
    Client getLoggedClient();
    Client findClient(String userName);
    void addNewClient(Client client);
    void logout();
    boolean isAnyOneConnected();
    List<Client> getAllClients();
    void deleteClient(String clientId);
    Client findClientById(String clientId);
}
