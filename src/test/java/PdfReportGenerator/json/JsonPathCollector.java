package PdfReportGenerator.json;

import PdfReportGenerator.exception.PdfReportPluginException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class JsonPathCollector {

    public List<Path> retrieveFilePaths(String jsonDirectory) {
        List<Path> jsonFilePaths = null;
        try {
            jsonFilePaths = Files.walk(Paths.get(jsonDirectory)).filter(Files::isRegularFile)
                    .filter(p -> p.toString().toLowerCase().endsWith(".json")).collect(Collectors.toList());
        } catch (IOException e) {
            throw new PdfReportPluginException(
                    "Unable to navigate Cucumber Json report folders. Stopping report creation. "
                            + "Check the 'pdfreport.cucumberJsonReportDirectory' plugin configuration.");
        }
        if (jsonFilePaths == null || jsonFilePaths.size() == 0)
            throw new PdfReportPluginException("No Cucumber Json Report found. Stopping report creation. "
                    + "Check the 'pdfreport.cucumberJsonReportDirectory' plugin configuration.");
        return jsonFilePaths;
    }
}
