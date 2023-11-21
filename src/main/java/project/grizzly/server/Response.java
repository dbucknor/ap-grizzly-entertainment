package project.grizzly.server;

import java.io.Serializable;

public class Response implements Serializable {
    private Request request;
    private Object response;

    public Response(Request request, Object response) {
        this.request = request;
        this.response = response;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Object getValue() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Response{" +
                "request=" + request +
                ", response=" + response +
                '}';
    }
}
