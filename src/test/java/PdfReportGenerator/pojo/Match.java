package PdfReportGenerator.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Match {

    private List<Argument> arguments = new ArrayList<>();
    private String location;
}
