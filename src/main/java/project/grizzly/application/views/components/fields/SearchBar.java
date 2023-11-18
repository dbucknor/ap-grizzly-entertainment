package project.grizzly.application.views.components.fields;

import project.grizzly.application.models.interfaces.FieldListeners;
import project.grizzly.application.models.interfaces.IFormField;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.RoundedComponent;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

public class SearchBar extends JPanel implements IView, IFormField<String> {
    private JTextField field;
    private project.grizzly.application.views.components.fields.Button searchBtn;
    private JLabel lbl;
    private String value;
    private List<FieldListeners<String>> listeners;
    private ThemeManager theme;

    public SearchBar() {
        super(new BorderLayout());
        theme = ThemeManager.getInstance();
        listeners = new ArrayList<>();

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    @Override
    public void initializeComponents() {
        field = new JTextField();
        field.setFont(theme.getFontLoader().getLIGHT().deriveFont(24f));
        lbl = new JLabel("Search");
        searchBtn = new Button(FontAwesomeSolid.SEARCH);
    }

    @Override
    public void addComponents() {
        this.add(field, BorderLayout.CENTER);
        this.add(lbl, BorderLayout.WEST);
        this.add(searchBtn, BorderLayout.EAST);
    }

    @Override
    public void addListeners() {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateChange();
            }
        });

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                value = field.getText().trim();
            }
        });
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateListeners();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(RoundedComponent.paintCorners(g, 20, this));
    }

    @Override
    public void setProperties() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(new Dimension(Double.valueOf(d.width * 0.6).intValue(), 50));
        this.setMaximumSize(new Dimension(Double.valueOf(d.width * 0.6).intValue(), 50));
        this.setMinimumSize(new Dimension(Double.valueOf(d.width * 0.6).intValue(), 50));
        this.setBackground(theme.getCurrentScheme().getNeutralLight());
        this.setOpaque(true);
    }

    private void updateListeners() {
        for (FieldListeners<String> fl : listeners
        ) {
            if (fl != null) {
                fl.onChange(value);
                fl.onBlur(value);
            }
        }
    }

    private void updateChange() {
        value = field.getText().trim();
        for (FieldListeners<String> fl : listeners
        ) {
            if (fl != null) {
                fl.onChange(value);
            }
        }
    }

    @Override
    public void addListeners(FieldListeners<String> fl) {
        listeners.add(fl);
    }

    @Override
    public void removeListeners(FieldListeners<String> fl) {
        listeners.remove(fl);
    }
}
