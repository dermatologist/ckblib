package com.canehealth.ckblib.qtakes.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class QtakesRoot {
    @JsonProperty("AnatomicalSiteMention")
    public List<ConceptMention> anatomicalSiteMention;
    @JsonProperty("MedicationMention")
    public List<ConceptMention> medicationMention;
    @JsonProperty("DrugChangeStatusAnnotation")
    public List<ConceptMention> drugChangeStatusAnnotation;
    @JsonProperty("StrengthAnnotation")
    public List<ConceptMention> strengthAnnotation;
    @JsonProperty("FractionStrengthAnnotation")
    public List<ConceptMention> fractionStrengthAnnotation;
    @JsonProperty("FrequencyUnitAnnotation")
    public List<ConceptMention> frequencyUnitAnnotation;
    @JsonProperty("DiseaseDisorderMention")
    public List<ConceptMention> diseaseDisorderMention;
    @JsonProperty("SignSymptomMention")
    public List<ConceptMention> signSymptomMention;
    @JsonProperty("DateAnnotation")
    public List<ConceptMention> dateAnnotation;
    @JsonProperty("RouteAnnotation")
    public List<ConceptMention> routeAnnotation;
    @JsonProperty("MeasurementAnnotation")
    public List<ConceptMention> measurementAnnotation;
    @JsonProperty("ProcedureMention")
    public List<ConceptMention> procedureMention;
    @JsonProperty("TimeMention")
    public List<ConceptMention> timeMention;
    @JsonProperty("StrengthUnitAnnotation")
    public List<ConceptMention> strengthUnitAnnotation;
}
