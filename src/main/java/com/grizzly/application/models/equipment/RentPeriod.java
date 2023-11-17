package com.grizzly.application.models.equipment;

import com.grizzly.application.models.enums.RentedPer;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

@Embeddable
public class RentPeriod implements Serializable {
    @Column(name = "rentalStartDate")
    private LocalDateTime rentalStartDate;

    @Column(name = "rentalEndDate")
    private LocalDateTime rentalEndDate;

    public RentPeriod() {
        rentalStartDate = LocalDateTime.now();
        rentalEndDate = LocalDateTime.now();
    }

    @NotNull
    public RentPeriod(LocalDateTime rentalStartDate, LocalDateTime rentalEndDate) throws Exception {
        checkDates(rentalStartDate, rentalEndDate);
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
    }

    public RentPeriod(RentPeriod period) {
        this.rentalStartDate = period.rentalStartDate;
        this.rentalEndDate = period.rentalEndDate;
    }

    public void checkDates(LocalDateTime start, LocalDateTime end) throws Exception {
        if (end.isBefore(start)) {
            throw new Exception("Invalid start and end date given!");
        }
    }

    public boolean hasEnded() {
        LocalDateTime now = LocalDateTime.now();
        return rentalStartDate.isBefore(now) && rentalEndDate.isBefore(now);
    }

    public Duration difference() {
        return Duration.between(rentalStartDate, rentalEndDate);
    }

    public int asHours() {
        return (int) difference().toHours();
    }

    public int asDays() {
        return asHours() / 24;
    }

    public int asWeeks() {
        return asDays() / 7;
    }

    public int asMonths() {
        return Period.ofDays(asDays()).getMonths();
    }

    public int periodAs(RentedPer rentedPer) {
        switch (rentedPer) {
            case HOUR -> {
                return asHours();
            }
            case WEEK -> {
                return asWeeks();
            }
            case MONTH -> {
                return asMonths();
            }
            default -> {
                return asDays();
            }
        }
    }

    public LocalDateTime getRentalStartDate() {
        return rentalStartDate;
    }

    @NotNull
    public void setRentalStartDate(LocalDateTime rentalStartDate) throws Exception {
        checkDates(rentalStartDate, this.rentalEndDate);
        this.rentalStartDate = rentalStartDate;
    }

    public LocalDateTime getRentalEndDate() {
        return rentalEndDate;
    }

    @NotNull
    public void setRentalEndDate(LocalDateTime rentalEndDate) throws Exception {
        checkDates(this.rentalStartDate, rentalEndDate);
        this.rentalEndDate = rentalEndDate;
    }

    @Override
    public String toString() {
        return rentalStartDate + " - " + rentalEndDate;
    }
}
