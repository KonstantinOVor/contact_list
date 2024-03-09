package com.example.contact_list.listener;

import com.example.contact_list.model.Contact;
import com.example.contact_list.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataBaseContactCreator {
    private final ContactService contactService;

    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        log.info("Init DataBaseContactCreator");

//        List<Contact> contacts = contactService.findAll();
        List<Contact> contacts = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Contact contact = new Contact();
            contact.setFirstName("firstName" + i);
            contact.setLastName("lastName" + i);
            contact.setEmail("email" + i);
            contact.setPhone("phone" + i);
            contacts.add(contact);
        }
        contactService.batchInsert(contacts);
    }
}
