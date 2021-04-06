package com.example.invoicingdemo.pojo.api;

import org.intellij.lang.annotations.Pattern;

public class QRCode {
    private int width;
    private int height;
    @Pattern("(?i)^(pay|details)$")
    private String action;
}
