package controller.reparation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jooq.Record3;

import database.DB;
import io.javalin.http.Context;
import model.Tables;
import model.dto.ReparationDTO;
import model.tables.records.ModelRecord;
import model.tables.records.ReparationRecord;
import toolkit.util.Pagination;

public class ReparationController {

    public static void index(Context context) 
        throws ClassNotFoundException, 
                SQLException, 
                RuntimeException{

        Integer idComponentCategory = context.queryParamAsClass("idComponentCategory", Integer.class).getOrDefault(null);
        Integer page = context.queryParamAsClass("page", Integer.class).getOrDefault(1);

        DB.handle(ctx -> {
            List<Record3<ReparationRecord, ModelRecord, String>> data = ReparationDTO.fetchByComponentCategory(ctx, idComponentCategory);
            Pagination<Record3<ReparationRecord, ModelRecord, String>> pagination = new Pagination<>(data, 10).set(page);

            context.render("/layouts/main", Map.ofEntries(
                Map.entry("active", "/reparation/reparation"),
                Map.entry("page", "/pages/reparation/reparation/index"),
                Map.entry("data", pagination.getPage()),
                Map.entry("pagination", pagination.spread(2, 1, "...")),
                Map.entry("paginationPage", context.queryParamAsClass("page", String.class).getOrDefault("1")),
                Map.entry("selectValues", ctx.fetch(Tables.COMPONENT_CATEGORY))
            ));
            return null;
        });

    }
    
}
