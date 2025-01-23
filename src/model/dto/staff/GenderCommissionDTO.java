package model.dto.staff;

import java.math.BigDecimal;

public class GenderCommissionDTO {

    Integer idGender;
    String Gender;
    BigDecimal commission;

    public GenderCommissionDTO() {}

    public Integer getIdGender() {
        return idGender;
    }
    public void setIdGender(Integer idGender) {
        this.idGender = idGender;
    }
    public String getGender() {
        return Gender;
    }
    public void setGender(String gender) {
        Gender = gender;
    }
    public BigDecimal getCommission() {
        return commission;
    }
    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }
    
}
