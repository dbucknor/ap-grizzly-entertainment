package project.grizzly.application.views.screens;

public class Product {
    private String category; // Lighting, Sound, Power, Stage
    private String title;
    private double price;
    private String details;
	public Product(String category, String title, double price, String details) {
		super();
		this.category = category;
		this.title = title;
		this.price = price;
		this.details = details;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

    
}
