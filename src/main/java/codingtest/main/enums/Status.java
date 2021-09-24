package codingtest.main.enums;

public enum Status {

    COMPLETED("complete"),
    INCOMPLETE("incomplete"),
    CANCELED("canceled");

    private final String value;

    private Status(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
