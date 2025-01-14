package controller.misc;

import database.DB;
import io.javalin.http.Context;
import model.dto.misc.BrandDTO;
import model.dto.misc.ComponentCategoryDTO;
import model.dto.misc.ComponentDTO;
import model.dto.misc.ModelCategoryDTO;
import model.tables.records.BrandRecord;
import model.tables.records.ComponentCategoryRecord;
import model.tables.records.ComponentRecord;
import model.tables.records.ModelCategoryRecord;
import toolkit.util.Pagination;
import util.APIResponse;
import util.Data;
import util.Renderer;

import org.json.*;

import static model.Tables.*;
import static util.Validation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ComponentController
{
    public static void page(Context context)
        throws ClassNotFoundException,
            SQLException,
            RuntimeException
    {   
        Map<String, Object> data = DB.handle(ctx -> {
            return Map.ofEntries(
                Map.entry("active", "/misc/component"),
                Map.entry("componentCategories", Data.asMap(
                    ctx.fetch(COMPONENT_CATEGORY),
                    ComponentCategoryRecord::getId,
                    ComponentCategoryRecord::getLabel
                )),
                Map.entry("modelCategories", Data.asMap(
                    ctx.fetch(MODEL_CATEGORY),
                    ModelCategoryRecord::getId,
                    ModelCategoryRecord::getLabel)),
                Map.entry("brands", Data.asMap(
                    ctx.fetch(BRAND),
                    BrandRecord::getId,
                    BrandRecord::getLabel)
                )
            );
        });
        Renderer.usingDefault()
            .render("misc/component/index")
            .with(context, data);
    }
    
    public static void index(Context context)   
        throws ClassNotFoundException,
               SQLException
    {
        Integer page = context.queryParamAsClass("page", Integer.class).getOrDefault(1);
        
        Map<String, Object> data = DB.handle(ctx -> {
            return Map.ofEntries(
                Map.entry("content", new Pagination<>(ctx.fetch(COMPONENT).map(ComponentDTO::new), 10).set(page)),
                Map.entry("componentCategories", Data.asMap(
                    ctx.fetch(COMPONENT_CATEGORY),
                    ComponentCategoryRecord::getId,
                    ComponentCategoryRecord::getLabel
                )),
                Map.entry("modelCategories", Data.asMap(
                    ctx.fetch(MODEL_CATEGORY),
                    ModelCategoryRecord::getId,
                    ModelCategoryRecord::getLabel)),
                Map.entry("brands", Data.asMap(
                    ctx.fetch(BRAND),
                    BrandRecord::getId,
                    BrandRecord::getLabel)
                )
            );
        });

        APIResponse.success(context, 200, data);
    }

    public static void show(Context context)
        throws ClassNotFoundException,
               SQLException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        ComponentDTO category = DB.handle(ctx -> {
            ComponentRecord record = ctx.fetchOne(COMPONENT, COMPONENT.ID.eq(id));
            return new ComponentDTO(record);
        });
        APIResponse.success(context, 200, category);
    }

    public static void store(Context context)
        throws ClassNotFoundException,
               SQLException
    {
        try
        {
            JSONObject object = new JSONObject(context.body());
            ComponentDTO model = new ComponentDTO();
            model.setSerialNumber(object.getString("serialNumber"));
            model.setIdComponentCategory(object.getInt("idComponentCategory"));
            model.setIdModelCategory(object.getInt("idModelCategory"));
            model.setIdBrand(object.getInt("idBrand"));
            model.setDescription(object.getString("description"));

            DB.handle(ctx -> {
                return model.toRecord(ctx).store();
            });

            APIResponse.success(context, 201, Map.of("message", "Composant créée avec succès"));
        }
        catch (Exception e)
        {
            APIResponse.error(context, 400, Map.of("message", e.getMessage()));
        }
    }

    public static void update(Context context)
        throws ClassNotFoundException,
               SQLException
    {
        try
        {
            Integer id = context.pathParamAsClass("id", Integer.class).get();
            JSONObject object = new JSONObject(context.body());
            ComponentDTO model = new ComponentDTO();
            model.setSerialNumber(object.getString("serialNumber"));
            model.setIdComponentCategory(object.getInt("idComponentCategory"));
            model.setIdModelCategory(object.getInt("idModelCategory"));
            model.setIdBrand(object.getInt("idBrand"));
            model.setDescription(object.getString("description"));
            model.setId(id);

            DB.handle(ctx -> {
                return model.toRecord(ctx).store();
            });

            APIResponse.success(context, 201, Map.of("message", "Composant modifiée avec succès"));
        }
        catch (Exception e)
        {
            APIResponse.error(context, 400, Map.of("message", e.getMessage()));
        }
    }

    public static void delete(Context context)
        throws ClassNotFoundException,
               SQLException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        DB.handle(ctx ->
            ctx.fetchOne(COMPONENT_CATEGORY, COMPONENT_CATEGORY.ID.eq(id))
               .delete()
        );
        APIResponse.success(context, 201, Map.of("message", "Composant supprimée avec succès"));
    }
}
