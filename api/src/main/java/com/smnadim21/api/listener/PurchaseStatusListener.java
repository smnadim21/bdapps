package com.smnadim21.api.listener;

public interface PurchaseStatusListener {
    void onPaymentSuccess(boolean paymentStats, String message, String item_code);
    void onPaymentFailed(String message);
}