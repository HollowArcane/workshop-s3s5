package controller.misc;

import database.DB;
import io.javalin.http.Context;
import model.dto.misc.BrandDTO;
import model.dto.misc.ComponentCategoryDTO;
import model.tables.records.ComponentCategoryRecord;
import toolkit.util.Pagination;
import util.APIResponse;
import util.Renderer;

import static model.Tables.*;
import static util.Validation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class ComponentCategoryController
{
    public static void page(Context context)
    {   
        Renderer.usingDefault()
                .render("misc/component-category/index")
                .with(context, Map.of("active", "/misc/component-category"));
    }
    
    public static void index(Context context)   
        throws ClassNotFoundException,
               SQLException
    {
        Integer page = context.queryParamAsClass("page", Integer.class).getOrDefault(1);

        List<ComponentCategoryDTO> categories = DB.handle(ctx -> {
            return ctx.fetch(COMPONENT_CATEGORY)
                        .map(ComponentCategoryDTO::new);
        });

        APIResponse.success(context, 200, new Pagination<>(categories, 10).set(page));
    }

    public static void show(Context context)
        throws ClassNotFoundException,
               SQLException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        ComponentCategoryDTO category = DB.handle(ctx -> {
            ComponentCategoryRecord record = ctx.fetchOne(COMPONENT_CATEGORY, COMPONENT_CATEGORY.ID.eq(id));
            return new ComponentCategoryDTO(record);
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
            ComponentCategoryDTO model = new ComponentCategoryDTO();
            model.setLabel(object.getString("label"));

            DB.handle(ctx -> {
                return model.toRecord(ctx).store();
            });

            APIResponse.success(context, 201, Map.of("message", "Catégorie de Composant créée avec succès"));
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
        ComponentCategoryDTO model = new ComponentCategoryDTO();
        model.setLabel(object.getString("label"));
        model.setId(id);

        DB.handle(ctx -> {
            return model.toRecord(ctx).store();
        });

        APIResponse.success(context, 201, Map.of("message", "Catégorie de Composant modifiée avec succès"));
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
        APIResponse.success(context, 201, Map.of("message", "Catégorie de Composant supprimée avec succès"));
    }
}
