package com.cst8288.finalproject.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class will be used to interact with the organizations table in the database.
 * It will implement the UserTypeDAO interface to use the getIdFromEmail method.
 */
public class OrganizationDAOImpl implements UserTypeDAO{

    private Connection connection;
    
    /**
     * Constructor for OrganizationDAOImpl class
     * This constructor establishes a connection to the database when called.
     *
     * @return connection to the database from DBConnection class
     */
    public OrganizationDAOImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    /**
     * Fetches the organization_id using the email of the organization.
     * @param email The email of the user who is a organization.
     * @return The organization_id corresponding to the given email.
     * @throws SQLException If there is any issue executing the query.
     */
    @Override
	public int getIdByEmail(String email) {
        // This query assumes that the Users table and organizations table are linked by user_id
        String query = "SELECT o.org_id FROM organizations o JOIN Users u ON o.user_id = u.user_id WHERE u.email = ?";

        int organizationId = 0;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    organizationId = resultSet.getInt("org_id");
                } else {
                    throw new SQLException("organization not found with email: " + email);
                }
            }catch(SQLException e) {
                System.out.println("Error fetching organization ID: " + e.getMessage());
            }
            return organizationId;
        }

}
