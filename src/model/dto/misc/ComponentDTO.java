package model.dto.misc;

import static model.Tables.COMPONENT;
import static model.Tables.V_LABEL_COMPONENT;
import static model.Tables.V_LABEL_TICKET_COMPONENT;

import java.time.LocalDate;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import model.Tables;
import model.dto.ticket.VLabelTicketComponentDTO;
import model.tables.records.ComponentRecord;

public class ComponentDTO {
    private Integer id;
    private String serialNumber;
    private Integer idComponentCategory;
    private Integer idModelCategory;
    private Integer idBrand;
    private String description;

    public ComponentDTO() {
    }

    public ComponentDTO(ComponentRecord record) {
        id = record.getId();
        serialNumber = record.getSerialNumber();
        idComponentCategory = record.getIdComponentCategory();
        idModelCategory = record.getIdModelCategory();
        idBrand = record.getIdBrand();
        description = record.getDescription();
    }

    public ComponentRecord toRecord(DSLContext context) {
        ComponentRecord newRecord = id == null ? context.newRecord(COMPONENT)
                : context.fetchOne(COMPONENT, COMPONENT.ID.eq(id));
        newRecord.setSerialNumber(getSerialNumber());
        newRecord.setIdComponentCategory(getIdComponentCategory());
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

    public Integer getIdComponentCategory() {
        return idComponentCategory;
    }

    public void setIdComponentCategory(Integer idComponentCategory) {
        this.idComponentCategory = idComponentCategory;
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

    public static List<VLabelTicketComponentDTO> fetchPriceMovement(DSLContext context, LocalDate dateMin, LocalDate dateMax)
    {
        var result = context.selectFrom(Tables.V_LABEL_TICKET_COMPONENT)
                            .where("1=1");
        if(dateMin!=null)
        {
            result.and(DSL.cast(Tables.V_LABEL_TICKET_COMPONENT.DATE_START, SQLDataType.LOCALDATE).greaterOrEqual(dateMin));
        }
        if(dateMax!=null)
        {
            result.and(DSL.cast(Tables.V_LABEL_TICKET_COMPONENT.DATE_START, SQLDataType.LOCALDATE).lessThan(dateMax));
        }
        return result.fetch(VLabelTicketComponentDTO::new);
    }
}
