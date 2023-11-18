package project.grizzly.application.views.components.fields;

import project.grizzly.application.models.enums.ButtonSize;
import project.grizzly.application.models.interfaces.FieldListeners;
import project.grizzly.application.models.interfaces.IFormField;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Toggle extends Button implements IFormField<Boolean> {

    private Boolean toggled;
    private final ArrayList<FieldListeners<Boolean>> listeners;

    public Toggle(Boolean defaultValue, String label) {
        this.toggled = defaultValue;
        this.size = ButtonSize.ICON;
        this.setText(label);
        this.listeners = new ArrayList<>();
        setIcons();

        setButtonColor(theme.getCurrentScheme().getNeutralLight(), theme.getCurrentScheme().getPrimary());
    }

    private void setIcons() {
        if (toggled) {
            setFontIcon(FontIcon.of(FontAwesomeSolid.TOGGLE_ON));
        } else {
            setFontIcon(FontIcon.of(FontAwesomeSolid.TOGGLE_OFF));
        }
        setIconSize(28);
    }

    public Boolean getToggled() {
        return toggled;
    }

    @Override
    protected void addListener() {
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggled = !toggled;
                setIcons();
                updateListeners();
            }
        });

        super.addListener();
    }

    public void setToggled(Boolean toggled) {
        this.toggled = toggled;
        updateListeners();
        setIcons();
    }

    public void updateListeners() {
        for (FieldListeners<Boolean> fl : listeners
        ) {
            fl.onChange(toggled);
            fl.onBlur(toggled);
        }
    }

    @Override
    public void addListeners(FieldListeners<Boolean> fl) {
        listeners.add(fl);
    }

    public ArrayList<FieldListeners<Boolean>> getListeners() {
        return listeners;
    }

    public void setFieldEnabled(Boolean enabled) {
        this.setEnabled(enabled);
    }

    public boolean getFieldEnabled() {
        return this.isEnabled();
    }

    @Override
    public void removeListeners(FieldListeners<Boolean> fl) {
        listeners.remove(fl);
    }
}
