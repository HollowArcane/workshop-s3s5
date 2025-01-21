package controller.reparation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.impl.TableRecordImpl;

import database.DB;
import io.javalin.http.Context;
import model.Tables;
import model.dto.reparation.ReparationDTO;
import model.dto.reparation.ReparationFeedbackDTO;
import model.tables.records.ModelRecord;
import model.tables.records.ReparationRecord;
import toolkit.util.Pagination;
import util.Flashdata;
import util.Renderer;
import util.Validation;

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

    public static void loadForm(Context context)
        throws ClassNotFoundException, 
        SQLException, 
        RuntimeException{
            
            Map<String,Object> dataForm = DB.handle(ctx -> {
                return Map.of(
                    "selectModels",ctx.fetch(Tables.V_LABEL_MODEL),
                    "engineer",ctx.fetch(Tables.ENGINEER));
            });

            Renderer.usingDefault().render("reparation/reparation/form").with(context,dataForm);;
    }

    public static void store (Context context) 
        throws ClassNotFoundException,
        SQLException,
        RuntimeException
    {
        try
        {
            ReparationDTO model = new ReparationDTO();

            model.setPrice(Validation.parse(
                Double.class,
                context.formParam("price"),
                "Prix doit être un prix valide"
            ));
            model.setIdModel(Validation.parse(
                Integer.class,
                context.formParam("idModel"),
                "Modèle doit être un modèle valide"));
            model.setDate(Validation.parse(
                LocalDate.class,
                context.formParam("date"),
                "Date de Réparation doît être une date valide"
            ));
            model.setIdEngineer(Validation.parse(
                Integer.class,
                context.formParam("idEngineer"),
                "Technicien doit être un Technicien valide"));

            DB.handle(ctx -> {
                return model.toRecord(ctx).store();
            });

            Flashdata.set(context, "message__success", "Retour de Réparation insérée avec succès.");
            context.redirect("/reparation/feedback");
        }
        catch (Exception e)
        {
            context.attribute("message__error", e.getMessage());
            context.attribute("date", context.formParam("date"));
            context.attribute("price", context.formParam("price"));
            context.attribute("idModel", context.formParam("idModel"));
            context.attribute("idEngineer", context.formParam("idEngineer"));
            loadForm(context);
        }
    }
}
