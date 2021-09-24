package codingtest.main.enums;

public enum NormalCharge {

    STOP1_2("Stop1", "Stop2", "$3.25"),
    STOP1_3("Stop1", "Stop3", "$7.30"),
    STOP2_1("Stop2", "Stop1", "$3.25"),
    STOP2_3("Stop2", "Stop3", "$5.50"),
    STOP3_1("Stop3", "Stop1", "$7.30"),
    STOP3_2("Stop3", "Stop2", "$5.50"),
    CANCELED(null, null, "$0");

    private final String start;
    private final String end;
    private final String filed;

    NormalCharge(String start, String end, String filed) {
        this.start = start;
        this.end = end;
        this.filed = filed;
    }

    public String getStart() { return start; }

    public String getEnd() { return end; }

    public String getFiled() { return filed; }

    public static String codeOf(String startStop, String endStop) {
        for (NormalCharge charge : values()) {
            if (charge.getStart().equals(startStop) && charge.getEnd().equals(endStop)) {
                return charge.getFiled();
            }
        }
        throw new RuntimeException("No value in enum found!");
    }
}
