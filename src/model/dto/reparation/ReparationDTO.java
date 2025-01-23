package model.dto.reparation;

import model.Tables;
import model.tables.records.ModelRecord;
import model.tables.records.ReparationRecord;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.impl.DSL;

import static model.Tables.REPARATION_DETAIL;

import java.time.LocalDate;
import java.util.List;

public class ReparationDTO {

    private Integer id;
    private LocalDate date;
    private Double price;
    private Integer idModel;
    private Integer idEngineer;

    public ReparationDTO() {}

    public ReparationDTO(ReparationRecord record)
    {
        this.id = record.getId();
        this.date = record.getDate();
        this.price = record.getPrice();
        this.idModel = record.getIdModel();
        this.idEngineer = record.getIdEngineer();
    }

    public ReparationRecord toRecord(DSLContext context)
    {
        ReparationRecord newRecord = id == null ?
                context.newRecord(Tables.REPARATION):
                context.fetchOne(Tables.REPARATION, Tables.REPARATION.ID.eq(id));

        newRecord.setDate(getDate());
        newRecord.setPrice(getPrice());
        newRecord.setIdModel(getIdModel());
        newRecord.setIdEngineer(getIdEngineer());
        return newRecord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getIdModel() {
        return idModel;
    }

    public void setIdModel(Integer idModel) {
        this.idModel = idModel;
    }

    public Integer getIdEngineer() {
        return idEngineer;
    }

    public void setIdEngineer(Integer idEngineer) {
        this.idEngineer = idEngineer;
    }


    public static Result<Record3<ReparationRecord, ModelRecord, String>> fetchByComponentCategory(DSLContext context, Integer idComponentCategory){
        
        var result = context.select(
                Tables.REPARATION,
                Tables.MODEL,
                DSL.listAgg(Tables.COMPONENT_CATEGORY.LABEL).withinGroupOrderBy(Tables.COMPONENT_CATEGORY.LABEL)
            )
            .from(Tables.REPARATION)
            .join(Tables.MODEL)
                .on(Tables.MODEL.ID.eq(Tables.REPARATION.ID_MODEL))
            .leftJoin(Tables.REPARATION_DETAIL)
                .on(Tables.REPARATION.ID.eq(Tables.REPARATION_DETAIL.ID_REPARATION));
                
        if( idComponentCategory != null )
        { result.and(Tables.REPARATION_DETAIL.ID_COMPONENT_CATEGORY.eq(idComponentCategory));  }

        return result.join(Tables.COMPONENT_CATEGORY)
            .on(Tables.COMPONENT_CATEGORY.ID.eq(Tables.REPARATION_DETAIL.ID_COMPONENT_CATEGORY))
        .groupBy(Tables.REPARATION, Tables.MODEL)
        .fetch();
    }
}