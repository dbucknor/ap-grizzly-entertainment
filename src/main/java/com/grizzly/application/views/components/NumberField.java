package com.grizzly.application.views.components;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.text.*;
import java.awt.*;

public class NumberField extends TextField {

    static Logger logger = LogManager.getLogger(NumberField.class);

    private NumberFilter filter = new NumberFilter();
    private AbstractDocument doc;

    protected Double min;
    protected Double max;

    public NumberField() {
        super();
    }

    public NumberField(String label, double value) {
        super(label, Double.toString(value));
        handleFilter();
    }

    public NumberField(String label, double value, double min, double max) throws Exception {
        super(label, Double.toString(value));

        if (value < min || value > max) {
            throw new Exception("Invalid default value for range!");
        }

        this.min = min;
        this.max = max;
        handleFilter();
    }

    public NumberField(NumberField field) {
        super(field.label, field.value);
        filter = field.filter;
        this.max = field.max;
        this.min = field.min;
        handleFilter();
    }

    @Override
    protected void handleChange() {
        if (field.hasFocus() && field.getText().compareTo(value) != 0) {
            if (onChangeCallBack != null) {
                onChangeCallBack.apply(field.getText().trim());
            }
        }
    }

    private void handleFilter() {
//        StyledDocument styledDoc = textPane.getStyledDocument();
        if (field.getDocument() instanceof AbstractDocument) {
            doc = (AbstractDocument) field.getDocument();
            doc.setDocumentFilter(filter);
        }
    }

    @Override
    protected void handleBlur() {
        Double val = Double.parseDouble(field.getText().trim());
        if (!passMaxCheck(val)) {
            Toolkit.getDefaultToolkit().beep();
            value = max.toString();
            field.setText(max.toString());
        }
        if (!passMinCheck(val)) {
            Toolkit.getDefaultToolkit().beep();
            value = min.toString();
            field.setText(min.toString());
        }

        super.handleBlur();
    }

    public Double getMin() {
        return this.min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return this.max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public void setAllowsNegative(boolean bool) {
        filter.allowsNegative = bool;
    }

    public boolean getAllowsNegative() {
        return filter.allowsNegative;
    }

    private boolean passMinCheck(Double value) {
        return min != null && value >= min;
    }

    private boolean passMaxCheck(Double value) {
        return max != null && value <= max;
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
        if (isDecimalPoint(fullText, str) || isNumber(str, offs)) {

            super.replace(fb, offs, length, str, a);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }


    }

    private boolean isDecimalPoint(String value, String newChar) {
        return !value.contains(".") && newChar.compareTo(".") == 0;
    }


    public boolean isNumber(String value, int index) {
        if (allowsNegative && value.compareTo("-") == 0 && index < 1) {
            return true;
        } else {
            return NumberUtils.isParsable(value);
        }

    }

}