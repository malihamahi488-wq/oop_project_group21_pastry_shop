package com.example.BranchManager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.*;

/**
 * Model for ManageEmployeeAccounts (UML)
 *
 * Attributes:
 * - managerID: int
 * - employeeID: int (used per-operation)
 *
 * Methods:
 * + viewEmployees(): List<Employee>
 * + addOrEditEmployee(data: Map<String,String>): boolean
 * + validateEmployee(data): boolean
 *
 * This model uses an in-memory store (employeeStore). Replace with DB operations for production.
 */
public class ManageEmployeeAccounts {

    private final int managerID;
    private final Map<String, Employee> employeeStore = new LinkedHashMap<>();
    private String lastValidationMessage = "";

    public ManageEmployeeAccounts(int managerID) {
        this.managerID = managerID;
        seedMockData();
    }

    /**
     * Return current list of employees
     */
    public List<Employee> viewEmployees() {
        return new ArrayList<>(employeeStore.values());
    }

    /**
     * Add a new employee or edit existing one. Expects data map keys:
     * employeeId (optional/blank for new), name, role, contactNumber, email, status
     */
    public boolean addOrEditEmployee(Map<String,String> data) {
        String id = Optional.ofNullable(data.get("employeeId")).orElse("").trim();
        String name = Optional.ofNullable(data.get("name")).orElse("").trim();
        String role = Optional.ofNullable(data.get("role")).orElse("").trim();
        String contact = Optional.ofNullable(data.get("contactNumber")).orElse("").trim();
        String email = Optional.ofNullable(data.get("email")).orElse("").trim();
        String status = Optional.ofNullable(data.get("status")).orElse("Active");

        if (!validateEmployee(data)) return false;

        if (id.isEmpty()) {
            // add new -> generate id
            id = generateEmployeeId();
            Employee e = new Employee(id, name, role, contact, email, status);
            employeeStore.put(id, e);
        } else {
            // edit existing
            Employee e = employeeStore.get(id);
            if (e == null) {
                // if id not found, create new
                e = new Employee(id, name, role, contact, email, status);
                employeeStore.put(id, e);
            } else {
                e.setName(name);
                e.setRole(role);
                e.setContactNumber(contact);
                e.setEmail(email);
                e.setStatus(status);
            }
        }
        return true;
    }

    /**
     * Validate employee data (returns true if valid), sets lastValidationMessage on failure.
     */
    public boolean validateEmployee(Map<String,String> data) {
        String name = Optional.ofNullable(data.get("name")).orElse("").trim();
        String role = Optional.ofNullable(data.get("role")).orElse("").trim();
        String contact = Optional.ofNullable(data.get("contactNumber")).orElse("").trim();
        String email = Optional.ofNullable(data.get("email")).orElse("").trim();

        if (name.isEmpty()) {
            lastValidationMessage = "Name is required.";
            return false;
        }
        if (role.isEmpty()) {
            lastValidationMessage = "Role is required.";
            return false;
        }
        if (contact.isEmpty()) {
            lastValidationMessage = "Contact number is required.";
            return false;
        }
        if (!email.contains("@") || email.length() < 5) {
            lastValidationMessage = "Invalid email address.";
            return false;
        }
        lastValidationMessage = "OK";
        return true;
    }

    public String getLastValidationMessage() {
        return lastValidationMessage;
    }

    /**
     * Simple search by id or name (case-insensitive contains)
     */
    public List<Employee> search(String query) {
        query = query == null ? "" : query.toLowerCase(Locale.ROOT).trim();
        List<Employee> out = new ArrayList<>();
        for (Employee e : employeeStore.values()) {
            if (e.getEmployeeId().toLowerCase().contains(query) || e.getName().toLowerCase().contains(query)) {
                out.add(e);
            }
        }
        return out;
    }

    private String generateEmployeeId() {
        int base = 200;
        while (employeeStore.containsKey("E" + base)) base++;
        return "E" + base;
    }

    private void seedMockData() {
        Employee a = new Employee("E200", "Arif Ahmed", "Baker", "01710000001", "arif@example.com", "Active");
        Employee b = new Employee("E201", "Bipasha Roy", "Cashier", "01710000002", "bipasha@example.com", "Active");
        Employee c = new Employee("E202", "Camille Khan", "Supervisor", "01710000003", "camille@example.com", "On Leave");
        employeeStore.put(a.getEmployeeId(), a);
        employeeStore.put(b.getEmployeeId(), b);
        employeeStore.put(c.getEmployeeId(), c);
    }

    // --- Inner Employee class with JavaFX properties for TableView binding ---
    public static class Employee {
        private final StringProperty employeeId;
        private final StringProperty name;
        private final StringProperty role;
        private final StringProperty contactNumber;
        private final StringProperty email;
        private final StringProperty status;

        public Employee(String employeeId, String name, String role, String contactNumber, String email, String status) {
            this.employeeId = new SimpleStringProperty(employeeId);
            this.name = new SimpleStringProperty(name);
            this.role = new SimpleStringProperty(role);
            this.contactNumber = new SimpleStringProperty(contactNumber);
            this.email = new SimpleStringProperty(email);
            this.status = new SimpleStringProperty(status);
        }

        public String getEmployeeId() { return employeeId.get(); }
        public StringProperty employeeIdProperty() { return employeeId; }

        public String getName() { return name.get(); }
        public void setName(String n) { this.name.set(n); }
        public StringProperty nameProperty() { return name; }

        public String getRole() { return role.get(); }
        public void setRole(String r) { this.role.set(r); }
        public StringProperty roleProperty() { return role; }

        public String getContactNumber() { return contactNumber.get(); }
        public void setContactNumber(String c) { this.contactNumber.set(c); }
        public StringProperty contactProperty() { return contactNumber; }

        public String getEmail() { return email.get(); }
        public void setEmail(String e) { this.email.set(e); }
        public StringProperty emailProperty() { return email; }

        public String getStatus() { return status.get(); }
        public void setStatus(String s) { this.status.set(s); }
        public StringProperty statusProperty() { return status; }
    }
}
