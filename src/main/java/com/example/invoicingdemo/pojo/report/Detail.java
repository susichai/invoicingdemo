package com.example.invoicingdemo.pojo.report;

import org.springframework.lang.Nullable;

public class Detail {
    public String invoice_number;
    public String reference;
    public String currency_code;
    public String note;
    public String term;
    public String memo;
    public PaymentTerm payment_term;
    public String invoice_date;
}
