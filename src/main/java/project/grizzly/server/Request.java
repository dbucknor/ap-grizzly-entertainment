package project.grizzly.server;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class Request implements Serializable {
    private String action;
    private String entity;
    private Object inputObject;

    public Request(String action, String entity, Object inputObject) {
        this.action = action;
        this.entity = entity;
        this.inputObject = inputObject;
//        System.out.println(this);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Object getInputObject() {
        return inputObject;
    }

    public void setInputObject(Object inputObject) {
        this.inputObject = inputObject;
    }

    @Override
    public String toString() {
        return "Request{" +
                "action='" + action + '\'' +
                ", entity='" + entity + '\'' +
                ", inputObject=" + inputObject +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        return new EqualsBuilder().append(action, request.action).append(entity, request.entity).append(inputObject, request.inputObject).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(action).append(entity).append(inputObject).toHashCode();
    }
}
