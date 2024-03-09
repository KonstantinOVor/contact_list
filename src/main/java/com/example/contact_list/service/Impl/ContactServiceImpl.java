package com.example.contact_list.service.Impl;

import com.example.contact_list.model.Contact;
import com.example.contact_list.repository.ContactRepository;
import com.example.contact_list.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    @Override
    public Contact save(Contact contact) {
        log.debug("Call save method, save contact: {}", contact);

        return contactRepository.save(contact);
    }

    @Override
    public void delete(Long id) {
        log.debug("Call delete method, delete contact: {}", id);

        contactRepository.deleteById(id);
    }

    @Override
    public List<Contact> findAll() {
        log.debug("Call findAll method");

        return contactRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        log.debug("Call findById method, id: {}", id);

        return contactRepository.findById(id).orElse(null);
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Call update method, contact: {}", contact);

        return contactRepository.update(contact);
    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        log.debug("Call batchInsert method, contacts: {}", contacts);

        contactRepository.batchInsert(contacts);
    }
}
