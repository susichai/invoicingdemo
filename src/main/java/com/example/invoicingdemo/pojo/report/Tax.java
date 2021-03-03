package com.example.invoicingdemo.pojo.report;

import org.springframework.lang.Nullable;

public class Tax {
    public String name;
    public String percent;
    @Nullable
    public Amount amount;
}
