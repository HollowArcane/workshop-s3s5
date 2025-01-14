package controller.recommendation;

import java.sql.SQLException;

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
            return RecommendationComponentDTO.fetchByDate(ctx, null, null);
        });
        Renderer.usingDefault().render("/recommendation/recommendation-component/index");
    }
    
}
