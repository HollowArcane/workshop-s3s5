package model.dto;

import static model.Tables.MODEL_CATEGORY;

import org.jooq.DSLContext;

import model.tables.records.ModelCategoryRecord;

public class ModelCategoryDTO
{
    private Integer id;
    private String label;

    public ModelCategoryDTO() {}

    public ModelCategoryDTO(ModelCategoryRecord record)
    {
        this.id = record.getId();
        this.label = record.getLabel();
    }

    public ModelCategoryRecord toRecord(DSLContext context)
    {
        ModelCategoryRecord newRecord = id == null ?
                    context.newRecord(MODEL_CATEGORY):
                    context.fetchOne(MODEL_CATEGORY, MODEL_CATEGORY.ID.eq(id));
        
        newRecord.setLabel(getLabel());
        return newRecord;
    }

    public Integer getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    } 
}
