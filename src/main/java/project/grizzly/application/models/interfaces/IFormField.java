package project.grizzly.application.models.interfaces;

public interface IFormField<T> {
    void addListeners(FieldListeners<T> fl);

    void removeListeners(FieldListeners<T> fl);
}
