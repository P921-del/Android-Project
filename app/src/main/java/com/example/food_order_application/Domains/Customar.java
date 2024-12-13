package com.example.food_order_application.Domains;

public class Customar {
    public int CustomarID ;
    public String Name;
    public String Address;
    public int ContactNumber;
    public String Email;
    public String Password;

    public Customar(String name, String address, int contactNumber, String email, String password) {
        Name = name;
        Address = address;
        ContactNumber = contactNumber;
        Email = email;
        Password = password;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Customar(int customarID, String name, String address, int contactNumber, String email, String password) {
        CustomarID = customarID;
        Name = name;
        Address = address;
        ContactNumber = contactNumber;
        Email = email;
        Password = password;
    }

    public Customar(String name, int customarID, String address, int contactNumber, String email) {//constructor for updateTable
        Name = name;
        CustomarID = customarID;
        Address = address;
        ContactNumber = contactNumber;
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(int contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getEmail() {
        return Email;
    }

    public int getCustomarID() {
        return CustomarID;
    }

    public void setCustomarID(int customarID) {
        CustomarID = customarID;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Customar(String name, String address, int contactNumber, String email) {//constructor for insertTable
        Name = name;
        Address = address;
        ContactNumber = contactNumber;
        Email = email;
    }
}
