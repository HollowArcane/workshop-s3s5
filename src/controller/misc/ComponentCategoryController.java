package controller.misc;

import database.DB;
import io.javalin.http.Context;
import model.tables.records.ComponentCategoryRecord;
import toolkit.util.Pagination;
import util.APIResponse;
import util.Renderer;
import util.Validation;

import static model.Tables.*;

import java.sql.SQLException;
import java.util.Map;

import org.jooq.Result;

public class ComponentCategoryController
{
    public static void page(Context context)
    {   
        Renderer.usingDefault()
                .render("component-category/index")
                .with(context, Map.of("active", "/misc/component-category"));
    }
    
    public static void index(Context context)   
        throws ClassNotFoundException,
               SQLException
    {
        Integer page = context.queryParamAsClass("page", Integer.class).get();
        Result<ComponentCategoryRecord> categories = DB.handle(ctx -> ctx.fetch(COMPONENT_CATEGORY));

        APIResponse.success(context, 200, new Pagination<>(categories, 10).set(page));
    }

    public static void show(Context context)
        throws ClassNotFoundException,
               SQLException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        ComponentCategoryRecord category = DB.handle(ctx -> 
            ctx.fetchOne(COMPONENT_CATEGORY, COMPONENT_CATEGORY.ID.eq(id))
        );
        APIResponse.success(context, 200, category);
    }

    public static void create(Context context)
        throws ClassNotFoundException,
               SQLException
    {
        ComponentCategoryRecord category = context.bodyValidator(ComponentCategoryRecord.class)
            // .check("label", obj -> Validation.notBlank(obj.getLabel()), "Label is required")
            // .check("label", obj -> Validation.unique(obj.getLabel(), COMPONENT_CATEGORY.LABEL), "Label already exists")
            .get();

        DB.handle(ctx ->
            ctx.insertInto(COMPONENT_CATEGORY)
                .set(category)
                .execute()
        );
    }

    public static void update(Context context)
        throws ClassNotFoundException,
               SQLException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        ComponentCategoryRecord category = context.bodyValidator(ComponentCategoryRecord.class)
            // .check("label", obj -> Validation.notBlank(obj.getLabel()), "Label is required")
            // .check("label", obj -> Validation.unique(obj.getLabel(), COMPONENT_CATEGORY.LABEL), "Label already exists")
            .get();
        category.setId(id);

        DB.handle(ctx ->
            ctx.update(COMPONENT_CATEGORY)
                .set(category)
                .where(COMPONENT_CATEGORY.ID.eq(id))
                .execute()
        );
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
    }
}
