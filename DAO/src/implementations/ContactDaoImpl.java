package implementations;

import dao.ContactDao;
import data.DatabaseConnection;
import model.Contact;
import utilities.ResultSetBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for working with Contacts in the database.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class ContactDaoImpl implements ContactDao {
    private final ResultSetBuilder resultSetBuilder;

    public ContactDaoImpl() {
        resultSetBuilder = new ResultSetBuilder();
    }

    /**
     * Get all contacts from the database.
     *
     * @return All contacts in the database.
     */
    @Override
    public List<Contact> getAllContacts() {
        String query = "SELECT * from contacts";
        List<Contact> contacts = new ArrayList<>();
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                var contact = resultSetBuilder.buildContactResult(resultSet);
                contacts.add(contact);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
            return contacts;
        }
    }
}
