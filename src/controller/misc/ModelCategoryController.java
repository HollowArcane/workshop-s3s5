package controller.misc;

import static model.Tables.BRAND;
import static model.Tables.MODEL_CATEGORY;
import static util.Validation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import database.DB;
import io.javalin.http.Context;
import model.dto.BrandDTO;
import model.dto.ModelCategoryDTO;
import model.tables.records.BrandRecord;
import model.tables.records.ModelCategoryRecord;
import toolkit.util.Pagination;
import util.APIResponse;
import util.Renderer;

public class ModelCategoryController
{
    public static void page(Context context)
    {
        Renderer.usingDefault()
            .render("misc/model-category/index")
            .with(context, Map.of("active", "/misc/model-category"));
    }

    public static void index(Context context)
        throws ClassNotFoundException,
                SQLException,
                RuntimeException
    {
        Integer page = context.queryParamAsClass("page", Integer.class).getOrDefault(1);
        
        List<ModelCategoryDTO> brands = DB.handle(ctx -> {
            return ctx.fetch(MODEL_CATEGORY).map(ModelCategoryDTO::new);
        });

        APIResponse.success(context, 200, new Pagination<>(brands, 10).set(page));
    }

    public static void show(Context context)
        throws ClassNotFoundException,
                SQLException,
                RuntimeException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        ModelCategoryRecord brand = DB.handle(ctx -> {
            return ctx.fetchOne(MODEL_CATEGORY, MODEL_CATEGORY.ID.eq(id));
        });

        APIResponse.success(context, 200, brand);
    }

    public static void store(Context context)
        throws ClassNotFoundException,
                SQLException,
                RuntimeException
    {
        ModelCategoryDTO model = context.bodyValidator(ModelCategoryDTO.class)
            .check("label", m -> notBlank(m.getLabel()), "Label is required")
            .check("label", m -> unique(m.getLabel(), MODEL_CATEGORY.LABEL), "Label already exists")
            .get();

        DB.handle(ctx -> {
            return model.toRecord(ctx).store();
        });

        APIResponse.success(context, 201, Map.entry("message", "Model category created successfuly"));
    }

    public static void update(Context context)
        throws ClassNotFoundException,
                SQLException,
                RuntimeException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();

        ModelCategoryDTO model = context.bodyValidator(ModelCategoryDTO.class)
            .check("label", m -> notBlank(m.getLabel()), "Label is required")
            .check("label", m -> unique(m.getLabel(), MODEL_CATEGORY.LABEL), "Label already exists")
            .get();
        model.setId(id);

        DB.handle(ctx -> {
            return model.toRecord(ctx).store();
        });

        APIResponse.success(context, 201, Map.entry("message", "Model category updated successfuly"));
    }

    public static void delete(Context context)  
        throws ClassNotFoundException,
                SQLException,
                RuntimeException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        
        DB.handle(ctx -> {
            return ctx.fetchOne(MODEL_CATEGORY, MODEL_CATEGORY.ID.eq(id))
                        .delete();
        });

        APIResponse.success(context, 201, MODEL_CATEGORY.ID.eq(id));
    }
}
