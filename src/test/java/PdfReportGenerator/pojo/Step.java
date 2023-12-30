package PdfReportGenerator.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Step {

    private Result result;
    private int line;
    private String name;
    private Match match;
    private String keyword;
    private List<Row> rows = new ArrayList<>();
    @SerializedName("doc_string")
    private DocString docString = new DocString();
    private List<Hook> before = new ArrayList<>();
    private List<Hook> after = new ArrayList<>();
    private List<String> output = new ArrayList<>();
    private List<Embedded> embeddings = new ArrayList<>();

    private String dataTableMarkup;
    private String docStringMarkup;
}
