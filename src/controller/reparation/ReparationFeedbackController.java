package controller.reparation;

import static model.Tables.REPARATION_FEEDBACK;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jooq.Result;

import database.DB;
import io.javalin.http.Context;
import model.Tables;
import model.dto.ReparationDetailInfo;
import model.dto.ReparationFeedbackDTO;
import model.tables.records.ComponentCategoryRecord;
import model.tables.records.ModelCategoryRecord;
import model.tables.records.ReparationFeedbackRecord;
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

    public static void store (Context context) 
        throws ClassNotFoundException,
        SQLException,
        RuntimeException{
            Integer idReparation = context.formParamAsClass("idReparation", Integer.class).getOrDefault(null);
            LocalDate dateReparation = context.formParamAsClass("dateReparation", LocalDate.class).getOrDefault(null);
            
            DB.handle(ctx -> {
                ReparationFeedbackRecord newRecord = ctx.newRecord(REPARATION_FEEDBACK);
                newRecord.setIdReparation(idReparation);
                newRecord.setDate(dateReparation);
                return newRecord.store();
            });

            context.redirect("/reparation/feedback");

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
