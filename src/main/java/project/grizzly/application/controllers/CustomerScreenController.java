package project.grizzly.application.controllers;

import project.grizzly.application.models.*;
import project.grizzly.application.models.enums.TransportOption;
import project.grizzly.application.models.equipment.*;
import project.grizzly.application.models.interfaces.FieldListeners;
import project.grizzly.application.models.interfaces.IFormField;
import project.grizzly.application.services.AuthChangedListener;
import project.grizzly.application.services.AuthService;
import project.grizzly.server.Request;
import project.grizzly.server.Response;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerScreenController extends TableController<Equipment, String> implements IFormField<List<InvoiceItem>> {
    private List<Equipment> sound, light, power, stage;
    private static CustomerScreenController instance;
    private Invoice invoice;
    private List<FieldListeners<List<InvoiceItem>>> cartListeners;

    private CustomerScreenController() {
        super(Equipment.class);
        sound = new ArrayList<>();
        power = new ArrayList<>();
        light = new ArrayList<>();
        stage = new ArrayList<>();
        cartListeners = new ArrayList<>();
        invoice = new Invoice();
        invoice.setDeliveryOption(TransportOption.PICKUP);

        getUser();
    }

    public static CustomerScreenController getInstance() {
        if (instance == null) {
            instance = new CustomerScreenController();
        }
        return instance;
    }

    private void getUser() {
        AuthService.getInstance().addAuthChangedListener(new AuthChangedListener<User>() {
            @Override
            public void onAuthChanged(User user) {
                if (user instanceof Customer c) {
                    invoice.setCustomer(c);
                }
            }
        });
    }

    public void sort() {
        sound = new ArrayList<>();
        power = new ArrayList<>();
        light = new ArrayList<>();
        stage = new ArrayList<>();

        for (Equipment e : records
        ) {
            if (e instanceof Sound) {
                sound.add(e);
            }
            if (e instanceof Stage) {
                stage.add(e);
            }
            if (e instanceof Power) {
                power.add(e);
            }
            if (e instanceof Light) {
                light.add(e);
            }
        }
    }

    public void addToRequest(InvoiceItem item) {
        item.setInvoice(invoice);
        item.calculatePrice();
        invoice.getItems().add(item);
        invoice.setTotalPrice(invoice.getTotalPrice() + item.getTotalPrice());
        logger.info("Item added: " + item);
        logger.info("Item added: " + invoice);
        updateCartListeners();
    }

    public void removeFromRequest(InvoiceItem item) {
        item.calculatePrice();
        invoice.getItems().remove(item);
        invoice.setTotalPrice(invoice.getTotalPrice() - item.getTotalPrice());
        logger.info("Item removed: " + item);
        updateCartListeners();
    }


    public void handleSearch(String value) {
        filteredRecords = new ArrayList<>();
        for (Equipment e : records
        ) {
            if (e.toString().toLowerCase().contains(value.toLowerCase())) {
                filteredRecords.add(e);
            }
        }
    }

    public void sendRequest(RentalRequest rentalRequest) {
        executorService.submit(() -> {
            try {
                Request r = new Request("ADD", "RENTALREQUEST", rentalRequest);
                client.sendRequest(r);
                Object o = ((Response) client.receiveResponse(r)).getValue();

                SwingUtilities.invokeLater(() -> {
                    if (o instanceof Boolean && (Boolean) o) {
                        JOptionPane.showMessageDialog(null, "Request Sent!");
                        emptyCart();
                    } else {
                        JOptionPane.showMessageDialog(null, "Request Not sent!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Request Not sent!", "Error", JOptionPane.ERROR_MESSAGE);

                });
            }

        });
    }

    public void sendInvoice(Invoice invoice) {
        executorService.submit(() -> {
            try {
                Request r = new Request("ADD", "INVOICE", invoice);
                client.sendRequest(r);
                Object o = ((Response) client.receiveResponse(r)).getValue();

                SwingUtilities.invokeLater(() -> {
                    if (o instanceof Boolean && (Boolean) o) {
                        JOptionPane.showMessageDialog(null, "Invoice Sent!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Invoice Not sent!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Invoice sent!", "Error", JOptionPane.ERROR_MESSAGE);

                });
            }

        });
    }

    public void updateCartListeners() {
        for (FieldListeners<List<InvoiceItem>> fl : cartListeners) {
            if (fl != null) {
                fl.onChange(invoice.getItems());
                fl.onBlur(invoice.getItems());
            }
        }
    }

    public void emptyCart() {
        invoice = new Invoice();
        updateCartListeners();
    }

    public List<Equipment> getSound() {
        return sound;
    }

    public void setSound(List<Equipment> sound) {
        this.sound = sound;
    }

    public List<Equipment> getLight() {
        return light;
    }

    public void setLight(List<Equipment> light) {
        this.light = light;
    }

    public List<Equipment> getPower() {
        return power;
    }

    public void setPower(List<Equipment> power) {
        this.power = power;
    }

    public List<Equipment> getStage() {
        return stage;
    }

    public void setStage(List<Equipment> stage) {
        this.stage = stage;
    }

    public List<InvoiceItem> getRequestList() {
        return invoice.getItems();
    }

    public void setRequestList(List<InvoiceItem> requestList) {
        invoice.setItems(new ArrayList<>(requestList));
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public void addListeners(FieldListeners<List<InvoiceItem>> fl) {
        cartListeners.add(fl);
    }

    @Override
    public void removeListeners(FieldListeners<List<InvoiceItem>> fl) {
        cartListeners.remove(fl);
    }


}
