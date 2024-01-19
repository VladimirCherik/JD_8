package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        new DatabaseInitService().initAndPopulateDB();

        ClientService clientService = new ClientService(Database.getInstance().getConnection());

        //create a new client
        System.out.println("clientService.create(\"Petro\") = " + clientService.create("Petro"));

        //get by ID
        System.out.println("clientService.getById(1) = " + clientService.getById(1));

        //set name by ID
        clientService.setName(2, "Lincoln");

        //delete client
        clientService.deleteById(5);

        // get all clients
        List<Client> clients = clientService.listAll();
        for (Client client: clients) {
            System.out.println("client = " + client);
        }
    }
}