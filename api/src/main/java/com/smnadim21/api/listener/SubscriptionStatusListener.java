package com.smnadim21.api.listener;

public interface SubscriptionStatusListener {
    void onSuccess(boolean isSubscribed);
    void onFailed(String message);
}
