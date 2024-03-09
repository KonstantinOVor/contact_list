package com.example.contact_list.repository;

import com.example.contact_list.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {
    Contact save(Contact contact);
    void deleteById(Long id);

    List<Contact> findAll();
    Optional<Contact> findById(Long id);
    Contact update(Contact contact);
    void batchInsert(List<Contact> contacts);
}
