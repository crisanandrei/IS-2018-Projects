package com.is.AutoShop.services;

import com.is.AutoShop.model.Client;
import com.is.AutoShop.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService implements ClientServiceInterface {
    private ClientRepository clientRepository;
    private Client loggedClient;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public void setLoggedClient(Client client) {
        this.loggedClient = client;
    }

    @Override
    public Client getLoggedClient() {
        return this.loggedClient;
    }

    @Override
    public Client findClient(String userName) {
        Client client = clientRepository.findByUserName(userName);
        return client;
    }

    @Override
    public void logout() {
        this.loggedClient=null;
    }

    @Override
    public void addNewClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public boolean isAnyOneConnected() {
        return (loggedClient!=null);
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clientList = new ArrayList<>();
        clientRepository.findAll().iterator().forEachRemaining(clientList::add);
        return clientList;
    }

    @Override
    public void deleteClient(String clientId) {
        clientRepository.deleteByClientId(clientId);
    }

    @Override
    public Client findClientById(String clientId) {
        return clientRepository.findByClientId(clientId);
    }
}
