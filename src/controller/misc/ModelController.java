package controller.misc;

import database.DB;
import io.javalin.http.Context;
import model.Tables;
import model.dto.misc.BrandDTO;
import model.dto.misc.ModelCategoryDTO;
import model.dto.misc.ModelDTO;
import model.tables.records.BrandRecord;
import model.tables.records.ModelRecord;
import model.tables.records.ModelCategoryRecord;
import toolkit.util.Pagination;
import util.APIResponse;
import util.Data;
import util.Renderer;

import static model.Tables.*;
import static util.Validation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ModelController 
{
    public static void page(Context context) 
        throws ClassNotFoundException,
            SQLException,
            RuntimeException
    {
        Map<String, Object> data = DB.handle(ctx -> {
            return Map.ofEntries(
              Map.entry("active", "/misc/model"),
              Map.entry("modelCategories", Data.asMap(
                ctx.fetch(MODEL_CATEGORY), 
                ModelCategoryRecord::getId,
                ModelCategoryRecord::getLabel)),
              Map.entry("brands", Data.asMap(
                ctx.fetch(BRAND),
                BrandRecord::getId,
                BrandRecord::getLabel))  
            );
        });
        Renderer.usingDefault()
            .render("misc/model/index")
            .with(context,data);
    }    

    public static void index(Context context)
        throws ClassNotFoundException,
            SQLException
    {
        Integer page = context.queryParamAsClass("page", Integer.class).getOrDefault(1);
        
        Map<String, Object> data = DB.handle(ctx -> {
            return Map.ofEntries(
                Map.entry("content", new Pagination<>(ctx.fetch(MODEL).map(ModelDTO::new), 10).set(page)),
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
        ModelDTO category = DB.handle(ctx -> {
            ModelRecord record = ctx.fetchOne(MODEL, MODEL.ID.eq(id));
            return new ModelDTO(record);
        });
        APIResponse.success(context, 200, category);
    }

    public static void store(Context context)
        throws ClassNotFoundException,
               SQLException
    {
        
    }

    public static void update(Context context)
        throws ClassNotFoundException,
               SQLException
    {
        
    }

    public static void delete(Context context)
        throws ClassNotFoundException,
               SQLException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        DB.handle(ctx ->
            ctx.fetchOne(MODEL_CATEGORY, MODEL_CATEGORY.ID.eq(id))
               .delete()
        );
        APIResponse.success(context, 201, Map.of("message", "Model supprimée successfuly"));
    }
}
