package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private final Connection conn;
    private PreparedStatement createSt;
    private PreparedStatement readSt;

    public ClientService(Connection conn) {
        this.conn = conn;
    }

    long create(String name)  {

        if (name.length() < 2 || name.length() > 1000) {
            throw new IllegalArgumentException("The name is not valid");
        }
        long result = 0;
        try {
            createSt = conn.prepareStatement("INSERT INTO client (name) VALUES (?)");
            createSt.setString(1, name);
            createSt.executeUpdate();

            readSt = conn.prepareStatement("SELECT id FROM client WHERE name = ?");
            readSt.setString(1, name);
            ResultSet resultSet = readSt.executeQuery();
            List<Integer> arrayID = new ArrayList<>();
            while (resultSet.next()){
                arrayID.add(resultSet.getInt("id"));
            }
            result = arrayID.get(arrayID.size()-1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    String getById(long id)  {

        if(id < 1 || id > getMaxClientID() ){
            throw new IllegalArgumentException("The ID is not valid");
        }
        String result = null;

        try {
            readSt = conn.prepareStatement("SELECT name FROM client WHERE id = ?");
            readSt.setLong(1, id);
            ResultSet resultSet = readSt.executeQuery();
            if(resultSet.next()){
                result = resultSet.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    void setName(long id, String name) {

        if (id < 1 || id > getMaxClientID() || name.length() < 2 || name.length() > 1000 ){
            throw new IllegalArgumentException("The ID or Name is not valid");
        }
        try {
            createSt = conn.prepareStatement("UPDATE client SET name = (?) WHERE id = (?)");
            createSt.setString(1, name);
            createSt.setLong(2, id);
            createSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    void deleteById(long id) {

        if(id < 1 || id > getMaxClientID() ){
            throw new IllegalArgumentException("The ID is not valid");
        }
        try {
            createSt = conn.prepareStatement("DELETE FROM client WHERE id = (?)");
            createSt.setLong(1, id);
            createSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    List<Client> listAll() {
        List<Client> clients = new ArrayList<>();
        try {
            readSt = conn.prepareStatement("SELECT * FROM client");
            ResultSet resultSet = readSt.executeQuery();
            while (resultSet.next()){
                clients.add(new Client(resultSet.getInt("id"),
                        resultSet.getString("name")));
            }
            if (clients.isEmpty()) {
                System.out.println("Table is empty");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
    int getMaxClientID (){
        int maxID = 0;

        try {
            readSt = conn.prepareStatement("SELECT MAX(id) FROM client");
            ResultSet resultSet = readSt.executeQuery();

            if(resultSet.next()){
                maxID = resultSet.getInt("MAX(id)");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxID;
    }
}
