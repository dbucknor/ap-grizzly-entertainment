package project.grizzly.application.views.components.fields;

import project.grizzly.application.models.enums.ButtonSize;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.RoundedComponent;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Button extends JButton {

    protected ThemeManager theme = ThemeManager.getInstance();
    protected ButtonSize size;
    protected boolean hasFocus = false;
    protected boolean outlined = false;
    protected int borderRadius;

    protected Color background;
    protected Color foreground;
    protected FontIcon icon;
    protected int iconSize;

    /**
     * Creates a generic button
     */
    public Button() {
        this.setText("Button");
        size = ButtonSize.NORMAL;
        setButtonColor(theme.getCurrentScheme().getPrimary(), theme.getCurrentScheme().getNeutralLight());
        addListener();
        setProps();
    }


    public Button(String text, ButtonSize size) {
        this.size = size;
        setButtonColor(theme.getCurrentScheme().getPrimary(), theme.getCurrentScheme().getNeutralLight());
        this.setText(text);
        addListener();
        setProps();
    }

    public Button(String text, Icon icon, ButtonSize size) {
        this.setText(text);
        setButtonColor(theme.getCurrentScheme().getPrimary(), theme.getCurrentScheme().getNeutralLight());
        this.setIcon(icon);
        this.size = size;
        addListener();
        setProps();
    }

    public Button(Ikon icon) {
        this.setText("");
        setButtonColor(theme.getCurrentScheme().getPrimary(), theme.getCurrentScheme().getNeutralLight());
        this.icon = FontIcon.of(icon, 24, foreground);
        this.setFontIcon(this.icon);
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
                if (icon != null) {
                    setIconColor(foreground.brighter().brighter());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(background);
                if (icon != null) {
                    setIconColor(foreground);
                }
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
                    if (icon != null) {
                        setIconColor(foreground.brighter().brighter());
                    }
                }
                super.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (hasFocus && (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE)) {
                    setBackground(background);
                    if (icon != null) {
                        setIconColor(foreground);
                    }
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

    protected void handleButtonSizing() {
        switch (size) {
            case SMALL -> {
                borderRadius = 10;
                this.setSize(new Dimension(70, 20));
//                this.setMinimumSize(new Dimension(60, 20));
                this.setFont(theme.getFontLoader().getBODY().deriveFont(Font.BOLD, 14f));
                this.setVerticalAlignment(SwingConstants.CENTER);
            }
            case EXTEND -> {
                borderRadius = 15;
                this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 40));
                this.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 40));
                this.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 40));
                this.setFont(new Font("Montserrat Regular", Font.PLAIN, 18));
            }
            case ICON -> {
                borderRadius = 15;
                this.setPreferredSize(new Dimension(30, 30));
                this.setMinimumSize(new Dimension(30, 30));
                this.setMaximumSize(new Dimension(30, 30));
            }
            default -> {
                borderRadius = 15;
                this.setPreferredSize(new Dimension(250, 40));
                this.setMinimumSize(new Dimension(250, 40));
                this.setFont(new Font("Montserrat Regular", Font.PLAIN, 18));

            }
        }
    }

    public void setButtonColor(Color background, Color foreground) {
        this.foreground = foreground;
        this.background = background;
        this.setBackground(background);
        this.setForeground(foreground);
        setIconColor(foreground);
        this.repaint();
    }

    public void setIconSize(int iconSize) {
        if (this.icon == null) return;
        this.iconSize = iconSize;
        this.icon.setIconSize(iconSize);
        this.setFontIcon(icon);
        this.setMinimumSize(new Dimension(iconSize + 20, iconSize + 20));
        this.setPreferredSize(new Dimension(iconSize + 20, iconSize + 20));
        this.setMaximumSize(new Dimension(iconSize + 20, iconSize + 20));
    }

    public void setIconColor(Color color) {
        if (this.icon == null) return;
        this.icon.setIconColor(color);
        this.setFontIcon(icon);
    }

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
    public void paint(Graphics g) {
        super.paint(RoundedComponent.paintCorners(g, borderRadius, this));
    }

    public void setFontIcon(FontIcon icon) {
        this.icon = icon;
        this.setIcon(icon);
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

    public FontIcon getFontIcon() {
        return icon;
    }

    public int getIconSize() {
        return iconSize;
    }
}

