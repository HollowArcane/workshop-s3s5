package controller.recommendation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import org.jooq.Result;

import database.DB;
import io.javalin.http.Context;
import model.dto.recommendation.RecommendationComponentDTO;
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
            .with(context, Map.of("data",data));
    }
    
}
