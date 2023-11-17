package com.grizzly.application.views.components;

import com.grizzly.application.controllers.TableController;
import com.grizzly.application.models.InvoiceItem;
import com.grizzly.application.models.RentalRequest;
import com.grizzly.application.models.interfaces.FieldListeners;
import com.grizzly.application.models.interfaces.IView;
import com.grizzly.application.theme.ThemeManager;
import com.grizzly.application.views.components.fields.DateTimePicker;
import com.grizzly.application.views.components.fields.Toggle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.List;

public class RequestListItem extends JPanel implements IView {
    private InvoiceItem invoiceItem;
    private ThemeManager theme;
    private JPanel rentStart, rentEnd, approved;
    JLabel equipmentId, equipmentName, condition;
    private DateTimePicker start, end;
    private Toggle approve;
    private List<FieldListeners<InvoiceItem>> listeners;

    public RequestListItem(InvoiceItem invoiceItem) {
        super(new GridLayout(2, 3));
        this.invoiceItem = invoiceItem;
        theme = ThemeManager.getInstance();
    }

    @Override
    public void initializeComponents() {
        rentStart = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rentEnd = new JPanel(new FlowLayout(FlowLayout.CENTER));
        approved = new JPanel(new FlowLayout(FlowLayout.CENTER));

        equipmentId = new JLabel("Equipment Id: " + invoiceItem.getEquipment().getEquipmentId());
        condition = new JLabel("Condition: " + invoiceItem.getEquipment().getCondition());
        equipmentName = new JLabel("Equipment Name: " + invoiceItem.getEquipment().getName());

        approve = new Toggle(invoiceItem.getApproved(), null);

        start = new DateTimePicker(null, false, false, invoiceItem.getEquipment()
                .getNextAvailableDate(), LocalDateTime.now().plusYears(30));
        start.setValue(invoiceItem.getRentalStartDate());
        start.setEnabled(false);

        end = new DateTimePicker(null, false, false, start.getValue(),
                LocalDateTime.now().plusYears(30));
        end.setValue(invoiceItem.getRentalEndDate());
        end.setEnabled(false);

        rentStart.add(new JLabel("Start"));
        rentStart.add(start);
        rentEnd.add(new JLabel("End"));
        rentEnd.add(end);
        approved.add(new JLabel("Approved:"));
        approved.add(approve);
    }

    @Override
    public void addComponents() {
        this.add(equipmentId);
        this.add(equipmentName);
        this.add(condition);

        this.add(rentStart);
        this.add(rentEnd);
        this.add(approved);
    }

    @Override
    public void addListeners() {
        start.addListeners(new FieldListeners<LocalDateTime>() {
            @Override
            public void onChange(LocalDateTime fieldValue) {
                try {
                    invoiceItem.setRentalStartDate(fieldValue);
                    updateListeners();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Please ensure that start date comes before end date!", "Invalid Date Entry", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onBlur(LocalDateTime fieldValue) {
            }
        });

        end.addListeners(new FieldListeners<LocalDateTime>() {
            @Override
            public void onChange(LocalDateTime fieldValue) {
                try {
                    invoiceItem.setRentalEndDate(fieldValue);
                    updateListeners();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Please ensure that end date comes after start date!", "Invalid Date Entry", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onBlur(LocalDateTime fieldValue) {
            }
        });

        approve.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invoiceItem.setApproved(!invoiceItem.getApproved());
                updateListeners();
            }
        });
    }

    @Override
    public void setProperties() {
        this.setBackground(theme.getCurrentScheme().getNeutralLight());
        this.setSize(new Dimension(600, 40));
    }

    private void updateListeners() {
        for (FieldListeners<InvoiceItem> fl : listeners
        ) {
            fl.onChange(invoiceItem);
            fl.onBlur(invoiceItem);
        }
    }

    public void addListeners(FieldListeners<InvoiceItem> listener) {
        listeners.add(listener);
    }

    public void removeListeners(FieldListeners<InvoiceItem> listener) {
        listeners.remove(listener);
    }
}
