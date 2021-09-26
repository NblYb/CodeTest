package codingtest.main.pojo;

import lombok.Data;

import java.util.Objects;

@Data
public class Result {
    public String Started;
    public String Finished;
    public String DurationSecs;
    public String FromStopId;
    public String ToStopId;
    public String ChargeAmount;
    public String CompanyId;
    public String BusID;
    public String PAN;
    public String Status;

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Result)) {
            return false;
        }
        Result result = (Result) o;
        return Started.equals(result.Started)
                && ((Finished == null && result.Finished == null) || Finished.equals(result.Finished))
                && ((DurationSecs == null && result.DurationSecs == null) || DurationSecs.equals(result.DurationSecs))
                && FromStopId.equals(result.FromStopId)
                && ((ToStopId == null && result.ToStopId == null) || ToStopId.equals(result.ToStopId))
                && ChargeAmount.equals(result.ChargeAmount)
                && CompanyId.equals(result.CompanyId)
                && BusID.equals(result.BusID)
                && PAN.equals(result.PAN)
                && Status.equals(result.Status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Started,
                Finished,
                DurationSecs,
                FromStopId,
                ToStopId,
                ChargeAmount,
                CompanyId,
                BusID,
                PAN,
                Status
        );
    }
}
