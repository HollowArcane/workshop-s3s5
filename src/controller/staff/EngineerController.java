package controller.staff;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Result;

import database.DB;
import io.javalin.http.Context;
import model.Tables;
import model.dto.staff.EngineerDTO;
import model.dto.staff.GenderCommissionDTO;
import model.dto.ticket.CustomerDTO;
import model.tables.records.CustomerRecord;
import model.tables.records.ModelRecord;
import model.tables.records.VEngineerCommissionRecord;
import toolkit.util.Pagination;
import util.Renderer;

public class EngineerController {

    public static void index(Context context)
        throws ClassNotFoundException,
        SQLException,
        RuntimeException
        {
            LocalDate dateMin = context.queryParamAsClass("dateMin", LocalDate.class).getOrDefault(null);
            LocalDate dateMax = context.queryParamAsClass("dateMax", LocalDate.class).getOrDefault(null);
         
            Map<String, Object> data = DB.handle(ctx -> Map.of(
                "data", EngineerDTO.fetchByDate(ctx, dateMin, dateMax),
                "data2", EngineerDTO.fetchCommissionByGender(ctx, dateMin, dateMax),
                "active", "/staff/engineer"
            ));
            
            Renderer.usingDefault()
                .render("/staff/engineer/index")
                .with(context, data);
        }
}
