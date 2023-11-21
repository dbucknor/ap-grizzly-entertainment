package project.grizzly.application.views.customer;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class InvoicePanel extends JPanel {

    public InvoicePanel() {
        setPanelProperties();
        addInvoiceLabel();
        addInvoiceTablePanel();
    }

    private void setPanelProperties() {
        setBackground(new Color(255, 255, 255));
        setPreferredSize(new Dimension(615, 640));
        setLayout(new GridBagLayout());
    }

    private void addInvoiceLabel() {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 0;
        labelConstraints.fill = GridBagConstraints.HORIZONTAL;
        labelConstraints.anchor = GridBagConstraints.CENTER;
        labelConstraints.insets = new Insets(20, 20, 0, 20);

        JLabel label = createInvoiceLabel();
        add(label, labelConstraints);
    }

    private JLabel createInvoiceLabel() {
        JLabel label = new JLabel("Invoice");
        label.setOpaque(true);
        label.setBackground(new Color(230, 244, 241));
        label.setForeground(new Color(0, 58, 118));
        label.setBorder(BorderFactory.createEmptyBorder(4, 15, 4, 4));
        label.setPreferredSize(new Dimension(0, 20));
        label.setFont(new Font("SansSerif", Font.PLAIN, 20));
        return label;
    }

    private void addInvoiceTablePanel() {
        GridBagConstraints invoicegbc = new GridBagConstraints();
        invoicegbc.gridx = 0;
        invoicegbc.gridy = 1;
        invoicegbc.weightx = 1;
        invoicegbc.weighty = 1;
        invoicegbc.fill = GridBagConstraints.BOTH;
        invoicegbc.insets = new Insets(20, 20, 20, 20);

        JPanel tablePanel = createInvoiceTablePanel();
        add(tablePanel, invoicegbc);
    }

    private JPanel createInvoiceTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        JPanel panel = createTableContentPanel();
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        tablePanel.add(scroll);

        return tablePanel;
    }

    private JPanel createTableContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints panelgbc = new GridBagConstraints();
        panelgbc.fill = GridBagConstraints.HORIZONTAL;
        panelgbc.weightx = 1.0;
        panelgbc.gridx = 0;
        panelgbc.gridy = 0;
        panel.setBackground(Color.WHITE);

        for (int i = 0; i < 9; i++) {
            DefaultTableModel model = createDefaultTableModel();
            JTable table = createInvoiceTable(model);
            panel.add(createScrollPane(table), panelgbc);
            panelgbc.gridy++;

            JPanel labelPaneInvoice = createLabelPaneInvoice();
            panel.add(labelPaneInvoice, panelgbc);
            panelgbc.gridy++;

            if (i > 0) {
                panel.add(createWhiteSpace(), panelgbc);
                panelgbc.gridy++;
            }
        }
        return panel;
    }

    private DefaultTableModel createDefaultTableModel() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Item", "Qty", "Unit/Period", "Period", "Total"}, 0);
        for (int j = 0; j < 1; j++) {
            model.addRow(new Object[]{"Item " + (j + 1), j + 1, "Unit " + (j + 1), "Period " + (j + 1), (j + 1) * (j + 1)});
        }
        return model;
    }

    private JTable createInvoiceTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.getColumnModel().getColumn(0).setCellRenderer(new MultiLineTableCellRenderer());
        table.setShowVerticalLines(false);
        table.setRowHeight(80);
        if (model.getRowCount() > 0) {
            table.setTableHeader(null);
        }
        return table;
    }

    private JScrollPane createScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollPane.setPreferredSize(new Dimension(0, 83));
        scrollPane.getViewport().setBackground(Color.WHITE);
        return scrollPane;
    }

    private JPanel createLabelPaneInvoice() {
        JTextField starText1 = createTextField("text 1");
        JTextField endTextl2 = createTextField("text 2");

        JPanel labelPaneInvoice = new JPanel();
        labelPaneInvoice.setBackground(new Color(230, 244, 241));
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        Border bottomBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY);
        Border compoundBorder = BorderFactory.createCompoundBorder(emptyBorder, bottomBorder);
        labelPaneInvoice.setBorder(compoundBorder);
        labelPaneInvoice.setLayout(new FlowLayout(FlowLayout.LEFT));
        labelPaneInvoice.setPreferredSize(new Dimension(0, 35));
        labelPaneInvoice.add(starText1);
        labelPaneInvoice.add(endTextl2);
        return labelPaneInvoice;
    }

    private JTextField createTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setPreferredSize(new Dimension(80, 25));
        textField.setBorder(new EmptyBorder(0, 0, 0, 0));
        textField.setBackground(new Color(230, 244, 241));
        textField.setForeground(new Color(0, 58, 118));
        textField.setFont(new Font("SansSerif", Font.BOLD, 12));
        return textField;
    }

    private JPanel createWhiteSpace() {
        JPanel whiteSpace = new JPanel();
        whiteSpace.setBackground(Color.WHITE);
        whiteSpace.setPreferredSize(new Dimension(0, 10));
        return whiteSpace;
    }

    static class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
        MultiLineTableCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

}
