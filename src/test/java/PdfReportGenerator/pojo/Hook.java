package PdfReportGenerator.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Hook {

    private List<String> output = new ArrayList<>();
    private List<Embedded> embeddings = new ArrayList<>();
    private Result result;
    private Match match;
}
