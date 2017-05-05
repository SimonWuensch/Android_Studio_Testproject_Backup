package ssi.ssn.com.ssi_service.model.network.response.kpi.definitions;

public class ResponseKpiDefinition {

    private String key;
    private String provider;
    private String type;
    private String minimalGranularity;
    private String aggregationStrategy;
    private String location;
    private String name;
    private String units;
    private String description;
    private Object evaluationScript;
    private int removeAfter;
    private String[] spectrumCategories;

    public ResponseKpiDefinition() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMinimalGranularity() {
        return minimalGranularity;
    }

    public void setMinimalGranularity(String minimalGranularity) {
        this.minimalGranularity = minimalGranularity;
    }

    public String getAggregationStrategy() {
        return aggregationStrategy;
    }

    public void setAggregationStrategy(String aggregationStrategy) {
        this.aggregationStrategy = aggregationStrategy;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getEvaluationScript() {
        return evaluationScript;
    }

    public void setEvaluationScript(Object evaluationScript) {
        this.evaluationScript = evaluationScript;
    }

    public int getRemoveAfter() {
        return removeAfter;
    }

    public void setRemoveAfter(int removeAfter) {
        this.removeAfter = removeAfter;
    }

    public String[] getSpectrumCategories() {
        return spectrumCategories;
    }

    public void setSpectrumCategories(String[] spectrumCategories) {
        this.spectrumCategories = spectrumCategories;
    }
}
