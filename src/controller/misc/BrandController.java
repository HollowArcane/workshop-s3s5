package controller.misc;

import static model.Tables.BRAND;
import static util.Validation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import database.DB;
import io.javalin.http.Context;
import model.dto.BrandDTO;
import model.tables.records.BrandRecord;
import toolkit.util.Pagination;
import util.APIResponse;
import util.Renderer;

public class BrandController
{
    public static void page(Context context)
    {
        Renderer.usingDefault()
            .render("misc/brand/index")
            .with(context, Map.of("active", "/misc/brand"));
    }

    public static void index(Context context)
        throws ClassNotFoundException,
                SQLException,
                RuntimeException
    {
        Integer page = context.queryParamAsClass("page", Integer.class).getOrDefault(1);
        
        List<BrandDTO> brands = DB.handle(ctx -> {
            return ctx.fetch(BRAND).map(BrandDTO::new);
        });

        APIResponse.success(context, 200, new Pagination<>(brands, 10).set(page));
    }

    public static void show(Context context)
        throws ClassNotFoundException,
                SQLException,
                RuntimeException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        BrandRecord brand = DB.handle(ctx -> {
            return ctx.fetchOne(BRAND, BRAND.ID.eq(id));
        });

        APIResponse.success(context, 200, brand);
    }

    public static void store(Context context)
        throws ClassNotFoundException,
                SQLException,
                RuntimeException
    {
        BrandDTO model = context.bodyValidator(BrandDTO.class)
            .check("label", m -> notBlank(m.getLabel()), "Label is required")
            .check("label", m -> unique(m.getLabel(), BRAND.LABEL), "Label already exists")
            .get();

        DB.handle(ctx -> {
            return model.toRecord(ctx).store();
        });

        APIResponse.success(context, 201, Map.entry("message", "Brand created successfuly"));
    }

    public static void update(Context context)
        throws ClassNotFoundException,
                SQLException,
                RuntimeException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();

        BrandDTO model = context.bodyValidator(BrandDTO.class)
            .check("label", m -> notBlank(m.getLabel()), "Label is required")
            .check("label", m -> unique(m.getLabel(), BRAND.LABEL), "Label already exists")
            .get();
        model.setId(id);

        DB.handle(ctx -> {
            return model.toRecord(ctx).store();
        });

        APIResponse.success(context, 201, Map.entry("message", "Brand updated successfuly"));
    }

    public static void delete(Context context)  
        throws ClassNotFoundException,
                SQLException,
                RuntimeException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        
        DB.handle(ctx -> {
            return ctx.fetchOne(BRAND, BRAND.ID.eq(id))
                        .delete();
        });

        APIResponse.success(context, 201, BRAND.ID.eq(id));
    }
}
