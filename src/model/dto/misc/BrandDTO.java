package model.dto.misc;

import static model.Tables.BRAND;

import org.jooq.DSLContext;

import model.tables.records.BrandRecord;

public class BrandDTO
{
    private Integer id;
    private String label;

    public BrandDTO() {}

    public BrandDTO(BrandRecord record)
    {
        this.id = record.getId();
        this.label = record.getLabel();
    }

    public BrandRecord toRecord(DSLContext context)
    {
        BrandRecord newRecord = id == null ?
                    context.newRecord(BRAND):
                    context.fetchOne(BRAND, BRAND.ID.eq(id));
        
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
