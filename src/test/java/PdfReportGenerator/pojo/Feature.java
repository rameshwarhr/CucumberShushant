package PdfReportGenerator.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Feature {
    private int line;
    private List<Scenario> elements = new ArrayList<>();
    private String name;
    private String description;
    private String id;
    private String keyword;
    private String uri;
    private List<Tag> tags = new ArrayList<>();
}
