package com.example.invoicingdemo.pojo.report;

import com.example.invoicingdemo.pojo.api.Link;
import org.springframework.lang.Nullable;

import java.util.List;

public class InvoiceDTO {
    private String id;
    private String status;
    private Detail detail;
    private Invoicer invoicer;
    private List<PrimaryRecipient> primary_recipients;
    private List<Item> items;
    private Configuration configuration;
    private Amount amount;
    @Nullable
    private List<Link> links;
    @Nullable
    private DueAmount due_amount;

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public Invoicer getInvoicer() {
        return invoicer;
    }

    public void setInvoicer(Invoicer invoicer) {
        this.invoicer = invoicer;
    }

    public List<PrimaryRecipient> getPrimary_recipients() {
        return primary_recipients;
    }

    public void setPrimary_recipients(List<PrimaryRecipient> primary_recipients) {
        this.primary_recipients = primary_recipients;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}
