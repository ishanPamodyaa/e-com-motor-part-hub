package com.motorcycleparts.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingInfoDto {
    private String trackingNumber;
    private String carrier;
    private String trackingUrl;
    
    // Factory method to create tracking info from notes
    public static TrackingInfoDto fromOrderNotes(String notes) {
        if (notes == null || notes.isEmpty()) {
            return null;
        }
        
        TrackingInfoDto trackingInfo = new TrackingInfoDto();
        trackingInfo.setTrackingNumber(notes);
        trackingInfo.setCarrier("Default Carrier");
        trackingInfo.setTrackingUrl("https://track.carrier.com/" + notes);
        
        return trackingInfo;
    }
} 