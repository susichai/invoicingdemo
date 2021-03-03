package com.example.invoicingdemo.pojo.api;

import org.springframework.lang.Nullable;

public class SendInfo {
    @Nullable
    private String subject;
    @Nullable
    private String note;
    @Nullable
    private boolean send_to_invoicer;
    @Nullable
    private boolean send_to_recipient;
    @Nullable
    private String[] additional_recipients; //list of emails

    //public SendInfo(){}

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isSend_to_invoicer() {
        return send_to_invoicer;
    }

    public void setSend_to_invoicer(boolean send_to_invoicer) {
        this.send_to_invoicer = send_to_invoicer;
    }

    public boolean isSend_to_recipient() {
        return send_to_recipient;
    }

    public void setSend_to_recipient(boolean send_to_recipient) {
        this.send_to_recipient = send_to_recipient;
    }

    public String[] getAdditional_recipients() {
        return additional_recipients;
    }

    public void setAdditional_recipients(String[] additional_recipients) {
        this.additional_recipients = additional_recipients;
    }

}
