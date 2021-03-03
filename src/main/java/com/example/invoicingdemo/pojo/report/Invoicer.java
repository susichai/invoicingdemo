package com.example.invoicingdemo.pojo.report;

import org.springframework.lang.Nullable;

import java.util.List;

public class Invoicer {
    public Name name;
    public Address address;
    @Nullable
    public String email_address;
    public List<Phone> phones;
    public String website;
    public String tax_id;
    public String logo_url;
    public String additional_notes;
}
