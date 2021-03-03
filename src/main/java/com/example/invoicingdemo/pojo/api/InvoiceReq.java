package com.example.invoicingdemo.pojo.api;

import com.example.invoicingdemo.pojo.report.*;

import java.util.List;

public class InvoiceReq {
    public Detail detail;
    public Invoicer invoicer;
    public List<PrimaryRecipient> primary_recipients;
    public List<Item> items;
    public Configuration configuration;
    public Amount amount;
}


