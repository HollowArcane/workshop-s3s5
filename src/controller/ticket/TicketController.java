package controller.ticket;

import static model.Tables.COMPONENT_CATEGORY;
import static model.Tables.CUSTOMER;
import static model.Tables.ENGINEER;
import static model.Tables.MODEL;
import static model.Tables.TICKET;
import static model.Tables.V_LABEL_MODEL;
import static model.Tables.V_LABEL_TICKET;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import database.DB;
import io.javalin.http.Context;
import util.APIResponse;
import util.Data;
import util.Renderer;
import model.dto.misc.ComponentCategoryDTO;
import model.dto.staff.EngineerDTO;
import model.dto.ticket.TicketDTO;
import model.dto.ticket.VLabelTicketDTO;
import model.tables.records.ComponentCategoryRecord;
import model.tables.records.CustomerRecord;
import model.tables.records.EngineerRecord;
import model.tables.records.ModelRecord;
import model.tables.records.VLabelModelRecord;
import toolkit.util.Pagination;

public class TicketController
{
    public static void page(Context context)
        throws ClassNotFoundException,
                SQLException
    {
        var data = DB.handle(ctx -> {
            Map<Integer, String> categories = Data.asMap(ctx.fetch(COMPONENT_CATEGORY), ComponentCategoryRecord::getId, ComponentCategoryRecord::getLabel);
            categories.put(null, "Tous");
            
            return Map.of(
                "active", "/ticket/ticket",
                "customers", Data.asMap(ctx.fetch(CUSTOMER), CustomerRecord::getId, CustomerRecord::getName),
                "models", Data.asMap(ctx.fetch(V_LABEL_MODEL), VLabelModelRecord::getId, m -> m.getSerialNumber() + ": " + m.getModelCategory()),
                "engineers", Data.asMap(ctx.fetch(ENGINEER), EngineerRecord::getId, EngineerRecord::getName),
                "componentCategories", categories
            );
    });

        Renderer.usingDefault()
            .render("ticket/ticket/index")
            .with(context, data);
    }

    public static void index(Context context)
        throws ClassNotFoundException,
        SQLException
    {
        Integer page = context.queryParamAsClass("page", Integer.class).getOrDefault(1);
        Integer idComponentCategory = context.queryParamAsClass("idComponentCategory", Integer.class).getOrDefault(null);

        List<VLabelTicketDTO> data = DB.handle(ctx -> {
            return TicketDTO.fetchByComponentCategory(ctx, idComponentCategory);
        });

        APIResponse.success(context, 200, new Pagination<>(data, 10).set(page));
    }

    public static void feecback(Context context)
        throws ClassNotFoundException,
        SQLException
    {
        Integer page = context.queryParamAsClass("page", Integer.class).getOrDefault(1);
        Integer idComponentCategory = context.queryParamAsClass("idComponentCategory", Integer.class).getOrDefault(null);

        List<VLabelTicketDTO> data = DB.handle(ctx -> {
            return TicketDTO.fetchByComponentCategory(ctx, idComponentCategory);
        });

        APIResponse.success(context, 200, new Pagination<>(data, 10).set(page));
    }
    
    public static void show(Context context)
        throws ClassNotFoundException,
                SQLException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        TicketDTO ticket = DB.handle(ctx -> {
            return new TicketDTO(ctx.fetchOne(TICKET, TICKET.ID.eq(id)));
        });

        APIResponse.success(context, 200, ticket);
    }

    public static void store(Context context)
        throws ClassNotFoundException,
                SQLException
    {
        try
        {
            JSONObject object = new JSONObject(context.body());
            TicketDTO ticket = new TicketDTO();
            ticket.setDateStart(LocalDateTime.parse(object.getString("dateStart")));
            ticket.setIdCustomer(object.getInt("idCustomer"));
            ticket.setIdModel(object.getInt("idModel"));
            ticket.setPriceReparation(object.getBigDecimal("priceReparation"));
            ticket.setDiagnostic(object.getString("diagnostic"));
            ticket.setIdEngineer(object.getInt("idEngineer"));

            DB.handle(ctx -> {
                return ticket.toRecord(ctx).store();
            });

            APIResponse.success(context, 201, Map.of("message", "Tiquet créé avec succès"));
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
            TicketDTO ticket = new TicketDTO();
            ticket.setDateStart(LocalDateTime.parse(object.getString("dateStart")));
            ticket.setIdCustomer(object.getInt("idCustomer"));
            ticket.setIdModel(object.getInt("idModel"));
            ticket.setPriceReparation(object.getBigDecimal("priceReparation"));
            ticket.setDiagnostic(object.getString("diagnostic"));
            ticket.setIdEngineer(object.getInt("idEngineer"));

            ticket.setId(id);

            DB.handle(ctx -> {
                return ticket.toRecord(ctx).store();
            });

            APIResponse.success(context, 201, Map.of("message", "Tiquet modifié avec succès"));
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
            return ctx.fetchOne(TICKET, TICKET.ID.eq(id))
                        .delete();
        });

        APIResponse.success(context, 201, Map.of("message", "Tiquet supprimée avec succès"));
    }
}
