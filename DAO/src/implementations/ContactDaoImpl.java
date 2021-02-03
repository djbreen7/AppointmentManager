package implementations;

import dao.ContactDao;
import data.DatabaseConnection;
import model.Contact;
import utilities.ResultSetBuilder;

import java.util.ArrayList;
import java.util.List;

public class ContactDaoImpl implements ContactDao {
    private ResultSetBuilder resultSetBuilder;

    public ContactDaoImpl() {
        resultSetBuilder = new ResultSetBuilder();
    }

    @Override
    public List<Contact> getAllContacts() {
        String query = String.format("SELECT * from contacts");
        List<Contact> contacts = new ArrayList();
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                var contact = resultSetBuilder.buildContactResult(resultSet);
                contacts.add(contact);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DatabaseConnection.closeConnection();
            return contacts;
        }
    }
}
