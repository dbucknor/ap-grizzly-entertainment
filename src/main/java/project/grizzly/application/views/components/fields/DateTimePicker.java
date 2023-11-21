package project.grizzly.application.views.components.fields;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;
import project.grizzly.application.models.interfaces.FieldListeners;
import project.grizzly.application.models.interfaces.IFormField;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.util.ArrayList;
import java.util.Locale;

public class DateTimePicker extends JPanel implements IFormField<LocalDateTime>, IView {
    private DatePickerSettings dateSettings;
    private TimePickerSettings timeSetting;
    private Locale locale;
    private boolean allowNullDate;
    private boolean allowNullTime;
    private LocalDateTime value;
    private final ArrayList<FieldListeners<LocalDateTime>> listeners;
    private LocalDateTime min, max;
    private String label;
    private Boolean fieldEnabled;
    private JLabel fieldLbl;
    private LocalDateTime disabledStart;
    private LocalDateTime disabledEnd;
    private com.github.lgooddatepicker.components.DateTimePicker field;
    private ThemeManager theme;

    public DateTimePicker(String label, boolean allowNullDate, boolean allowNullTime, LocalDateTime min, LocalDateTime max) {
        locale = new Locale("EN", "JM");
        theme = ThemeManager.getInstance();

        this.allowNullDate = allowNullDate;
        this.allowNullTime = allowNullTime;
        this.min = min;
        this.max = max;
        this.label = label;
        this.listeners = new ArrayList<>();

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    @Override
    public void initializeComponents() {
        timeSetting = new TimePickerSettings(locale);
        dateSettings = new DatePickerSettings(locale);

//        dateSettings.setVetoPolicy(new DateVetoPolicy() {
//            @Override
//            public boolean isDateAllowed(LocalDate localDate) {
//                boolean b = true;
//
//                if (min != null) {
//                    b = localDate.isAfter(min.toLocalDate());
//                }
//                if (max != null) {
//                    b = localDate.isBefore(max.toLocalDate());
//                }
//                if (disabledEnd != null) {
//                    b = localDate.isAfter(disabledEnd.toLocalDate());
//                }
//                if (disabledStart != null) {
//                    b = localDate.isBefore(disabledStart.toLocalDate());
//                }
//
//                return b;
//            }
//        });

//        timeSetting.setVetoPolicy(new TimeVetoPolicy() {
//            @Override
//            public boolean isTimeAllowed(LocalTime localTime) {
//                boolean b = true;
//
//                if (min != null) {
//                    b = localTime.isAfter(min.toLocalTime());
//                }
//                if (max != null) {
//                    b = localTime.isBefore(max.toLocalTime());
//                }
//                if (disabledEnd != null) {
//                    b = localTime.isAfter(disabledEnd.toLocalTime());
//                }
//                if (disabledStart != null) {
//                    b = localTime.isBefore(disabledStart.toLocalTime());
//                }
//
//                return b;
//            }
//        });

        field = new com.github.lgooddatepicker.components.DateTimePicker(dateSettings, timeSetting);

        configDateSettings();
        configTimeSettings();

        field.setOpaque(true);
        field.setBackground(theme.getCurrentScheme().getAccent1());
        field.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 5));
        field.setFont(new Font("Montserrat Light", Font.PLAIN, 18));
        field.setForeground(theme.getCurrentScheme().getPrimary());
        field.setVisible(true);

        fieldLbl = new JLabel();
        fieldLbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
        fieldLbl.setFont(new Font("Montserrat Light", Font.PLAIN, 18));
        fieldLbl.setLabelFor(field);
        fieldLbl.setForeground(Color.DARK_GRAY);
        fieldLbl.setText(label);
        fieldLbl.setVisible(label != null);
        value = field.getDateTimeStrict();

    }

    @Override
    public void addComponents() {
    }

    @Override
    public void addListeners() {
        field.addDateTimeChangeListener(new DateTimeChangeListener() {
            @Override
            public void dateOrTimeChanged(DateTimeChangeEvent dateTimeChangeEvent) {
                value = dateTimeChangeEvent.getNewDateTimeStrict();
                updateListeners();
            }
        });
    }

    @Override
    public void setProperties() {
        if (value != null) {
            field.setDateTimeStrict(value);
        }

        this.setLayout(new BorderLayout());

        this.add(fieldLbl, BorderLayout.NORTH);
        this.add(field, BorderLayout.CENTER);

        this.setPreferredSize(new Dimension(300, label == null ? 40 : 65));
        this.setMaximumSize(new Dimension(300, label == null ? 40 : 65));
        this.setVisible(true);
    }

    private void configDateSettings() {
        dateSettings.setAllowEmptyDates(allowNullTime);
        dateSettings.setLocale(locale);
        dateSettings.setDateRangeLimits(min != null ? min.toLocalDate() : null, max != null ? max.toLocalDate() : null);
    }

    private void configTimeSettings() {
        timeSetting.setAllowEmptyTimes(allowNullTime);
        timeSetting.setInitialTimeToNow();
    }

    private void updateListeners() {
        for (FieldListeners<LocalDateTime> fl : listeners
        ) {
            if (fl != null) {
                fl.onBlur(value);
                fl.onChange(value);
            }
        }
    }

    @Override
    public void addListeners(FieldListeners<LocalDateTime> fl) {
        listeners.add(fl);
    }

    @Override
    public void removeListeners(FieldListeners<LocalDateTime> fl) {
        listeners.remove(fl);
    }

//    public DatePickerSettings getDateSettings() {
//        return dateSettings;
//    }
//
//    public ArrayList<FieldListeners<LocalDateTime>> getListeners() {
//        return listeners;
//    }
//
//    public void setDateSettings(DatePickerSettings dateSettings) {
//        this.dateSettings = dateSettings;
//    }
//
//    public TimePickerSettings getTimeSetting() {
//        return timeSetting;
//    }
//
//    public void setTimeSetting(TimePickerSettings timeSetting) {
//        this.timeSetting = timeSetting;
//    }


    public ArrayList<FieldListeners<LocalDateTime>> getListeners() {
        return listeners;
    }

    public LocalDateTime getDisabledStart() {
        return disabledStart;
    }

    public void setDisabledStart(LocalDateTime disabledStart) {
        this.disabledStart = disabledStart;
    }

    public LocalDateTime getDisabledEnd() {
        return disabledEnd;
    }

    public void setDisabledEnd(LocalDateTime disabledEnd) {
        this.disabledEnd = disabledEnd;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getFieldEnabled() {
        return fieldEnabled;
    }

    public void setFieldEnabled(Boolean fieldEnabled) {
        this.fieldEnabled = fieldEnabled;
        field.setEnabled(fieldEnabled);
        field.timePicker.setEnabled(fieldEnabled);
        field.datePicker.setEnabled(fieldEnabled);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean isAllowNullDate() {
        return allowNullDate;
    }

    public void setAllowNullDate(boolean allowNullDate) {
        this.allowNullDate = allowNullDate;
    }

    public boolean isAllowNullTime() {
        return allowNullTime;
    }

    public void setAllowNullTime(boolean allowNullTime) {
        this.allowNullTime = allowNullTime;
    }

    public LocalDateTime getValue() {
        return value;
    }

    public void setValue(LocalDateTime value) {
        this.value = value;
    }

    public LocalDateTime getMin() {
        return min;
    }

    public void setMin(LocalDateTime min) {
        this.min = min;
    }

    public LocalDateTime getMax() {
        return max;
    }

    public void setMax(LocalDateTime max) {
        this.max = max;
    }

    public com.github.lgooddatepicker.components.DateTimePicker getField() {
        return field;
    }
}
