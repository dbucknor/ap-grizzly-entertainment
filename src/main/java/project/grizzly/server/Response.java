package project.grizzly.server;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Response response1 = (Response) o;

        return new EqualsBuilder().append(request, response1.request).append(response, response1.response).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(request).append(response).toHashCode();
    }

    @Override
    public String toString() {
        return "Response{" +
                "request=" + request +
                ", response=" + response +
                '}';
    }
}
