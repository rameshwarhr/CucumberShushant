package PdfReportGenerator.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Row {
    private List<String> cells = new ArrayList<>();
}
