package controller.reparation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import database.DB;
import io.javalin.http.Context;
import model.dto.ReparationDTO;

public class ReparationController {

    public static void index(Context context) 
        throws ClassNotFoundException, 
                SQLException, 
                RuntimeException{

        Integer idModelCategory = context.queryParamAsClass("idModelCategory", Integer.class).getOrDefault(null);
        
        List<ReparationDTO> data = DB.handle(ctx -> {
            return ReparationDTO.fetchByModelCategory(ctx, idModelCategory);
        });

        context.render("/layouts/main", Map.ofEntries(
            Map.entry("page", "/pages/reparation/reparation/index"),
            Map.entry("data", data)
        ));
    }
    
}
