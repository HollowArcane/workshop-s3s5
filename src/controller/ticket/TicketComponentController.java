package controller.ticket;

import static model.Tables.TICKET;
import static model.Tables.TICKET_COMPONENT;
import static model.Tables.V_LABEL_TICKET;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import database.DB;
import io.javalin.http.Context;
import util.APIResponse;
import util.Renderer;
import model.dto.ticket.TicketComponentDTO;
import model.dto.ticket.TicketDTO;
import model.dto.ticket.VLabelTicketDTO;
import toolkit.util.Pagination;

public class TicketComponentController
{
    public static void page(Context context)
    {
        Renderer.usingDefault()
            .render("ticket/ticket-component/index")
            .with(context, Map.of("active", "/ticket/ticket-component"));
    }

    public static void index(Context context)
        throws ClassNotFoundException,
        SQLException
    {
        Integer page = context.queryParamAsClass("page", Integer.class).getOrDefault(1);

        List<TicketComponentDTO> data = DB.handle(ctx -> {
            return ctx.fetch(TICKET_COMPONENT).map(TicketComponentDTO::new);
        });

        APIResponse.success(context, 200, new Pagination<>(data, 10).set(page));
    }
    
    public static void show(Context context)
        throws ClassNotFoundException,
                SQLException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        TicketComponentDTO ticketComponent = DB.handle(ctx -> {
            return new TicketComponentDTO(ctx.fetchOne(TICKET_COMPONENT, TICKET_COMPONENT.ID.eq(id)));
        });

        APIResponse.success(context, 200, ticketComponent);
    }

    public static void store(Context context)
        throws ClassNotFoundException,
                SQLException
    {
        try
        {
            JSONObject object = new JSONObject(context.body());
            TicketComponentDTO ticketComponent = new TicketComponentDTO();
            ticketComponent.setCostTotal(object.getBigDecimal("costTotal"));
            ticketComponent.setQuantity(object.getBigDecimal("quantity"));
            ticketComponent.setIdComponent(object.getInt("idComponent"));
            ticketComponent.setIdTicket(object.getInt("idTiquet"));
            
            DB.handle(ctx -> {
                return ticketComponent.toRecord(ctx).store();
            });

            APIResponse.success(context, 201, Map.of("message", "Composant ajouté avec succès"));
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
            TicketComponentDTO ticketComponent = new TicketComponentDTO();
            ticketComponent.setCostTotal(object.getBigDecimal("costTotal"));
            ticketComponent.setQuantity(object.getBigDecimal("quantity"));
            ticketComponent.setIdComponent(object.getInt("idComponent"));
            ticketComponent.setIdTicket(object.getInt("idTiquet"));
            
            ticketComponent.setId(id);

            DB.handle(ctx -> {
                return ticketComponent.toRecord(ctx).store();
            });

            APIResponse.success(context, 201, Map.of("message", "Composant modifié avec succès"));
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
            return ctx.fetchOne(TICKET_COMPONENT, TICKET_COMPONENT.ID.eq(id))
                        .delete();
        });

        APIResponse.success(context, 201, Map.of("message", "Composant retiré avec succès"));
    }
}
