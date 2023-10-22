package com.grizzly.application.models;

import com.grizzly.application.views.components.HeaderView;

import javax.swing.*;
import java.awt.*;

public class Header {
    private final HeaderView headerView;

    public Header() {
        headerView = new HeaderView();
    }

    public JPanel getUI() {
        return headerView;
    }
}
