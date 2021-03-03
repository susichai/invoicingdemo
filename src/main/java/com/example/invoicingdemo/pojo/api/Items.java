package com.example.invoicingdemo.pojo.api;

import com.example.invoicingdemo.pojo.report.InvoiceDTO;
import com.example.invoicingdemo.pojo.report.Item;

import java.util.List;

public class Items {
    private List<InvoiceDTO> items;
    private List<Link> links;

    public List<InvoiceDTO> getItems() {
        return items;
    }

    public void setItems(List<InvoiceDTO> items) {
        this.items = items;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
