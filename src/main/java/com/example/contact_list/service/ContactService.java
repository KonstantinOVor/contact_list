package com.example.contact_list.service;

import com.example.contact_list.model.Contact;
import java.util.List;


public interface ContactService {
    Contact save(Contact contact);
    void delete(Long id);

    List<Contact> findAll();
    Contact findById(Long id);
    Contact update(Contact contact);

    void batchInsert(List<Contact> contacts);
}
