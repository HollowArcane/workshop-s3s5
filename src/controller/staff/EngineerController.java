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
import model.dto.reparation.CustomerDTO;
import model.dto.reparation.ReparationDTO;
import model.dto.staff.EngineerDTO;
import model.tables.records.CustomerRecord;
import model.tables.records.ModelRecord;
import model.tables.records.ReparationRecord;
import model.tables.records.VEngineerCommissionRecord;
import toolkit.util.Pagination;
import util.Renderer;

public class EngineerController {

    public static void index(Context context)
        throws ClassNotFoundException,
        SQLException,
        RuntimeException {
         
            Result<VEngineerCommissionRecord> data = DB.handle(ctx -> {
                
                LocalDate dateMin = null;
                if(context.queryParamAsClass("dateMin",String.class).getOrDefault(null) != null)
                {
                    dateMin = LocalDate.parse(context.queryParamAsClass("dateMin",String.class).get());
                }
                
                LocalDate dateMax = null; 
                if(context.queryParamAsClass("dateMax",String.class).getOrDefault(null) != null)
                {
                    dateMax = LocalDate.parse(context.queryParamAsClass("dateMax",String.class).get());
                }

                return EngineerDTO.fetchByDate(ctx, dateMin, dateMax);
            });
            
            Renderer.usingDefault()
                .render("/staff/engineer/index")
                .with(context, Map.of("data",data));
        }
}
