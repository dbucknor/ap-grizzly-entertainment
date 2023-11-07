package com.grizzly.application.views.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomCardLayout extends CardLayout {

    private String selectedCardName;
    private final HashMap<String, Component> cards;

    public CustomCardLayout() {
        cards = new HashMap<>();
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        cards.put((String) constraints, comp);
        this.selectedCardName = (String) constraints;
        super.addLayoutComponent(comp, constraints);
    }

    @Override
    public void show(Container parent, String name) {
        selectedCardName = name;
        super.show(parent, name);
    }

    public boolean isCurrentCard(String name) {
        return selectedCardName.compareTo(name) == 0;

    }

    public Component getSelectedCard() {
        return cards.get(selectedCardName);
    }

    public String getSelectedCardName() {
        return selectedCardName;
    }
}
