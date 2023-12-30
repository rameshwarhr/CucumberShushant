package PdfReportGenerator.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class Scenario {

    @SerializedName("start_timestamp")
    private String startTimestamp;
    private int line;
    private String name;
    private String description;
    private String id;
    private String type;
    private String keyword;
    private List<Hook> before = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();
    private List<Hook> after = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
}
