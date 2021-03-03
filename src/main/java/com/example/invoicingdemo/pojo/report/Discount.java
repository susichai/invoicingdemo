package com.example.invoicingdemo.pojo.report;

import org.springframework.lang.Nullable;

public class Discount {
    public String percent;
    public Amount amount;
    public InvoiceDiscount invoice_discount;
    @Nullable
    public ItemDiscount item_discount;
}
