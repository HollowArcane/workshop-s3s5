package model.dto;

public class ReparationDetailInfo {
    
    private String serialNumber;
    private String modelCategoryLabel;
    private String componentCategoryLabel;
    private Float price;
    private String feedbackDate;

    public String getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    public String getModelCategoryLabel() {
        return modelCategoryLabel;
    }
    public void setModelCategoryLabel(String modelCategoryLabel) {
        this.modelCategoryLabel = modelCategoryLabel;
    }
    public String getComponentCategoryLabel() {
        return componentCategoryLabel;
    }
    public void setComponentCategoryLabel(String componentCategoryLabel) {
        this.componentCategoryLabel = componentCategoryLabel;
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
    public String getFeedbackDate() {
        return feedbackDate;
    }
    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    // Getters et setters
}
