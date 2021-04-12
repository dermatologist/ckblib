// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */
public class ConceptAttribute{
    public String code;
    public String cui;
    public String codingScheme;
    public String tui;
}

public class AnatomicalSiteMention{
    public int begin;
    public int end;
    public String text;
    public int polarity;
    public List<ConceptAttribute> conceptAttributes;
}

public class MedicationMention{
    public int begin;
    public int end;
    public String text;
    public int polarity;
    public List<ConceptAttribute> conceptAttributes;
}

public class DiseaseDisorderMention{
    public int begin;
    public int end;
    public String text;
    public int polarity;
    public List<ConceptAttribute> conceptAttributes;
}

public class SignSymptomMention{
    public int begin;
    public int end;
    public String text;
    public int polarity;
    public List<ConceptAttribute> conceptAttributes;
}

public class ProcedureMention{
    public int begin;
    public int end;
    public String text;
    public int polarity;
    public List<ConceptAttribute> conceptAttributes;
}

public class Root{
    @JsonProperty("AnatomicalSiteMention")
    public List<AnatomicalSiteMention> anatomicalSiteMention;
    @JsonProperty("MedicationMention")
    public List<MedicationMention> medicationMention;
    @JsonProperty("DrugChangeStatusAnnotation")
    public List<Object> drugChangeStatusAnnotation;
    @JsonProperty("StrengthAnnotation")
    public List<Object> strengthAnnotation;
    @JsonProperty("FractionStrengthAnnotation")
    public List<Object> fractionStrengthAnnotation;
    @JsonProperty("FrequencyUnitAnnotation")
    public List<Object> frequencyUnitAnnotation;
    @JsonProperty("DiseaseDisorderMention")
    public List<DiseaseDisorderMention> diseaseDisorderMention;
    @JsonProperty("SignSymptomMention")
    public List<SignSymptomMention> signSymptomMention;
    @JsonProperty("DateAnnotation")
    public List<Object> dateAnnotation;
    @JsonProperty("RouteAnnotation")
    public List<Object> routeAnnotation;
    @JsonProperty("MeasurementAnnotation")
    public List<Object> measurementAnnotation;
    @JsonProperty("ProcedureMention")
    public List<ProcedureMention> procedureMention;
    @JsonProperty("TimeMention")
    public List<Object> timeMention;
    @JsonProperty("StrengthUnitAnnotation")
    public List<Object> strengthUnitAnnotation;
}

curl -X POST -H "Content-Type: text/plain" --data "this is not hypertension, but diabetes with pruritus and erythema  treated with oral paracetamol and blepharoplasty in the morning" 127.0.0.1:8093/analyze

{"AnatomicalSiteMention":[{"begin":73,"end":77,"text":"face","polarity":0,"conceptAttributes":[{"code":"89545001","cui":"C0015450","codingScheme":"SNOMEDCT_US","tui":"T029"}]},{"begin":91,"end":95,"text":"oral","polarity":0,"conceptAttributes":[{"code":"74262004","cui":"C0226896","codingScheme":"SNOMEDCT_US","tui":"T030"}]}],"MedicationMention":[{"begin":91,"end":95,"text":"oral","polarity":0,"conceptAttributes":[{"code":"385268001","cui":"C1272919","codingScheme":"SNOMEDCT_US","tui":"T122"}]},{"begin":96,"end":107,"text":"paracetamol","polarity":0,"conceptAttributes":[{"code":"387517004","cui":"C0000970","codingScheme":"SNOMEDCT_US","tui":"T109"},{"code":"90332006","cui":"C0000970","codingScheme":"SNOMEDCT_US","tui":"T121"},{"code":"90332006","cui":"C0000970","codingScheme":"SNOMEDCT_US","tui":"T109"},{"code":"161","cui":"C0000970","codingScheme":"RXNORM","tui":"T121"},{"code":"161","cui":"C0000970","codingScheme":"RXNORM","tui":"T109"},{"code":"387517004","cui":"C0000970","codingScheme":"SNOMEDCT_US","tui":"T121"}]}],"DrugChangeStatusAnnotation":[],"StrengthAnnotation":[],"FractionStrengthAnnotation":[],"FrequencyUnitAnnotation":[],"DiseaseDisorderMention":[{"begin":12,"end":24,"text":"hypertension","polarity":-1,"conceptAttributes":[{"code":"38341003","cui":"C0020538","codingScheme":"SNOMEDCT_US","tui":"T047"}]},{"begin":30,"end":38,"text":"diabetes","polarity":0,"conceptAttributes":[{"code":"73211009","cui":"C0011849","codingScheme":"SNOMEDCT_US","tui":"T047"}]},{"begin":57,"end":65,"text":"erythema","polarity":0,"conceptAttributes":[{"code":"247441003","cui":"C0041834","codingScheme":"SNOMEDCT_US","tui":"T047"},{"code":"444827008","cui":"C0041834","codingScheme":"SNOMEDCT_US","tui":"T047"},{"code":"70819003","cui":"C0041834","codingScheme":"SNOMEDCT_US","tui":"T047"},{"code":"86735004","cui":"C0041834","codingScheme":"SNOMEDCT_US","tui":"T047"}]}],"SignSymptomMention":[{"begin":44,"end":52,"text":"pruritus","polarity":0,"conceptAttributes":[{"code":"279333002","cui":"C0033774","codingScheme":"SNOMEDCT_US","tui":"T184"},{"code":"424492005","cui":"C0033774","codingScheme":"SNOMEDCT_US","tui":"T184"},{"code":"418363000","cui":"C0033774","codingScheme":"SNOMEDCT_US","tui":"T184"},{"code":"418290006","cui":"C0033774","codingScheme":"SNOMEDCT_US","tui":"T184"}]}],"DateAnnotation":[],"RouteAnnotation":[],"MeasurementAnnotation":[],"ProcedureMention":[{"begin":112,"end":126,"text":"blepharoplasty","polarity":0,"conceptAttributes":[{"code":"398248006","cui":"C0197213","codingScheme":"SNOMEDCT_US","tui":"T061"}]}],"TimeMention":[],"StrengthUnitAnnotation":[]}