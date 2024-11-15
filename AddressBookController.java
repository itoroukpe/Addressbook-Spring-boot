package com.rondustech.addressbook.controller;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")  // Allow requests from the frontend
public class AddressBookController {

    private final Map<String, String> addressBook = new HashMap<>();

    // Add a new contact
    @PostMapping("/add")
    public String addContact(@RequestParam String name, @RequestParam String phone) {
        addressBook.put(name, phone);
        return "Contact " + name + " added successfully!";
    }

    // Retrieve a contact
    @GetMapping("/get")
    public String getContact(@RequestParam String name) {
        if (addressBook.containsKey(name)) {
            return name + "'s phone number is " + addressBook.get(name);
        } else {
            return "No contact found for " + name;
        }
    }

    // Display all contacts
    @GetMapping("/all")
    public Map<String, String> getAllContacts() {
        return addressBook;
    }
}
