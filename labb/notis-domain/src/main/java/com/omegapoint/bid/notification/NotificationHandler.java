package com.omegapoint.bid.notification;

public interface NotificationHandler {
    /**
     * Push message to the specific device
     * @param deviceId
     * @param message
     */
    public void push(String deviceId, String message);
}
