package controller.misc;

import static model.Tables.BRAND;
import static util.Validation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import database.DB;
import io.javalin.http.Context;
import model.dto.misc.BrandDTO;
import model.dto.misc.ComponentDTO;
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
        try
        {
            JSONObject object = new JSONObject(context.body());
            BrandDTO model = new BrandDTO();
            model.setLabel(object.getString("label"));

            DB.handle(ctx -> {
                return model.toRecord(ctx).store();
            });

            APIResponse.success(context, 201, Map.of("message", "Marque créée avec succès"));
        }
        catch (Exception e)
        {
            APIResponse.error(context, 400, Map.of("message", e.getMessage()));
        }
    }

    public static void update(Context context)
        throws ClassNotFoundException,
                SQLException,
                RuntimeException
    {   
        try
        {
            Integer id = context.pathParamAsClass("id", Integer.class).get();
            
            JSONObject object = new JSONObject(context.body());
            BrandDTO model = new BrandDTO();
            model.setLabel(object.getString("label"));
            model.setId(id);

            DB.handle(ctx -> {
                return model.toRecord(ctx).store();
            });

            APIResponse.success(context, 201, Map.of("message", "Marque modifiée avec succès"));
        }
        catch (Exception e)
        {
            APIResponse.error(context, 400, Map.of("message", e.getMessage()));
        }
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

        APIResponse.success(context, 201, Map.of("message", "Marque supprimée avec succès"));
    }
}
