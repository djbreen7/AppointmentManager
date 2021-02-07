package dao;

import model.Contact;

import java.util.List;

/**
 * DAO for working with Contacts in the database.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public interface ContactDao {

    /**
     * Get all contacts from the database.
     *
     * @return List of Contact
     */
    List<Contact> getAllContacts();
}
