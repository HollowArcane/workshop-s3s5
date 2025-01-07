package model.dto;

import model.Tables;

import model.tables.records.ReparationRecord;
import org.jooq.DSLContext;

import java.time.LocalDate;

public class ReparationDTO {

    private Integer id;
    private LocalDate date;
    private Double price;
    private Integer idModel;

    public ReparationDTO() {}

    public ReparationDTO(ReparationRecord record)
    {
        this.id = record.getId();
        this.date = record.getDate();
        this.price = record.getPrice();
        this.idModel = record.getIdModel();
    }

    public ReparationRecord toRecord(DSLContext context)
    {
        ReparationRecord newRecord = id == null ?
                context.newRecord(Tables.REPARATION):
                context.fetchOne(Tables.REPARATION, Tables.REPARATION.ID.eq(id));

        newRecord.setDate(getDate());
        newRecord.setPrice(getPrice());
        newRecord.setIdModel(getIdModel());
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


    public void fetchByModelCategory(DSLContext context, Integer idModelCategory){
        context
                .select(REPARATION)
    }
}