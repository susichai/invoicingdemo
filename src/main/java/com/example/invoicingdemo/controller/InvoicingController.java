package com.example.invoicingdemo.controller;

import com.example.invoicingdemo.pojo.api.*;
import com.example.invoicingdemo.pojo.report.InvoiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class InvoicingController {
    //private static final Logger log = (Logger) LoggerFactory.getLogger(InvoicingController.class);


    private RestTemplate restTemplate;
    static String authorizationToken;

    //private String authorizationToken;

    @Autowired
    public InvoicingController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Login for token
    @PostMapping("login")
    public ResponseEntity<LoginInfo> login() {
        final String url = "https://api-m.sandbox.paypal.com/v1/oauth2/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("AdEYm6qsmkM3Vo39F1blLVtocF3BI0fe7upGpTDAJ9IDo9wWepd80gaDHqpEY-R54nD4Ny5PUeapEyLz",
                "EJ2LTZ9V99Vmb8PVJUkDPOEuPPUHDvCxkxN-fbBsKuu6gnHJbv6s-sj5iJlc9F4i5L1UfPhJ0cGesz30");

        MultiValueMap<String, String> bodyParamMap = new LinkedMultiValueMap<>();
        bodyParamMap.add("grant_type", "client_credentials");

        HttpEntity entity = new HttpEntity<>(bodyParamMap, headers);
        ResponseEntity<LoginInfo> response = restTemplate.exchange(url, HttpMethod.POST, entity, LoginInfo.class);

        authorizationToken = response.getBody().access_token;
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    //List invoices
    @GetMapping("/invoices")
    public ResponseEntity<Items> listInvoices(@RequestParam Optional<Integer> page,
                                              @RequestParam Optional<Integer> page_size,
                                              @RequestParam Optional<Boolean> total_required,
                                              @RequestParam Optional<String> fields) {

        final String url = "https://api-m.sandbox.paypal.com/v2/invoicing/invoices";
        HttpHeaders headers = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        //Check all the query parameters
        if(page.isPresent())
            builder.queryParam("page", page.get());
        if(page_size.isPresent())
            builder.queryParam("page_size", page_size.get());
        if(total_required.isPresent())
            builder.queryParam("total_required", total_required.get());
        if(fields.isPresent())
            builder.queryParam("fields", fields.get());

        headers.set("Authorization", "BEARER " + authorizationToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Items> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Items.class);

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    //Generate invoice number
    @PostMapping("generate-next-invoice-number")
    public ResponseEntity<GenerateNumber> getNumber() {
        final String url = "https://api-m.sandbox.paypal.com/v2/invoicing/generate-next-invoice-number";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "BEARER " + authorizationToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<GenerateNumber> response = restTemplate.exchange(url, HttpMethod.POST, entity, GenerateNumber.class);

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    //Create invoice draft
    @PostMapping("/invoice")
    public ResponseEntity<Link> createInvoice(@RequestBody @Validated InvoiceReq request) {
        HttpHeaders headers = new HttpHeaders();


        headers.set("Authorization", "BEARER " + authorizationToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InvoiceReq> entity = new HttpEntity(request, headers);

        final String url = "https://api-m.sandbox.paypal.com/v2/invoicing/invoices";
        ResponseEntity<Link> responseLink = restTemplate.exchange(url, HttpMethod.POST, entity, Link.class);

//        String hateoasurl = responseLink.getBody().getHref();
//        //String httpMethod = responseLink.getBody().getMethod();
//
//        //ResponseEntity<InvoiceDTO> response = restTemplate.exchange(hateoasurl, HttpMethod.valueOf(httpMethod), InvoiceDTO.class);
//
//        InvoiceDTO obj = restTemplate.getForObject(hateoasurl, InvoiceDTO.class);
//        return new ResponseEntity<>(obj, HttpStatus.CREATED);
        return new ResponseEntity<>(responseLink.getBody(), responseLink.getStatusCode());
    }

    //Send invoice
    @PostMapping("/invoice/{id}/send")
    public ResponseEntity sendInvoice(@PathVariable String id, @RequestBody @Nullable SendInfo info) {
        final String url = "https://api-m.sandbox.paypal.com/v2/invoicing/invoices/" + id + "/send";

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "BEARER " + authorizationToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<SendInfo> entity = new HttpEntity(info, headers);
        try {
            ResponseEntity<Link> response = restTemplate.exchange(url, HttpMethod.POST, entity, Link.class);
            return new ResponseEntity(response.getBody(), response.getStatusCode());
        } catch(HttpClientErrorException e) {
            String response = e.getResponseBodyAsString();
            return new ResponseEntity<>(response, e.getStatusCode());
        }
    }

    //Show invoice details
    @GetMapping("/invoice/{id}")
    public ResponseEntity showInvoice(@PathVariable String id) {
        final String url = "https://api-m.sandbox.paypal.com/v2/invoicing/invoices/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "BEARER " + authorizationToken);
        headers.set("Content-Type", "application/json");

        HttpEntity entity = new HttpEntity(headers);

        try {
            ResponseEntity<InvoiceDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, InvoiceDTO.class);
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        } catch(HttpClientErrorException e) {
            String response = e.getResponseBodyAsString();
            return new ResponseEntity<>(response, e.getStatusCode());
        }
    }

    //Fully update invoice
    @PutMapping("invoice/{id}")
    public ResponseEntity updateInvoice(@PathVariable String id,
                                        @RequestBody @Nullable InvoiceDTO newObj,
                                        @RequestParam Optional<Boolean> send_to_recipient,
                                        @RequestParam Optional<Boolean> send_to_invoicer) {
        final String url = "https://api-m.sandbox.paypal.com/v2/invoicing/invoices/" + id;

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        //Check all the query parameters
        if(send_to_recipient.isPresent())
            builder.queryParam("page", send_to_recipient.get());
        if(send_to_invoicer.isPresent())
            builder.queryParam("page_size", send_to_invoicer.get());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "BEARER " + authorizationToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<InvoiceDTO> entity = new HttpEntity(newObj, headers);

        try {
            ResponseEntity<Link> response = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, Link.class);
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        } catch (HttpClientErrorException e) {
            String response = e.getResponseBodyAsString();
            return new ResponseEntity<>(response, e.getStatusCode());
        }
    }

    //Delete Invoice
    @DeleteMapping("/invoice/{id}")
    public ResponseEntity deleteInvoice(@PathVariable String id) {
        final String url = "https://api-m.sandbox.paypal.com/v2/invoicing/invoices/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "BEARER " + authorizationToken);
        headers.set("Content-Type", "application/json");

        HttpEntity entity = new HttpEntity(headers);
        try {
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
            return new ResponseEntity(response.getStatusCode());
        } catch (HttpClientErrorException e) {
            String response = e.getResponseBodyAsString();
            return new ResponseEntity(response, e.getStatusCode());
        }
    }

    //Cancel sent invoice
    @PostMapping("/invoice/{id}/cancel")
    public ResponseEntity cancelSentInvoice(@PathVariable String id, @RequestBody CancelInvoice cancel) {
        final String url = "https://api-m.sandbox.paypal.com/v2/invoicing/invoices/" + id + "/cancel";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "BEARER " + authorizationToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<CancelInvoice> entity = new HttpEntity(cancel, headers);

        try {
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
            return new ResponseEntity(response.getStatusCode());
        } catch (HttpClientErrorException e) {
            String response = e.getResponseBodyAsString();
            return new ResponseEntity(response, e.getStatusCode());
        }
    }

    //Generate QR code

}
