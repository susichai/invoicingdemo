package com.example.invoicingdemo.pojo.report;

import org.springframework.lang.Nullable;

public class Configuration {
    public PartialPayment partial_payment;
    public boolean allow_tip;
    public boolean tax_calculated_after_discount;
    public boolean tax_inclusive;
    @Nullable
    public String template_id;
}
