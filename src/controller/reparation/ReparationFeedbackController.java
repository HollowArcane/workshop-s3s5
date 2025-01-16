package controller.reparation;

import static io.javalin.apibuilder.ApiBuilder.sse;
import static model.Tables.REPARATION;
import static model.Tables.REPARATION_FEEDBACK;
import static model.Tables.V_LABEL_REPARATION;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Result;

import database.DB;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import model.Tables;
import model.dto.reparation.ReparationDetailInfo;
import model.dto.reparation.ReparationFeedbackDTO;
import model.tables.records.ComponentCategoryRecord;
import model.tables.records.ModelCategoryRecord;
import model.tables.records.ReparationFeedbackRecord;
import model.tables.records.ReparationRecord;
import model.tables.records.VLabelReparationRecord;
import util.Flashdata;
import util.FormData;
import util.Renderer;
import util.Validation;

public class ReparationFeedbackController {

    public static void loadForm(Context context)
        throws ClassNotFoundException,
                SQLException,
                RuntimeException{

            Result<VLabelReparationRecord> selectValues = DB.handle(ctx -> {
                return ctx.fetch(V_LABEL_REPARATION);
            });

            Map<String,Object> data = Map.of(
                "selectValues", selectValues,
                "active", "/reparation/reparation-feedback"
            );

            Renderer.usingDefault()
                .render("reparation/feedback/form")
                .with(context, data);    
        }

    public static void store (Context context) 
        throws ClassNotFoundException,
        SQLException,
        RuntimeException
    {
        try
        {
            ReparationFeedbackDTO model = new ReparationFeedbackDTO();

            model.setIdReparation(Validation.parse(
                Integer.class,
                context.formParam("idReparation"),
                "Réparation doit être une réparation valide"
            ));
            model.setDate(Validation.parse(
                LocalDate.class,
                context.formParam("dateReparation"),
                "Date de Réparation doît être une date valide"
            ));

            DB.handle(ctx -> {
                return model.toRecord(ctx).store();
            });

            Flashdata.set(context, "message__success", "Retour de Réparation insérée avec succès.");
            context.redirect("/reparation/feedback");
        }
        catch (Exception e)
        {
            context.attribute("message__error", e.getMessage());
            context.attribute("idReparation", context.formParam("idReparation"));
            context.attribute("dateReparation", context.formParam("dateReparation"));
            loadForm(context);
        }
    }

    public static void index(Context context)
        throws ClassNotFoundException,
                SQLException,
                RuntimeException{

            Integer idModelCategory = context.queryParamAsClass("idModelCategory", Integer.class).getOrDefault(null);
            Integer idComponentCategory = context.queryParamAsClass("idComponentCategory", Integer.class).getOrDefault(null);
            
            Map<String,Object> data =  DB.handle(ctx -> {
                return Map.of("data", ReparationFeedbackDTO.fetchByIdModelCategoryAndReparationDetail(ctx, idModelCategory,idComponentCategory),
                                "selectValues1", ctx.fetch(Tables.MODEL_CATEGORY),
                                "selectValues2", ctx.fetch(Tables.COMPONENT_CATEGORY),
                                "active", "/reparation/reparation-feedback"
                                );
            });

            Renderer.usingDefault()
                .render("reparation/feedback/index")
                .with(context,data);
        }
    
}
