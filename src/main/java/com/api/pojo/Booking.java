package com.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking {

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("totalprice")
    private int totalprice;

    @JsonProperty("depositpaid")
    private boolean depositpaid;

    @JsonProperty("bookingdates")
    private BookingDates bookingdates;

    @JsonProperty("additionalneeds")
    private String additionalneeds;

    // No-arg constructor (Required by Jackson for deserialization)
    public Booking() {}

    // All-arg constructor
    public Booking(String firstname, String lastname, 
                   int totalprice, boolean depositpaid, 
                   BookingDates bookingdates, 
                   String additionalneeds) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
        this.additionalneeds = additionalneeds;
    }

    // ===== BUILDER PATTERN =====

    public static class Builder {
        private String firstname;
        private String lastname;
        private int totalprice;
        private boolean depositpaid;
        private BookingDates bookingdates;
        private String additionalneeds;

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder totalprice(int totalprice) {
            this.totalprice = totalprice;
            return this;
        }

        public Builder depositpaid(boolean depositpaid) {
            this.depositpaid = depositpaid;
            return this;
        }

        public Builder bookingdates(BookingDates bookingdates) {
            this.bookingdates = bookingdates;
            return this;
        }

        public Builder additionalneeds(String additionalneeds) {
            this.additionalneeds = additionalneeds;
            return this;
        }

        public Booking build() {
            return new Booking(firstname, lastname, 
                totalprice, depositpaid, 
                bookingdates, additionalneeds);
        }
    }

    // ===== GETTERS AND SETTERS =====

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { 
        this.firstname = firstname; 
    }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { 
        this.lastname = lastname; 
    }

    public int getTotalprice() { return totalprice; }
    public void setTotalprice(int totalprice) { 
        this.totalprice = totalprice; 
    }

    public boolean isDepositpaid() { return depositpaid; }
    public void setDepositpaid(boolean depositpaid) { 
        this.depositpaid = depositpaid; 
    }

    public BookingDates getBookingdates() { 
        return bookingdates; 
    }
    public void setBookingdates(BookingDates bookingdates) { 
        this.bookingdates = bookingdates; 
    }

    public String getAdditionalneeds() { 
        return additionalneeds; 
    }
    public void setAdditionalneeds(String additionalneeds) { 
        this.additionalneeds = additionalneeds; 
    }

    // ===== toString (for debugging and logging) =====

    @Override
    public String toString() {
        return "Booking{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", totalprice=" + totalprice +
                ", depositpaid=" + depositpaid +
                ", bookingdates=" + bookingdates +
                ", additionalneeds='" + additionalneeds + '\'' +
                '}';
    }
}