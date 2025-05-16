package mdm.engsoft2.dem.transform;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Currency {
    private String currencyCode;
    private String currencyName;
    private String currencySymbol;
    private String countryId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp updatedAt;

    public Currency(String currencyCode, String currencyName, String currencySymbol, String countryId) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.countryId = countryId;    
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = null;
    }
}