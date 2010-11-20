package com.loiane.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.loiane.dao.ContactDAO;
import com.loiane.model.Contact;
import com.loiane.util.Util;

/**
 * Contact Business Delegate
 * 
 * @author Loiane Groner
 * http://loianegroner.com (English)
 * http://loiane.com (Portuguese)
 */
public class ContactService {
	
	private ContactDAO contactDAO;
	private Util util;

	/**
	 * Get all contacts
	 * @return
	 */
	public List<Contact> getContactList(){

		return contactDAO.getContacts();
	}
	
	/**
	 * Create new Contact/Contacts
	 * @param data - json data from request
	 * @return created contacts
	 * @throws ParseException 
	 */
	public List<Contact> create(Object data) throws ParseException{
		
        List<Contact> newContacts = new ArrayList<Contact>();
		
		List<Contact> list = util.getContactsFromRequest(data);
		
		for (Contact contact : list){
			newContacts.add(contactDAO.addContact(contact));
		}
		
		return newContacts;
	}
	
	
	/**
	 * Update contact/contacts
	 * @param data - json data from request
	 * @return updated contacts
	 * @throws ParseException 
	 */
	public List<Contact> update(Object data) throws ParseException{
		
		List<Contact> returnContacts = new ArrayList<Contact>();
		
		List<Contact> updatedContacts = util.getContactsFromRequest(data);
		
		for (Contact contact : updatedContacts){
			returnContacts.add(contactDAO.updateContact(contact));
		}
		
		return returnContacts;
	}
	
	/**
	 * Delete contact/contacts
	 * @param data - json data from request
	 */
	public void delete(Object data){
		
		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){
			
			List<Integer> deleteContacts = util.getListIdFromJSON(data);
			
			for (Integer id : deleteContacts){
				contactDAO.deleteContact(id);
			}
			
		} else { //it is only one object - cast to object/bean
			
			Integer id = Integer.parseInt(data.toString());
			
			contactDAO.deleteContact(id);
		}
	}
	

	/**
	 * Spring use - DI
	 * @param contactDAO
	 */
	public void setContactDAO(ContactDAO contactDAO) {
		this.contactDAO = contactDAO;
	}

	/**
	 * Spring use - DI
	 * @param util
	 */
	public void setUtil(Util util) {
		this.util = util;
	}
	
}
