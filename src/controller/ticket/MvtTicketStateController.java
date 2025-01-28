package controller.ticket;

import static model.Tables.MVT_TICKET_STATE;
import static model.Tables.TICKET_STATE;
import static model.Tables.V_LABEL_MVT_TICKET_STATE;
import static model.Tables.V_LABEL_TICKET;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import database.DB;
import io.javalin.http.Context;
import model.dto.ticket.MvtTicketStateDTO;
import model.dto.ticket.VLabelMvtTicketStateDTO;
import model.dto.ticket.VLabelTicketDTO;
import model.tables.VLabelMvtTicketState;
import model.tables.records.TicketStateRecord;
import model.tables.records.VLabelTicketRecord;
import toolkit.util.Pagination;
import util.APIResponse;
import util.Data;
import util.Renderer;

public class MvtTicketStateController
{
    public static void page(Context context)
        throws ClassNotFoundException,
            SQLException,
            RuntimeException
    {
        Map<String, Object> data = DB.handle(ctx -> Map.of(
            "active", "/ticket/mvt-ticket-state",
            "tickets", Data.asMap(ctx.fetch(V_LABEL_TICKET), VLabelTicketRecord::getId, t -> t.getDateStart() + " " + t.getSerialNumber() + " " + t.getModelCategory()),
            "ticketStates", Data.asMap(ctx.fetch(TICKET_STATE), TicketStateRecord::getId, TicketStateRecord::getLabel)
        ));
        Renderer.usingDefault()
            .render("ticket/mvt-ticket-state/index")
            .with(context, data);
    }

    public static void index(Context context)
        throws ClassNotFoundException,
        SQLException
    {
        Integer page = context.queryParamAsClass("page", Integer.class).getOrDefault(1);


        List<VLabelMvtTicketStateDTO> data = DB.handle(ctx -> {
            return ctx.fetch(V_LABEL_MVT_TICKET_STATE).map(VLabelMvtTicketStateDTO::new);
        });

        APIResponse.success(context, 200, new Pagination<>(data, 10).set(page));
    }
    
    public static void show(Context context)
        throws ClassNotFoundException,
                SQLException
    {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        MvtTicketStateDTO mvtTicketState = DB.handle(ctx -> {
            return new MvtTicketStateDTO(ctx.fetchOne(MVT_TICKET_STATE, MVT_TICKET_STATE.ID.eq(id)));
        });

        APIResponse.success(context, 200, mvtTicketState);
    }

    public static void store(Context context)
        throws ClassNotFoundException,
                SQLException
    {
        try
        {
            JSONObject object = new JSONObject(context.body());
            MvtTicketStateDTO mvtTicketState = new MvtTicketStateDTO();
            mvtTicketState.setDatetime(LocalDateTime.parse(object.getString("datetime")));
            mvtTicketState.setIdTicket(object.getInt("idTicket"));
            mvtTicketState.setIdTicketState(object.getInt("idTicketState"));
            
            DB.handle(ctx -> {
                return mvtTicketState.toRecord(ctx).store();
            });

            APIResponse.success(context, 201, Map.of("message", "État de Tiquet mis à jour avec succès"));
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
            MvtTicketStateDTO mvtTicketState = new MvtTicketStateDTO();
            mvtTicketState.setDatetime(LocalDateTime.parse(object.getString("datetime")));
            mvtTicketState.setIdTicket(object.getInt("idTicket"));
            mvtTicketState.setIdTicketState(object.getInt("idTicketState"));
            
            mvtTicketState.setId(id);

            DB.handle(ctx -> {
                return mvtTicketState.toRecord(ctx).store();
            });

            APIResponse.success(context, 201, Map.of("message", "État de Tiquet modifié avec succès"));
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
            return ctx.fetchOne(MVT_TICKET_STATE, MVT_TICKET_STATE.ID.eq(id))
                        .delete();
        });

        APIResponse.success(context, 201, Map.of("message", "État de Tiquet retiré avec succès"));
    }
}
