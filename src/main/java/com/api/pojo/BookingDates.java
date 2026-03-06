package com.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDates {

    @JsonProperty("checkin")
    private String checkin;

    @JsonProperty("checkout")
    private String checkout;

    // No-arg constructor (Required by Jackson for deserialization)
    public BookingDates() {}

    // All-arg constructor
    public BookingDates(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    // ===== BUILDER PATTERN =====

    public static class Builder {
        private String checkin;
        private String checkout;

        public Builder checkin(String checkin) {
            this.checkin = checkin;
            return this;
        }

        public Builder checkout(String checkout) {
            this.checkout = checkout;
            return this;
        }

        public BookingDates build() {
            return new BookingDates(checkin, checkout);
        }
    }

    // ===== GETTERS AND SETTERS =====

    public String getCheckin() { 
        return checkin; 
    }

    public void setCheckin(String checkin) { 
        this.checkin = checkin; 
    }

    public String getCheckout() { 
        return checkout; 
    }

    public void setCheckout(String checkout) { 
        this.checkout = checkout; 
    }

    // ===== toString (for debugging and logging) =====

    @Override
    public String toString() {
        return "BookingDates{" +
                "checkin='" + checkin + '\'' +
                ", checkout='" + checkout + '\'' +
                '}';
    }
}