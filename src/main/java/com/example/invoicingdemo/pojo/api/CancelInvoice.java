package com.example.invoicingdemo.pojo.api;

import java.util.List;

public class CancelInvoice {
    public String subject;
    public String note;
    public boolean send_to_invoicer;
    public boolean send_to_recipient;
    public List<String> additional_recipients;
}
