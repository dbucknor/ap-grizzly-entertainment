package com.grizzly.application.controllers;

import com.grizzly.application.models.RentalRequest;

public class RequestsController extends TableController<RentalRequest, Integer> {

    public RequestsController() {
        super(RentalRequest.class);
    }

}
