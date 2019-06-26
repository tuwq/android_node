package com.tuwq.findcontact;

public class Contact {
    public String name;
    public String address;
    public String email;
    public String phone;
    @Override
    public String toString() {
        return "Contact [name=" + name + ", address=" + address + ", email="
                + email + ", phone=" + phone + "]";
    }
}
