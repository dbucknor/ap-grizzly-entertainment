package project.grizzly.application.models;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constraint {
    private Object value1;
    private int comparator;
    private Object value2;
    private String errorMessage;
    private FieldConfig otherField;
    private Object o;

    public final static int EQUALS = 0;
    public final static int NOT_EQUALS = 1;
    public final static int GREATER = 2;
    public final static int LESSER = 3;
    public final static int MATCHES = 4;
    public final static int NOT_NULL = 5;
    public final static int IS_NULL = 6;

    public Constraint(int comparator, String errorMessage) {
        this.comparator = comparator;
        this.errorMessage = errorMessage;
    }

    public Constraint(int comparator, Object value2, String errorMessage) {
        this.value2 = value2;
        this.comparator = comparator;
        this.errorMessage = errorMessage;
    }

    public Constraint(FieldConfig otherField, int comparator, Object value2, String errorMessage) {
        this.otherField = otherField;
        this.value2 = value2;
        this.comparator = comparator;
        this.errorMessage = errorMessage;
    }

    public Boolean validate() {
        switch (comparator) {
            case 0 -> {
                if (value1 instanceof String && value2 instanceof String) {
                    return ((String) value1).compareTo((String) value2) == 0;
                }

                if (value1 instanceof Number && value2 instanceof Number) {
                    return ((Number) value1).longValue() == ((Number) value2).longValue();
                }

                if (value1 instanceof LocalDateTime && value2 instanceof LocalDateTime) {
                    return ((LocalDateTime) value1).isEqual((LocalDateTime) value2);
                }

                return value1.equals(value2);
            }
            case 1 -> {
                if (value1 instanceof String && value2 instanceof String) {
                    return ((String) value1).compareTo((String) value2) != 0;
                }

                if (value1 instanceof Number && value2 instanceof Number) {
                    return ((Number) value1).longValue() != ((Number) value2).longValue();
                }

                if (value1 instanceof LocalDateTime && value2 instanceof LocalDateTime) {
                    return !(((LocalDateTime) value1).isEqual((LocalDateTime) value2));
                }


                return !value1.equals(value2);
            }
            case 2 -> {
                if (value1 instanceof String && value2 instanceof String) {
                    return ((String) value1).compareTo((String) value2) > 0;
                }

                if (value1 instanceof Number && value2 instanceof Number) {
                    return ((Number) value1).longValue() > ((Number) value2).longValue();
                }

                if (value1 instanceof LocalDateTime && value2 instanceof LocalDateTime) {
                    return ((LocalDateTime) value1).isAfter((LocalDateTime) value2);
                }


                return false;
            }
            case 3 -> {
                if (value1 instanceof String && value2 instanceof String) {
                    return ((String) value1).compareTo((String) value2) < 0;
                }

                if (value1 instanceof Number && value2 instanceof Number) {
                    return ((Number) value1).longValue() < ((Number) value2).longValue();
                }

                if (value1 instanceof LocalDateTime && value2 instanceof LocalDateTime) {
                    return ((LocalDateTime) value1).isBefore((LocalDateTime) value2);
                }

                return false;
            }
            case 4 -> {
                if (value1 instanceof String && value2 instanceof String) {
                    Pattern pattern = Pattern.compile((String) value2);
                    Matcher matcher = pattern.matcher((String) value1);

                    return matcher.matches();
                }

                return false;
            }
            case 5 -> {
                if (value1 instanceof String) {
                    return !((String) value1).trim().isEmpty();
                } else {
                    return value1 != null;
                }
            }
            case 6 -> {
                if (value1 instanceof String) {
                    return ((String) value1).trim().isEmpty();
                } else {
                    return value1 == null;
                }
            }
            default -> {
                return false;
            }
        }
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getValue1() {
        return value1;
    }

    public void setValue1(Object value1) {
        this.value1 = value1;
    }

    public int getComparator() {
        return comparator;
    }

    public void setComparator(int comparator) {
        this.comparator = comparator;
    }

    public Object getValue2() {
        return value2;
    }

    public void setValue2(Object value2) {
        this.value2 = value2;
    }

    public FieldConfig getOtherField() {
        return otherField;
    }

    public void setOtherField(FieldConfig otherField) {
        this.otherField = otherField;
    }
}
