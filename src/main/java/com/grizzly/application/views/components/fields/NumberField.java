package com.grizzly.application.views.components.fields;

import com.grizzly.application.models.interfaces.FieldListeners;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.lang.Number;

public class NumberField extends TextField {
    static Logger logger = LogManager.getLogger(NumberField.class);
    private NumberFilter filter;
    private AbstractDocument doc;
    protected Number min;
    protected Number max;
    private final ArrayList<FieldListeners<Number>> listeners;
    private Class<?> numberClazz;

    public NumberField() {
        listeners = new ArrayList<>();
    }

    public NumberField(String label) {
        super(label, "");
        listeners = new ArrayList<>();
        handleFilter();
    }

    public NumberField(Class<?> clazz, String label, Number value) {
        super(label, value.toString());
        this.numberClazz = clazz;
        listeners = new ArrayList<>();
        handleFilter();
    }

    public NumberField(Class<?> clazz, String label, Number value, Number min, Number max) {
        super(label, value != null ? value.toString() : min != null ? min.toString() : "");
        listeners = new ArrayList<>();
        this.numberClazz = clazz;

        if (min != null && max != null && value != null) {
            if (value.longValue() < min.longValue() || value.longValue() > max.longValue()) {
                System.out.println("Invalid default value for range min-max!");
                System.out.println("Min and max removed.");
                handleFilter();
                return;
            }
        }

        this.min = min;
        this.max = max;
        handleFilter();
    }

    public NumberField(NumberField field) {
        super(field.label, field.value);
        this.listeners = field.getNumberListeners();
        this.numberClazz = field.numberClazz;
        this.filter = field.filter;
        this.max = field.max;
        this.min = field.min;
        handleFilter();
    }


    public Number asClassValue(String v) {
        if (numberClazz.equals(Integer.class)) {
            try {
                return Integer.parseInt(v);
            } catch (NumberFormatException ne3) {
                return 0;
            }
        } else if (numberClazz.equals(Long.class)) {
            try {
                return Long.parseLong(v);
            } catch (NumberFormatException ne4) {
                return 0;
            }
        } else if (numberClazz.equals(Double.class)) {
            try {
                return Double.parseDouble(v);
            } catch (NumberFormatException ne4) {
                return 0.0;
            }
        } else if (numberClazz.equals(Float.class)) {
            try {
                return Float.parseFloat(v);
            } catch (NumberFormatException ne4) {
                return 0f;
            }
        }
        return null;
    }

    @Override
    protected void handleChange() {
        if (field.hasFocus()) {
            value = field.getText().trim();

            updateChangeListeners();
        }
    }

    private void handleFilter() {
        filter = new NumberFilter(numberClazz);
        if (field.getDocument() instanceof AbstractDocument) {
            doc = (AbstractDocument) field.getDocument();
            doc.setDocumentFilter(filter);
        }
    }

    @Override
    protected void handleBlur() {
        value = field.getText().trim();

        Number val = asClassValue(value);
        if (max != null && !passMaxCheck(val)) {
            Toolkit.getDefaultToolkit().beep();
            value = max.toString();
            field.setText(max.toString());
        }
        if (min != null && !passMinCheck(val)) {
            Toolkit.getDefaultToolkit().beep();
            value = min.toString();
            field.setText(min.toString());
        }
        updateBlurListeners();
    }

    @Override
    public void updateChangeListeners() {
        for (FieldListeners<Number> fl : listeners
        ) {
            fl.onChange(asClassValue(value));
        }
    }

    @Override
    public void updateBlurListeners() {
        for (FieldListeners<Number> fl : listeners
        ) {
            fl.onBlur(asClassValue(value));
        }
    }

    public void addNumberListeners(FieldListeners<Number> fl) {
        listeners.add(fl);
    }

    public void removeNumberListeners(FieldListeners<Number> fl) {
        listeners.remove(fl);
    }

    public ArrayList<FieldListeners<Number>> getNumberListeners() {
        return listeners;
    }

    public Number getMin() {
        return this.min;
    }

    public void setMin(String min) {
        this.min = asClassValue(min);
    }

    public Number getMax() {
        return this.max;
    }

    public void setMax(String max) {
        this.max = asClassValue(max);
    }

    public Class<?> getNumberClazz() {
        return numberClazz;
    }

    public void setNumberClazz(Class<?> numberClazz) {
        this.numberClazz = numberClazz;
    }

    public void setAllowsNegative(Boolean allowsNegative) {
        filter.allowsNegative = allowsNegative == null || allowsNegative;
    }

    public boolean getAllowsNegative() {
        return filter.allowsNegative;
    }

    private boolean passMinCheck(Number value) {
        return min != null && asClassValue(value.toString()).longValue() >= min.longValue();
    }

    private boolean passMaxCheck(Number value) {

        return max != null && asClassValue(value.toString()).longValue() <= max.longValue();
    }

    public void setFieldEnabled(Boolean enabled) {
        field.setEnabled(enabled);
    }

    public boolean getFieldEnabled() {
        return field.isEnabled();
    }


    @Override
    public String toString() {
        return "NumberField{" +
                "min=" + min +
                ", max=" + max +
                ", label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}


class NumberFilter extends DocumentFilter {
    static Logger logger = LogManager.getLogger(NumberFilter.class);

    protected boolean allowsNegative = false;
    private Class<?> clazz;

    public NumberFilter(Class<?> clazz) {
        this.clazz = clazz;

    }


//    public void insertString(FilterBypass fb, int offs,
//                             String str, AttributeSet a)
//            throws BadLocationException {
//        if (DEBUG) {
//            System.out.println("in DocumentSizeFilter's insertString method");
//        }
//
//        //This rejects the entire insertion if it would make
//        //the contents too long. Another option would be
//        //to truncate the inserted string so the contents
//        //would be exactly maxCharacters in length.
//
//
//        Document d = fb.getDocument();
//        String textValue = d.getText(0, d.getLength());
//        Double dVal = Double.parseDouble(textValue);
//        logger.info("Inside insert: " + textValue);
//
//        if (isNumber(str, offs) && passMinCheck(dVal) && passMaxCheck(dVal))
//            super.insertString(fb, offs, str, a);
//        else
//            Toolkit.getDefaultToolkit().beep();
//    }

    public void replace(FilterBypass fb, int offs,
                        int length,
                        String str, AttributeSet a)
            throws BadLocationException {

        Document d = fb.getDocument();
        String fullText = d.getText(0, d.getLength());

//        logger.info("Inside replace: " + str + " - " + fullText);
        if (allowsDecimal(fullText, str) || isNumber(str, offs)) {

            super.replace(fb, offs, length, str, a);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private boolean isDecimalPoint(String value, String newChar) {
        return !value.contains(".") && newChar.compareTo(".") == 0;
    }

    private boolean allowsDecimal(String value, String newChar) {
        return isDecimalPoint(value, newChar) && (clazz == Double.TYPE || clazz == Float.TYPE);
    }

    public boolean isNumber(String value, int index) {
        if (allowsNegative && value.compareTo("-") == 0 && index < 1) {
            return true;
        } else {
            return NumberUtils.isParsable(value);
        }

    }

}