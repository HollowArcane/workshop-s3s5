package model.dto.misc;

import static model.Tables.MODEL;

import org.jooq.DSLContext;

import model.tables.records.ModelRecord;

public class ModelDTO 
{
    private Integer id;
    private String serialNumber;
    private Integer idModelCategory;
    private Integer idBrand;
    private String description;    

    public ModelDTO(ModelRecord record)
    {
        id = record.getId();
        serialNumber = record.getSerialNumber();
        idModelCategory = record.getIdModelCategory();
        idBrand = record.getIdBrand();
        description = record.getDescription();
    }

    public ModelRecord toRecord(DSLContext context)
    {
        ModelRecord newRecord = id == null ? context.newRecord(MODEL): context.fetchOne(MODEL, MODEL.ID.eq(id));
        newRecord.setSerialNumber(getSerialNumber());
        newRecord.setIdModelCategory(getIdModelCategory());
        newRecord.setIdBrand(getIdBrand());
        newRecord.setDescription(getDescription());
        return newRecord;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getIdModelCategory() {
        return idModelCategory;
    }

    public void setIdModelCategory(Integer idModelCategory) {
        this.idModelCategory = idModelCategory;
    }

    public Integer getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(Integer idBrand) {
        this.idBrand = idBrand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
