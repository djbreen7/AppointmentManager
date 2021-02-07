package dao;

import model.Contact;

import java.util.List;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public interface ContactDao {
    List<Contact> getAllContacts();
}
