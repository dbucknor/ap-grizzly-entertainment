package com.grizzly.application.models;

public class Stage extends Equipment {
    private float width;
    private float height;
    private float length;

    public Stage() {
        super();
        this.width = 0.0f;
        this.height = 0.0f;
        this.length = 0.0f;
    }

    public Stage(String id, String name, String description, String category, float price,
                 ArrayList<MaintainanceLog> maintainaceLogs, String type, Date nextAvaliableDate,
                 float width, float height, float length) {
        super(id, name, description, category, price, maintainaceLogs, type, nextAvaliableDate);
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public Stage(Stage stage) {
        super(stage);
        this.width = stage.width;
        this.height = stage.height;
        this.length = stage.length;
    }
    
    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "width=" + width +
                ", height=" + height +
                ", length=" + length +
                "} " + super.toString();
    }
}
