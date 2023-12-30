package PdfReportGenerator.data;

import PdfReportGenerator.pojo.Embedded;
import PdfReport.pdf.data.ReportData;
import PdfReport.pdf.pojo.cucumber.*;
import PdfReport.pdf.util.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PdfReportDataGenerator {

    public ReportData generateReportData(List<PdfReportGenerator.pojo.Feature> cukeFeatures) {
        List<Feature> features = new ArrayList<>();

        for (PdfReportGenerator.pojo.Feature cukeFeature : cukeFeatures) {
            List<Status> scenarioStatuses = new ArrayList<>();
            List<Scenario> scenarios = new ArrayList<>();
            LocalDateTime featureStartTime = DateUtil
                    .convertToLocalDateTimeFromTimeStamp(cukeFeature.getElements().get(0).getStartTimestamp());
            LocalDateTime startTime = null;
            LocalDateTime endTime = null;

            for (PdfReportGenerator.pojo.Scenario cukeScenario : cukeFeature.getElements()) {
                List<Step> steps = new ArrayList<>();
                List<Status> stepHookStatuses = new ArrayList<>();

                List<Hook> beforeHooks = new ArrayList<>();
                List<Hook> afterHooks = new ArrayList<>();
                startTime = DateUtil.convertToLocalDateTimeFromTimeStamp(cukeScenario.getStartTimestamp());
                startTime = createHooks(cukeScenario.getBefore(), startTime, beforeHooks, Hook.HookType.BEFORE,
                        stepHookStatuses);

                for (PdfReportGenerator.pojo.Step cukeStep : cukeScenario.getSteps()) {
                    List<Hook> beforeStepHooks = new ArrayList<>();
                    List<Hook> afterStepHooks = new ArrayList<>();
                    startTime = createHooks(cukeStep.getBefore(), startTime, beforeStepHooks, Hook.HookType.BEFORE_STEP,
                            stepHookStatuses);
                    endTime = startTime.plusNanos(cukeStep.getResult().getDuration());
                    steps.add(Step.builder().name(cukeStep.getName()).before(beforeStepHooks).after(afterStepHooks)
                            .status(convertStatus(cukeStep.getResult().getStatus())).keyword(cukeStep.getKeyword())
                            .docString(cukeStep.getDocString().getValue()).rows(convertRows(cukeStep.getRows()))
                            .errorMessage(cukeStep.getResult().getErrorMessage()).output(cukeStep.getOutput())
                            .media(getMediaData(cukeStep.getEmbeddings())).startTime(startTime).endTime(endTime)
                            .build());
                    startTime = LocalDateTime.from(endTime);
                    stepHookStatuses.add(convertStatus(cukeStep.getResult().getStatus()));

                    startTime = createHooks(cukeStep.getAfter(), startTime, afterStepHooks, Hook.HookType.AFTER_STEP,
                            stepHookStatuses);
                }
                startTime = createHooks(cukeScenario.getAfter(), startTime, afterHooks, Hook.HookType.AFTER,
                        stepHookStatuses);

                Scenario scenario = Scenario.builder().name(cukeScenario.getName())
                        .tags(cukeScenario.getTags().stream().map(t -> t.getName()).collect(Collectors.toList()))
                        .steps(steps).before(beforeHooks).after(afterHooks).status(Collections.max(stepHookStatuses))
                        .startTime(DateUtil.convertToLocalDateTimeFromTimeStamp(cukeScenario.getStartTimestamp()))
                        .endTime(startTime).build();
                scenarios.add(scenario);
                scenarioStatuses.add(Collections.max(stepHookStatuses));
            }
            Feature feature = Feature.builder().name(cukeFeature.getName())
                    .tags(cukeFeature.getTags().stream().map(t -> t.getName()).collect(Collectors.toList()))
                    .scenarios(scenarios).status(Collections.max(scenarioStatuses)).startTime(featureStartTime)
                    .endTime(startTime).build();
            features.add(feature);
        }
        return ReportData.builder().features(features).build();
    }

    private LocalDateTime createHooks(List<PdfReportGenerator.pojo.Hook> hooks, LocalDateTime startTime,
                                      List<Hook> collectHooks, Hook.HookType hookType, List<Status> statuses) {
        LocalDateTime endTime = LocalDateTime.from(startTime);
        for (PdfReportGenerator.pojo.Hook hook : hooks) {
            endTime = startTime.plusNanos(hook.getResult().getDuration());
            collectHooks.add(
                    Hook.builder().hookType(hookType).status(convertStatus(hook.getResult().getStatus().toUpperCase()))
                            .location(hook.getMatch().getLocation()).errorMessage(hook.getResult().getErrorMessage())
                            .output(hook.getOutput()).media(getMediaData(hook.getEmbeddings())).startTime(startTime)
                            .endTime(endTime).build());
            startTime = LocalDateTime.from(endTime);
            statuses.add(convertStatus(hook.getResult().getStatus()));
        }
        return endTime;
    }

    private Status convertStatus(String statusStr) {
        Status status = Status.SKIPPED;
        if (statusStr.equalsIgnoreCase("passed"))
            status = Status.PASSED;
        else if (statusStr.equalsIgnoreCase("failed"))
            status = Status.FAILED;
        return status;
    }

    private List<String> getMediaData(List<Embedded> embeddings) {
        return embeddings.stream().map(e -> e.getFilePath()).collect(Collectors.toList());
    }

    private List<PdfReport.pdf.pojo.cucumber.Row> convertRows(List<PdfReportGenerator.pojo.Row> rows) {
        List<PdfReport.pdf.pojo.cucumber.Row> pdfRows = new ArrayList<>();
        rows.forEach(r -> {
            pdfRows.add(PdfReport.pdf.pojo.cucumber.Row.builder().cells(r.getCells()).build());
        });
        return pdfRows;
    }

}
