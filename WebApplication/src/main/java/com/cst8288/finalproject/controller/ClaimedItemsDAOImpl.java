package com.cst8288.finalproject.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cst8288.finalproject.model.Claim;

/**
 * This class inserts items that were claimed by the organization into the database
 */
public class ClaimedItemsDAOImpl implements ClaimedItemsDAO{

    /*
     * establish a connection
     */
    private Connection connection;

    /**
     * instantiate an instance for connection
     */
    public ClaimedItemsDAOImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    /**
     * Method for adding items that were Claimd into the database.
     */
    @Override
    public void addClaim(int item_id, String organizationEmail, int quantity) {
                String query = "INSERT INTO claims (item_id, organization_id, quantity) VALUES (?, ?, ?)";

                OrganizationDAOImpl organizationDAO = new OrganizationDAOImpl();

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, item_id);
                    statement.setInt(2, organizationDAO.getIdByEmail(organizationEmail));
                    statement.setInt(3, quantity);
                    statement.executeUpdate();
                    System.out.println("Food Claim added successfully.");

                } catch (SQLException e) {
                    System.out.println("Error adding food Claim: " + e.getMessage());
                }

    }

    public List<Claim> retrieveAllClaims(String organizationEmail) {
        List<Claim> Claims = new ArrayList<>();
        String query = "SELECT * FROM claims WHERE organization_id = ?";

        ConsumerDAOImpl organizationDAO = new ConsumerDAOImpl();
        int organizationId = organizationDAO.getIdByEmail(organizationEmail);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, organizationId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Claim Claim = new Claim();
                Claim.setClaim_id(resultSet.getInt("Claim_id"));
                Claim.setItem_id(resultSet.getInt("item_id"));
                Claim.setConsumer_id(resultSet.getInt("organization_id"));
                Claim.setQuantity(resultSet.getInt("quantity"));
                Claims.add(Claim);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving Claims: " + e.getMessage());
        }
        return Claims;
    }
}

