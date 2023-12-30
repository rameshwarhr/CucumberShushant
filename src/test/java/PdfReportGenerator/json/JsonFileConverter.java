package PdfReportGenerator.json;

import PdfReportGenerator.exception.PdfReportPluginException;
import PdfReportGenerator.json.deserializer.EmbeddedDeserializer;
import PdfReportGenerator.logging.PdfReportLogger;
import PdfReportGenerator.pojo.Embedded;
import PdfReportGenerator.pojo.Feature;
import PdfReportGenerator.processor.EmbeddedProcessor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JsonFileConverter {

    private EmbeddedProcessor embeddedProcessor;
    //private PdfReportLogger logger;

    @Inject
    public JsonFileConverter(EmbeddedProcessor embeddedProcessor) {
        this.embeddedProcessor = embeddedProcessor;
        //this.logger = logger;
    }

    public List<Feature> retrieveFeaturesFromReport(List<Path> jsonFilePaths) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Embedded.class, new EmbeddedDeserializer(embeddedProcessor))
                .create();

        List<Feature> features = new ArrayList<>();
        Feature[] parsedFeatures = null;

        for (Path jsonFilePath : jsonFilePaths) {

            try {
                parsedFeatures = gson.fromJson(Files.newBufferedReader(jsonFilePath), Feature[].class);
            } catch (JsonSyntaxException | JsonIOException | IOException e) {
                /*logger.warn(String.format(
                        "Skipping json report at '%s', as unable to parse json report file to Feature pojo.",
                        jsonFilePath));*/
                continue;
            }

            if (parsedFeatures == null || parsedFeatures.length == 0) {
               /* logger.warn(String.format(
                        "Skipping json report at '%s', parsing json report file returned no Feature pojo.",
                        jsonFilePath));*/
                continue;
            }
            features.addAll(Arrays.asList(parsedFeatures));
        }

        if (features.size() == 0)
            throw new PdfReportPluginException("No Feature found in report. Stopping report creation. "
                    + "Check the 'extentreport.cucumberJsonReportDirectory' plugin configuration.");

        if (!features.stream().flatMap(f -> f.getElements().stream())
                .filter(s -> !s.getKeyword().equalsIgnoreCase("Background")
                        && (s.getStartTimestamp() == null || s.getStartTimestamp().isEmpty()))
                .collect(Collectors.toList()).isEmpty())
            throw new PdfReportPluginException(
                    "Start timestamp data of scenario is essential but is missing in json report. "
                            + "Plugin only generates report for Cucumber-JVM 4.3.0 and above. "
                            + "If Cucumber version is in the valid range, do submit an issue.");
        return features;
    }
}
