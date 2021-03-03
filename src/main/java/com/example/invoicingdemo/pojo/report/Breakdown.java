package com.example.invoicingdemo.pojo.report;

import org.springframework.lang.Nullable;

public class Breakdown {
    public Custom custom;
    public Shipping shipping;
    public Discount discount;
    @Nullable
    public TaxTotal tax_total;
    @Nullable
    public ItemTotal item_total;
}
