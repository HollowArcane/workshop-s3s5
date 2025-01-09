package controller.reparation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jooq.Result;

import database.DB;
import io.javalin.http.Context;
import model.Tables;
import model.dto.ReparationDetailInfo;
import model.dto.ReparationFeedbackDTO;
import model.tables.records.ComponentCategoryRecord;
import model.tables.records.ModelCategoryRecord;
import model.tables.records.ReparationRecord;
import util.Renderer;

public class ReparationFeedbackController {

    public static void loadForm(Context context)
        throws ClassNotFoundException,
                SQLException,
                RuntimeException{

            Result<ReparationRecord> selectValues = DB.handle(ctx -> {
                return ctx.fetch(Tables.REPARATION);
            });

            Map<String,Object> data = Map.of("selectValues",selectValues);

            Renderer.usingDefault()
                .render("reparation/feedback/form")
                .with(context,data);    
        }

    public static void index(Context context)
        throws ClassNotFoundException,
                SQLException,
                RuntimeException{

            Integer idModelCategory = context.queryParamAsClass("idModelCategory", Integer.class).getOrDefault(null);
            Integer idComponentCategory = context.queryParamAsClass("idComponentCategory", Integer.class).getOrDefault(null);
            
            List<ReparationDetailInfo> listReparationDetailInfos = DB.handle(ctx -> {
                return ReparationFeedbackDTO.fetchByIdModelCategoryAndReparationDetail(ctx, idModelCategory,idComponentCategory);
            });

            Result<ModelCategoryRecord> selectValues1 = DB.handle(ctx -> {
                return ctx.fetch(Tables.MODEL_CATEGORY);
            });

            Result<ComponentCategoryRecord> selectValues2 = DB.handle(ctx -> {
                return ctx.fetch(Tables.COMPONENT_CATEGORY);
            });

            Map<String,Object> data = Map.of("data",listReparationDetailInfos,
                                                "selectValues1",selectValues1,
                                                "selectValues2",selectValues2);

            Renderer.usingDefault()
                .render("reparation/feedback/index")
                .with(context,data);

        }
    
}
