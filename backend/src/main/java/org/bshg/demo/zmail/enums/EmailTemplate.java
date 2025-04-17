package org.bshg.demo.zmail.enums;

public enum EmailTemplate {
    ACTIVATE_ACCOUNT("activate_account"),
    FORGET_PASSWORD("forget_password"),
    CONFIRM_EMAIL("confirm_email"),
    ;

    EmailTemplate(String template) {
        this.template = template;
    }

    public final String template;
}
