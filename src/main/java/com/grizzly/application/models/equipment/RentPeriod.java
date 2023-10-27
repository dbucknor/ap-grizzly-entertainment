package com.grizzly.application.models.equipment;

import com.grizzly.application.models.enums.RentedPer;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class RentPeriod implements Serializable {
    private LocalDateTime start, end;

    public RentPeriod() {
        start = LocalDateTime.now();
        end = LocalDateTime.now();
    }

    @NotNull
    public RentPeriod(LocalDateTime start, LocalDateTime end) throws Exception {
        checkDates(start, end);
        this.start = start;
        this.end = end;
    }

    public RentPeriod(RentPeriod period) {
        this.start = period.start;
        this.end = period.end;
    }

    private void checkDates(LocalDateTime start, LocalDateTime end) throws Exception {
        if (end.isBefore(start)) {
            throw new Exception("Invalid start and end date given!");
        }
    }

    public boolean hasEnded() {
        LocalDateTime now = LocalDateTime.now();
        return start.isBefore(now) && end.isBefore(now);
    }

    public Duration difference() {
        return Duration.between(start, end);
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

    public LocalDateTime getStart() {
        return start;
    }

    @NotNull
    public void setStart(LocalDateTime start) throws Exception {
        checkDates(start, this.end);
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @NotNull
    public void setEnd(LocalDateTime end) throws Exception {
        checkDates(this.start, end);
        this.end = end;
    }

    @Override
    public String toString() {
        return start + " - " + end;
    }
}
