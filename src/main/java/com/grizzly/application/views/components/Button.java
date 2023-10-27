package com.grizzly.application.views.components;

import com.grizzly.application.theme.ThemeManager;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Button extends JButton {

    protected ThemeManager theme = ThemeManager.getInstance();
    protected ButtonSize size;
    protected int borderRadius;
    protected boolean hasFocus = false;
    protected boolean outlined = false;
    private Color background;
    private Color foreground;

    /**
     * Creates a generic button
     */
    public Button() {
        this.setText("Button");
        size = ButtonSize.NORMAL;
        addListener();
        setProps();
    }


    public Button(String text, ButtonSize size) {
        this.size = size;
        this.setText(text);
        addListener();
        setProps();
    }

    public Button(String text, Icon icon, ButtonSize size) {
        this.setText(text);
        this.setIcon(icon);
        this.size = size;
        addListener();
        setProps();
    }

    public Button(Ikon icon) {
        this.setText("");
        this.setButtonIcon(icon);
        this.size = ButtonSize.ICON;
        addListener();
        setProps();
    }

    protected void addListener() {

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(background.brighter());
//                handleClick();

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(background);

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (hasFocus && (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE)) {
                    setBackground(background.brighter());
//                    handleClick();

                }
                super.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (hasFocus && (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE)) {
                    setBackground(background);
                }
                super.keyReleased(e);
            }
        });
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                hasFocus = true;
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                hasFocus = false;
                super.focusLost(e);
            }
        });

    }
//
//
//    protected void handleClick() {
//        if (onClickCallBack != null) {
//            onClickCallBack.apply(null);
//        }
//    }

    protected void handleButtonSizing() {
        switch (size) {
            case SMALL -> {
                borderRadius = 12;
                this.setPreferredSize(new Dimension(75, 30));
                this.setFont(new Font("Montserrat Regular", Font.PLAIN, 16));
            }
            case EXTEND -> {
                borderRadius = 15;
                this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 40));
                this.setFont(new Font("Montserrat Regular", Font.PLAIN, 18));
            }
            case ICON -> {
                borderRadius = 15;
                this.setPreferredSize(new Dimension(30, 30));
            }
            default -> {
                borderRadius = 15;
                this.setPreferredSize(new Dimension(250, 40));
                this.setFont(new Font("Montserrat Regular", Font.PLAIN, 18));

            }
        }
    }

//    public void onClick(Function onClick) {
//        this.onClickCallBack = onClick;
//    }

    private void setProps() {
        handleButtonSizing();

        background = theme.getCurrentScheme().getPrimary();
        this.setBackground(background);

        foreground = theme.getCurrentScheme().getNeutralLight();
        this.setForeground(foreground);

        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setVisible(true);
    }


    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        Area area = new Area(createRoundTopLeft());
        area.intersect(new Area(createRoundTopRight()));
        area.intersect(new Area(createRoundBottomLeft()));
        area.intersect(new Area(createRoundBottomRight()));
        g2.fill(area);
        g2.dispose();
        super.paintComponent(grphcs);
    }

    private Shape createRoundTopLeft() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, borderRadius);
        int roundY = Math.min(height, borderRadius);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double((double) roundX / 2, 0, width - (double) roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, (double) roundY / 2, width, height - (double) roundY / 2)));
        return area;
    }

    private Shape createRoundTopRight() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, borderRadius);
        int roundY = Math.min(height, borderRadius);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - (double) roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, (double) roundY / 2, width, height - (double) roundY / 2)));
        return area;
    }

    private Shape createRoundBottomLeft() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, borderRadius);
        int roundY = Math.min(height, borderRadius);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundBottomRight() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, borderRadius);
        int roundY = Math.min(height, borderRadius);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }

    public void setButtonColor(Color background, Color foreground) {
        this.foreground = foreground;
        this.background = background;
        this.setBackground(background);
        this.setForeground(foreground);
    }

    public void setSize(ButtonSize size) {
        this.size = size;
    }

    public int getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
    }

    public void setButtonIcon(Ikon icon) {
        this.setIcon(FontIcon.of(icon, 24, theme.getCurrentScheme().getNeutralLight()));
    }

//    @Override
//    public void onChange(Object field) {
//
//    }
//
//    @Override
//    public void onBlur(Object field) {
//
//    }
}

