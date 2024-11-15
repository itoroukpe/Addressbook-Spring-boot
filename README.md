# Addressbook-Spring-boot
To build the above web application using **Spring Boot**, we need to structure it into a backend API service and a frontend interface. Here's how it can be achieved:

---

### **Steps to Create the Application**

1. **Set Up Spring Boot Project**
   - Use Spring Initializr to create a Spring Boot project with the following dependencies:
     - Spring Web
     - Spring Boot DevTools
     - Spring Data JPA (if persisting data in a database is required)
     - H2 Database (optional, for in-memory storage)

2. **Define the Backend API**

The backend will handle the business logic (adding, retrieving, and displaying contacts) using RESTful endpoints.

---

### **Spring Boot Code**

#### **1. `pom.xml`**
Add required dependencies:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

---

#### **2. `AddressBookApplication.java`**

```java
package com.rondustech.addressbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AddressBookApplication {
    public static void main(String[] args) {
        SpringApplication.run(AddressBookApplication.class, args);
    }
}
```

---

#### **3. `AddressBookController.java`**

```java
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
```

---

#### **4. Frontend Interface**

You can use a lightweight HTML file with JavaScript to interact with the backend API.

**`index.html`**
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Address Book</title>
    <script>
        const apiBaseUrl = "http://localhost:8080/api/contacts";

        function addContact() {
            const name = document.getElementById("contactName").value.trim();
            const phone = document.getElementById("contactPhone").value.trim();

            if (name && phone) {
                fetch(`${apiBaseUrl}/add?name=${name}&phone=${phone}`, { method: "POST" })
                    .then(response => response.text())
                    .then(data => {
                        document.getElementById("output").innerText = data;
                        document.getElementById("contactName").value = "";
                        document.getElementById("contactPhone").value = "";
                    });
            } else {
                alert("Please enter both name and phone number!");
            }
        }

        function getContact() {
            const name = document.getElementById("contactName").value.trim();

            if (name) {
                fetch(`${apiBaseUrl}/get?name=${name}`)
                    .then(response => response.text())
                    .then(data => {
                        document.getElementById("output").innerText = data;
                    });
            } else {
                alert("Please enter a name to retrieve the contact!");
            }
        }

        function displayAllContacts() {
            fetch(`${apiBaseUrl}/all`)
                .then(response => response.json())
                .then(data => {
                    let output = "<h3>All Contacts:</h3><ul>";
                    for (let [name, phone] of Object.entries(data)) {
                        output += `<li><b>${name}</b>: ${phone}</li>`;
                    }
                    output += "</ul>";
                    document.getElementById("output").innerHTML = output;
                });
        }
    </script>
</head>
<body>
    <h1>Address Book</h1>
    <div>
        <label for="contactName">Contact Name:</label>
        <input type="text" id="contactName" placeholder="Enter contact name"><br>
        <label for="contactPhone">Phone Number:</label>
        <input type="text" id="contactPhone" placeholder="Enter phone number"><br>
        <button onclick="addContact()">Add Contact</button>
        <button onclick="getContact()">Retrieve Contact</button>
        <button onclick="displayAllContacts()">Display All Contacts</button>
    </div>
    <div id="output" style="margin-top: 20px;"></div>
</body>
</html>
```

---

### **How It Works**
1. **Backend API**:
   - `/add`: Adds a contact to the address book.
   - `/get`: Retrieves a contact by name.
   - `/all`: Returns all contacts in the address book.

2. **Frontend**:
   - Uses `fetch()` to call the API endpoints and update the webpage dynamically.
   - Displays results in a user-friendly interface.

---

### **Run the Application**
1. Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
2. Open `index.html` in a browser.
3. Perform actions like adding, retrieving, or displaying contacts, which communicate with the backend API.

This approach separates concerns between the backend (Spring Boot) and frontend, making the application scalable and maintainable.
