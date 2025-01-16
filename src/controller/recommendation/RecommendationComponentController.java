package controller.recommendation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import org.jooq.Result;
import org.jooq.Table;

import database.DB;
import io.javalin.http.Context;
import model.Tables;
import model.dto.recommendation.RecommendationComponentDTO;
import model.tables.records.ComponentRecord;
import model.tables.records.VLabelRecommendationComponentRecord;
import util.Renderer;

public class RecommendationComponentController {

    public static void index(Context context)
        throws ClassNotFoundException, 
                SQLException, 
                RuntimeException{
        
        Result<VLabelRecommendationComponentRecord> data = DB.handle(ctx -> {
            LocalDate dateMin = null;
            if(context.queryParamAsClass("dateStart",String.class).getOrDefault(null) != null)
            {
                dateMin = LocalDate.parse(context.queryParamAsClass("dateStart",String.class).get());
            }
            
            LocalDate dateMax = null; 
            if(context.queryParamAsClass("dateEnd",String.class).getOrDefault(null) != null)
            {
                dateMax = LocalDate.parse(context.queryParamAsClass("dateEnd",String.class).get());
            }
            return RecommendationComponentDTO.fetchByDate(ctx, dateMin, dateMax);
        });
        Renderer.usingDefault()
            .render("/recommendation/recommendation-component/index")
            .with(context, Map.of(
                "data",data,
                "active", "/recommendation/recommendation-component"
            ));
    }

    public static void create(Context context)
        throws ClassNotFoundException, 
            SQLException, 
            RuntimeException{

        Result<ComponentRecord> data = DB.handle(ctx -> {
            return ctx.fetch(Tables.COMPONENT);
        });
        
        Renderer.usingDefault()
                .render("/recommendation/recommendation-component/form")
                .with(context, Map.of(
                    "selectValues1",data,
                    "active", "/recommendation/recommendation-component"
                ));

    }

    public static void store(Context context)
        throws ClassNotFoundException, 
        SQLException, 
        RuntimeException{
            RecommendationComponentDTO recommendationComponentDTO = new RecommendationComponentDTO();
            recommendationComponentDTO.setIdComponent(Integer.parseInt(context.formParam("idComponent")));
            recommendationComponentDTO.setDateStart(LocalDate.parse(context.formParam("dateStart")));
            recommendationComponentDTO.setDateEnd(LocalDate.parse(context.formParam("dateEnd")));

            DB.handle(ctx -> {
                return recommendationComponentDTO.toRecord(ctx).store();
            });

            if(recommendationComponentDTO.getDateEnd().isBefore(recommendationComponentDTO.getDateStart()))
            {
                context.attribute("message__error", "la Date de Fin doît être après Date de Début");
                context.attribute("idComponent", recommendationComponentDTO.getIdComponent());
                context.attribute("dateStart", recommendationComponentDTO.getDateStart());
                context.attribute("dateEnd", recommendationComponentDTO.getDateEnd());
                create(context);
            }
            else
            {
                DB.handle(ctx -> {
                    return recommendationComponentDTO.toRecord(ctx).store();
                });
    
                context.redirect("/recommendation/recommendation-component");
            }
    }
    
}
