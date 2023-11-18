package project.grizzly.application.controllers;

import project.grizzly.application.models.RentalRequest;

public class RequestsController extends TableController<RentalRequest, Integer> {

    public RequestsController() {
        super(RentalRequest.class);
    }

}
