package codingtest.main.enums;

public enum MaxCharge {

    STOP1("Stop1", "$7.30"),
    STOP2("Stop2", "$5.50"),
    STOP3("Stop3", "$7.30");

    private final String value;
    private final String filed;

    MaxCharge(String value, String filed) {
        this.value = value;
        this.filed = filed;
    }

    public String getValue() {
        return value;
    }

    public String getFiled() {
        return filed;
    }

    public static String codeOf(String currentValue) {
        for (MaxCharge charge : values()) {
            if (charge.getValue().equals(currentValue)) {
                return charge.getFiled();
            }
        }
        throw new RuntimeException("No value in enum found!");
    }
}