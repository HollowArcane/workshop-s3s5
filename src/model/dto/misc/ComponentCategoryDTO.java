package model.dto.misc;

import static model.Tables.COMPONENT_CATEGORY;

import org.jooq.DSLContext;

import model.tables.records.ComponentCategoryRecord;

public class ComponentCategoryDTO
{
    private Integer id;
    private String label;

    public ComponentCategoryDTO() {}

    public ComponentCategoryDTO(ComponentCategoryRecord record)
    {
        this.id = record.getId();
        this.label = record.getLabel();
    }

    public ComponentCategoryRecord toRecord(DSLContext context)
    {
        ComponentCategoryRecord newRecord = id == null ?
                    context.newRecord(COMPONENT_CATEGORY):
                    context.fetchOne(COMPONENT_CATEGORY, COMPONENT_CATEGORY.ID.eq(id));
        
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
