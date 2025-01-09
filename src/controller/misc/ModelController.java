package controller.misc;

import database.DB;
import io.javalin.http.Context;
import model.Tables;
import model.dto.BrandDTO;
import model.dto.ModelCategoryDTO;
import model.dto.ModelDTO;
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
        ModelDTO model = context.bodyValidator(ModelDTO.class)
            .check("serialNumber", m -> notBlank(m.getSerialNumber()), "Serial Number is required")
            .check("serialNumber", m -> unique(m.getSerialNumber(), MODEL.SERIAL_NUMBER), "Serial Number already exists")
            .check("idModelCategory", m -> exists(m.getIdModelCategory(), MODEL_CATEGORY.ID), "Model Category must exists")
            .check("idBrand", m -> exists(m.getIdBrand(), BRAND.ID), "Brand must exists")
            .check("description", m -> notBlank(m.getDescription()), "Description is required")
            .get();

        DB.handle(ctx -> {
            return model.toRecord(ctx).store();
        });
        APIResponse.success(context, 201, Map.of("message", "Component created successfuly"));
    }

    public static void update(Context context)
        throws ClassNotFoundException,
               SQLException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();

        ModelDTO model = context.bodyValidator(ModelDTO.class)
            .check("serialNumber", m -> notBlank(m.getSerialNumber()), "Serial Number is required")
            .check("serialNumber", m -> unique(m.getSerialNumber(), MODEL.SERIAL_NUMBER), "Serial Number already exists")
            .check("idModelCategory", m -> exists(m.getIdModelCategory(), MODEL_CATEGORY.ID), "Model Category must exists")
            .check("idBrand", m -> exists(m.getIdBrand(), BRAND.ID), "Brand must exists")
            .check("description", m -> notBlank(m.getDescription()), "Description is required")
            .get();
        model.setId(id);

        DB.handle(ctx -> {
            return model.toRecord(ctx).store();
        });
        APIResponse.success(context, 201, Map.of("message", "Component updated successfuly"));
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
        APIResponse.success(context, 201, Map.of("message", "Component updated successfuly"));
    }
}
