package model.dto.recommendation;

import static model.Tables.BRAND;

import java.time.LocalDate;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.DSL;

import model.Tables;
import model.tables.records.RecommendationComponentRecord;
import model.tables.records.VLabelRecommendationComponentRecord;

public class RecommendationComponentDTO {

    private Integer id;
    private Integer idComponent;
    private LocalDate dateStart;
    private LocalDate dateEnd;

    public RecommendationComponentDTO(){}

    public RecommendationComponentDTO(RecommendationComponentRecord record)
    {
        this.id = record.getId();
        this.idComponent = record.getIdComponent();
        this.dateStart = record.getDateStart();
        this.dateEnd = record.getDateEnd();
    }

    public RecommendationComponentRecord toRecord(DSLContext context)
    {
        RecommendationComponentRecord newRecord = id == null ?
                context.newRecord(Tables.RECOMMENDATION_COMPONENT):
                context.fetchOne(Tables.RECOMMENDATION_COMPONENT, Tables.RECOMMENDATION_COMPONENT.ID.eq(id));
        newRecord.setIdComponent(getIdComponent());        
        newRecord.setDateStart(getDateStart());        
        newRecord.setDateEnd(getDateEnd());        
        return newRecord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdComponent() {
        return idComponent;
    }

    public void setIdComponent(Integer idComponent) {
        this.idComponent = idComponent;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }


    public static Result<VLabelRecommendationComponentRecord> fetchByDate(DSLContext context, LocalDate min, LocalDate max)
    {
        var result = context.selectFrom(Tables.V_LABEL_RECOMMENDATION_COMPONENT).where("1=1");
        if( min != null )
        {
            result.and(Tables.V_LABEL_RECOMMENDATION_COMPONENT.DATE_END.greaterOrEqual(min));
        }
        if( max != null )
        {
            result.and(Tables.V_LABEL_RECOMMENDATION_COMPONENT.DATE_START.lessThan(max));
        }
        return result.fetch();
    }

    public static Result<VLabelRecommendationComponentRecord> fetchByDate(DSLContext context, int year)
    { return fetchByDate(context, LocalDate.parse(year + "-01-01"), LocalDate.parse((year + 1) + "-01-01")); }
}
