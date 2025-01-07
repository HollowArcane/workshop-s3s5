package controller.reparation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import database.DB;
import io.javalin.http.Context;
import model.Tables;
import model.dto.ReparationDTO;

public class ReparationController {

    public static void index(Context context) 
        throws ClassNotFoundException, 
                SQLException, 
                RuntimeException{

        Integer idComponentCategory = context.queryParamAsClass("idComponentCategory", Integer.class).getOrDefault(null);
        
        DB.handle(ctx -> {
            List<ReparationDTO> data = ReparationDTO.fetchByComponentCategory(ctx, idComponentCategory);
            context.render("/layouts/main", Map.ofEntries(
                Map.entry("page", "/pages/reparation/reparation/index"),
                Map.entry("data", data),
                Map.entry("selectValues", ctx.fetch(Tables.COMPONENT_CATEGORY))
            ));
            return null;
        });

    }
    
}
