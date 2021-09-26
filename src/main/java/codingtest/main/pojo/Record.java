package codingtest.main.pojo;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Record {
    @CsvBindByPosition(position = 0)
    public String ID;
    @CsvBindByPosition(position = 1)
    public String DateTimeUTC;
    @CsvBindByPosition(position = 2)
    public String TapType;
    @CsvBindByPosition(position = 3)
    public String StopId;
    @CsvBindByPosition(position = 4)
    public String CompanyId;
    @CsvBindByPosition(position = 5)
    public String BusID;
    @CsvBindByPosition(position = 6)
    public String PAN;
}
