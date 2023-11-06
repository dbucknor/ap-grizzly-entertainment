package com.grizzly.application.models;

import com.grizzly.application.models.enums.FormFieldType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FieldConfig {
    private FormFieldType fieldType;
    private boolean isId;
    private Object max;
    private Object min;
    private String label;
    private boolean disabled;
    private boolean allowsNegative;
    private String regex;
    private Object[] options;
    private Class<?> clazz;
    private String mutatorName;
    private String accessorName;

    private List<Constraint> constraintList;

    public FieldConfig(Class<?> clazz, String mutatorName, String accessorName, String label, FormFieldType fieldType, Object[] options) {
        this.label = label;
        this.fieldType = fieldType;
        this.options = options;
        this.isId = false;
        this.mutatorName = mutatorName;
        this.accessorName = accessorName;
        this.clazz = clazz;
        this.constraintList = new ArrayList<>();
    }

    public FieldConfig(Class<?> clazz, String mutatorName, String accessorName, String label, FormFieldType fieldType, Object max, Object min, boolean isId) {
        this.label = label;
        this.mutatorName = mutatorName;
        this.clazz = clazz;
        this.accessorName = accessorName;
        this.fieldType = fieldType;
        this.max = max;
        this.min = min;
        this.isId = isId;
        this.constraintList = new ArrayList<>();

    }

    public FieldConfig(Class<?> clazz, String mutatorName, String accessorName, String label, FormFieldType fieldType, Object max, Object min) {
        this.label = label;
        this.mutatorName = mutatorName;
        this.clazz = clazz;
        this.fieldType = fieldType;
        this.accessorName = accessorName;
        this.max = max;
        this.min = min;
        this.isId = false;
        this.constraintList = new ArrayList<>();
    }

    public FieldConfig(Class<?> clazz, String mutatorName, String accessorName, String label, FormFieldType fieldType, boolean isId) {
        this.label = label;
        this.fieldType = fieldType;
        this.isId = isId;
        this.mutatorName = mutatorName;
        this.accessorName = accessorName;
        this.clazz = clazz;
        this.constraintList = new ArrayList<>();
    }

    public FieldConfig(Class<?> clazz, String mutatorName, String accessorName, String label, FormFieldType fieldType) {
        this.label = label;
        this.fieldType = fieldType;
        this.isId = false;
        this.mutatorName = mutatorName;
        this.accessorName = accessorName;
        this.clazz = clazz;
        this.constraintList = new ArrayList<>();
    }

    public FieldConfig(Class<?> clazz, String mutatorName, String accessorName, String label, FormFieldType fieldType, String regex, boolean isId) {
        this.label = label;
        this.fieldType = fieldType;
        this.regex = regex;
        this.accessorName = accessorName;
        this.mutatorName = mutatorName;
        this.clazz = clazz;
        this.constraintList = new ArrayList<>();
    }

    public FieldConfig(Class<?> clazz, String mutatorName, String accessorName, String label, FormFieldType fieldType, Object max, Object min, boolean disabled, boolean isId, String regex) {
        this.label = label;
        this.fieldType = fieldType;
        this.max = max;
        this.min = min;
        this.accessorName = accessorName;
        this.disabled = disabled;
        this.regex = regex;
        this.isId = isId;
        this.mutatorName = mutatorName;
        this.clazz = clazz;
        this.constraintList = new ArrayList<>();
    }

    public FieldConfig addConstraint(Constraint constraint) {
        constraintList.add(constraint);
        return this;
    }


    public FormFieldType getFieldType() {
        return fieldType;
    }

    public List<Constraint> getConstraintList() {
        return constraintList;
    }

    public void setConstraintList(List<Constraint> constraintList) {
        this.constraintList = constraintList;
    }

    public void setFieldType(FormFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Object getMax() {
        return max;
    }


    public Object getMin() {
        return min;
    }

    public boolean isId() {
        return isId;
    }

    public void setId(boolean id) {
        isId = id;
    }

    public void setMax(Number max) {
        this.max = max;
    }

    public void setMin(Number min) {
        this.min = min;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object[] getOptions() {
        return options;
    }

    public void setOptions(Object[] options) {
        this.options = options;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getMutatorName() {
        return mutatorName;
    }

    public void setMutatorName(String mutatorName) {
        this.mutatorName = mutatorName;
    }

    public String getAccessorName() {
        return accessorName;
    }

    public void setAccessorName(String accessorName) {
        this.accessorName = accessorName;
    }

    public void setMax(Object max) {
        this.max = max;
    }

    public void setMin(Object min) {
        this.min = min;
    }

    public boolean isAllowsNegative() {
        return allowsNegative;
    }

    public void setAllowsNegative(boolean allowsNegative) {
        this.allowsNegative = allowsNegative;
    }
}
