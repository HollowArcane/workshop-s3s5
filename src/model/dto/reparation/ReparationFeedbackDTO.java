package model.dto.reparation;

import java.security.DrbgParameters.Reseed;
import java.time.LocalDate;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.util.xml.jaxb.Table;

import model.Tables;
import model.tables.records.ReparationFeedbackRecord;
import model.tables.records.ReparationRecord;

public class ReparationFeedbackDTO {

    private Integer id;
    private LocalDate date;
    private Integer idCustomer;
    private Integer idReparation;

    public ReparationFeedbackDTO() {
    }

    public ReparationFeedbackDTO(ReparationFeedbackDTO record) {
        this.id = record.getId();
        this.date = record.getDate();
        this.idCustomer = record.getIdCustomer();
        this.idReparation = record.getIdReparation();
    }

    public ReparationFeedbackRecord toRecord(DSLContext context) {
        ReparationFeedbackRecord newRecord = id == null ? 
                context.newRecord(Tables.REPARATION_FEEDBACK):
                context.fetchOne(Tables.REPARATION_FEEDBACK, Tables.REPARATION_FEEDBACK.ID.eq(id));

        newRecord.setDate(getDate());
        newRecord.setIdCustomer(getIdCustomer());;
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

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Integer getIdReparation() {
        return idReparation;
    }

    public void setIdReparation(Integer idReparation) {
        this.idReparation = idReparation;
    }

    public static List<ReparationDetailInfo> fetchByIdModelCategoryAndReparationDetail(DSLContext ctx,
            Integer idModelCategory, Integer reparationDetailIDComponentCategory) {
        var result = ctx.select(
                Tables.MODEL.SERIAL_NUMBER,
                Tables.MODEL_CATEGORY.LABEL.as("model_category_label"),
                Tables.COMPONENT_CATEGORY.LABEL.as("component_category_label"),
                Tables.REPARATION.PRICE,
                Tables.CUSTOMER.NAME,
                Tables.REPARATION_FEEDBACK.DATE.as("feedback_date"))
                .from(Tables.REPARATION)
                .join(Tables.REPARATION_DETAIL)
                    .on(Tables.REPARATION.ID.eq(Tables.REPARATION_DETAIL.ID_REPARATION))
                .join(Tables.COMPONENT)
                    .on(Tables.REPARATION_DETAIL.ID_COMPONENT_CATEGORY.eq(Tables.COMPONENT.ID_COMPONENT_CATEGORY))
                .join(Tables.MODEL)
                    .on(Tables.COMPONENT.ID_MODEL_CATEGORY.eq(Tables.MODEL.ID_MODEL_CATEGORY))
                .join(Tables.MODEL_CATEGORY)
                    .on(Tables.MODEL.ID_MODEL_CATEGORY.eq(Tables.MODEL_CATEGORY.ID))
                .join(Tables.COMPONENT_CATEGORY)
                    .on(Tables.COMPONENT.ID_COMPONENT_CATEGORY.eq(Tables.COMPONENT_CATEGORY.ID))
                .join(Tables.REPARATION_FEEDBACK)
                    .on(Tables.REPARATION.ID.eq(Tables.REPARATION_FEEDBACK.ID_REPARATION))
                .join(Tables.CUSTOMER)
                    .on(Tables.REPARATION_FEEDBACK.ID_CUSTOMER.eq(Tables.CUSTOMER.ID))
                .where("1=1");

        if (idModelCategory != null) {
            result.and(Tables.MODEL.ID_MODEL_CATEGORY.eq(idModelCategory));
        }

        if (reparationDetailIDComponentCategory != null) {
            result.and(Tables.REPARATION_DETAIL.ID_COMPONENT_CATEGORY.eq(reparationDetailIDComponentCategory));
        }

        return result.fetchInto(ReparationDetailInfo.class);
    }

}
