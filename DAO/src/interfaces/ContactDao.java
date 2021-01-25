package interfaces;

import model.Contact;

import java.util.List;

public interface ContactDao {
    public List<Contact> getAllContacts();
    public Contact getContact(int contactId);
    public void updateContact(Contact contact);
    public void deleteContact(int contactId);
}
