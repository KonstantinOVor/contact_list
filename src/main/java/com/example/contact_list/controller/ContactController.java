package com.example.contact_list.controller;

import com.example.contact_list.model.Contact;
import com.example.contact_list.service.ContactService;
import com.example.contact_list.service.Impl.ContactServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactController {
    private final List<Contact> contacts = new ArrayList<>();
    private final ContactService contactService;
    @GetMapping("/")
    public String index(Model model) {
        log.debug("Index");

        model.addAttribute("contacts", contactService.findAll());
        return "index";
    }
    @GetMapping("/contacts/add")
    public String showAddContact(Model model) {
        log.debug("Add Contact");
        model.addAttribute("contact", new Contact());
        return "add";
    }
    @PostMapping("/contacts/add")
    public String addContact(@ModelAttribute Contact contact) {
        log.debug(contact.toString());

        contactService.save(contact);
        return "redirect:/";
    }
    @GetMapping("/contacts/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.debug("Edit Contact");

        Contact contact = contactService.findById(id);
        if (contact != null) {
            model.addAttribute("contact", contact);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/contacts/edit")
    public String editContact(@ModelAttribute Contact contact) {
        log.debug(contact.toString());

        contactService.update(contact);
        return "redirect:/";
    }
    @GetMapping("/contacts/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        log.debug("Delete Contact");

        contactService.delete(id);
        return "redirect:/";
    }
}
