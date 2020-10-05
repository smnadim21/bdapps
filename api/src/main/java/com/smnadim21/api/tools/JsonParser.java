package com.smnadim21.api.tools;

public interface JsonParser {

    public interface VerifyOtp {
        String valid_subscriber = "valid_subscriber";
        String message = "message";
        String existing_subscriber = "existing_subscriber";
    }

    public interface RequestCAAS {
        String message = "message";
        String payment_status = "payment_status";
        String new_payment = "new_payment";
    }
}
