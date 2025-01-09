package model.dto;

import java.security.DrbgParameters.Reseed;
import java.time.LocalDate;
import java.util.List;

import org.jooq.DSLContext;

import model.Tables;
import model.tables.records.ReparationFeedbackRecord;
import model.tables.records.ReparationRecord;

public class ReparationFeedbackDTO {
    
    private Integer id;
    private LocalDate date;
    private Integer idReparation;

    public ReparationFeedbackDTO() {}

    public ReparationFeedbackDTO(ReparationFeedbackDTO record)
    {
        this.id = record.getId();
        this.date = record.getDate();
        this.idReparation = record.getIdReparation();
    }

    public ReparationFeedbackRecord toRecord(DSLContext context)
    {
        ReparationFeedbackRecord newRecord = id == null ?
                context.newRecord(Tables.REPARATION_FEEDBACK):
                context.fetchOne(Tables.REPARATION_FEEDBACK, Tables.REPARATION_FEEDBACK.ID.eq(id));

        newRecord.setDate(getDate());
        newRecord.setIdReparation(getIdReparation());
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

    public Integer getIdReparation() {
        return idReparation;
    }

    public void setIdReparation(Integer idReparation) {
        this.idReparation = idReparation;
    }

    public static List<ReparationDetailInfo> fetchByIdModelCategoryAndReparationDetail(DSLContext ctx, Integer idModelCategory, Integer reparationDetailIDComponentCategory) {
        return ctx.select(
                Tables.COMPONENT.SERIAL_NUMBER,
                Tables.MODEL_CATEGORY.LABEL.as("model_category_label"),
                Tables.COMPONENT_CATEGORY.LABEL.as("component_category_label"),
                Tables.REPARATION.PRICE,
                Tables.REPARATION_FEEDBACK.DATE.as("feedback_date")
            )
            .from(Tables.REPARATION)
            .join(Tables.REPARATION_DETAIL).on(Tables.REPARATION.ID.eq(Tables.REPARATION_DETAIL.ID_REPARATION))
            .join(Tables.COMPONENT).on(Tables.REPARATION_DETAIL.ID_COMPONENT_CATEGORY.eq(Tables.COMPONENT.ID_COMPONENT_CATEGORY))
            .join(Tables.MODEL).on(Tables.COMPONENT.ID_MODEL_CATEGORY.eq(Tables.MODEL.ID_MODEL_CATEGORY))
            .join(Tables.MODEL_CATEGORY).on(Tables.MODEL.ID_MODEL_CATEGORY.eq(Tables.MODEL_CATEGORY.ID))
            .join(Tables.COMPONENT_CATEGORY).on(Tables.COMPONENT.ID_COMPONENT_CATEGORY.eq(Tables.COMPONENT_CATEGORY.ID))
            .join(Tables.REPARATION_FEEDBACK).on(Tables.REPARATION.ID.eq(Tables.REPARATION_FEEDBACK.ID_REPARATION))
            .fetchInto(ReparationDetailInfo.class);
    }

    // public static List<ReparationDetailInfo> fetchByIdModelCategoryAndReparationDetail(DSLContext ctx, Integer idModelCategory, Integer reparationDetailIDComponentCategory) {
    //     return ctx.select(
    //             Tables.COMPONENT.SERIAL_NUMBER,
    //             Tables.MODEL_CATEGORY.LABEL.as("model_category_label"),
    //             Tables.COMPONENT_CATEGORY.LABEL.as("component_category_label"),
    //             Tables.REPARATION.PRICE,
    //             Tables.REPARATION_FEEDBACK.DATE.as("feedback_date")
    //         )
    //         .from(Tables.REPARATION)
    //         .join(Tables.REPARATION_DETAIL).on(Tables.REPARATION.ID.eq(Tables.REPARATION_DETAIL.ID_REPARATION))
    //         .join(Tables.COMPONENT).on(Tables.REPARATION_DETAIL.ID_COMPONENT_CATEGORY.eq(Tables.COMPONENT.ID_COMPONENT_CATEGORY))
    //         .join(Tables.MODEL).on(Tables.COMPONENT.ID_MODEL_CATEGORY.eq(Tables.MODEL.ID_MODEL_CATEGORY))
    //         .join(Tables.MODEL_CATEGORY).on(Tables.MODEL.ID_MODEL_CATEGORY.eq(Tables.MODEL_CATEGORY.ID))
    //         .join(Tables.COMPONENT_CATEGORY).on(Tables.COMPONENT.ID_COMPONENT_CATEGORY.eq(Tables.COMPONENT_CATEGORY.ID))
    //         .join(Tables.REPARATION_FEEDBACK).on(Tables.REPARATION.ID.eq(Tables.REPARATION_FEEDBACK.ID_REPARATION))
    //         .where(Tables.MODEL.ID_MODEL_CATEGORY.eq(idModelCategory))
    //         .and(Tables.REPARATION_DETAIL.ID_COMPONENT_CATEGORY.eq(reparationDetailIDComponentCategory))
    //         .fetchInto(ReparationDetailInfo.class);
    // }

    
}
