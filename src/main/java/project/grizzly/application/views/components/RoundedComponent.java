package project.grizzly.application.views.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;


public class RoundedComponent {
    private static int borderRadius;
    private static JComponent component;
    private static int width;
    private static int height;
    private static int roundX;
    private static int roundY;

    public static Graphics paintCorners(Graphics gfx, int _borderRadius, JComponent _component) {
        borderRadius = _borderRadius;
        component = _component;
        setDimensions();

        Graphics2D g2 = (Graphics2D) gfx.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(component.getBackground());

        Area area = addRoundedBorders();

        g2.fill(area);
        g2.dispose();

        return gfx;
    }

    private static Area addRoundedBorders() {
        Area area = new Area(createRoundTopLeft());
        area.intersect(new Area(createRoundTopRight()));
        area.intersect(new Area(createRoundBottomLeft()));
        area.intersect(new Area(createRoundBottomRight()));
        return area;
    }

    private static void setDimensions() {
        width = component.getWidth();
        height = component.getHeight();
        roundX = Math.min(width, borderRadius);
        roundY = Math.min(height, borderRadius);
    }

    private static Shape createRoundTopLeft() {
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double((double) roundX / 2, 0, width - (double) roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, (double) roundY / 2, width, height - (double) roundY / 2)));
        return area;
    }

    private static Shape createRoundTopRight() {
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - (double) roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, (double) roundY / 2, width, height - (double) roundY / 2)));
        return area;
    }

    private static Shape createRoundBottomLeft() {
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double((double) roundX / 2, 0, width - (double) roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - (double) roundY / 2)));
        return area;
    }

    private static Shape createRoundBottomRight() {
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - (double) roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - (double) roundY / 2)));
        return area;
    }
}
