package controller.reparation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jooq.Record3;
import org.jooq.Result;

import database.DB;
import io.javalin.http.Context;
import model.Tables;
import model.dto.reparation.CustomerDTO;
import model.dto.reparation.ReparationDTO;
import model.tables.records.CustomerRecord;
import model.tables.records.ModelRecord;
import model.tables.records.ReparationRecord;
import toolkit.util.Pagination;
import util.Renderer;

public class CustomerController {

    public static void index(Context context)
        throws ClassNotFoundException, 
                    SQLException, 
                    RuntimeException{

        Result<CustomerRecord> data = DB.handle(ctx -> {
            return CustomerDTO.fetchByDate(ctx, null);
        });

        Renderer.usingDefault()
        .render("/reparation/customer/index")
        .with(context,Map.of("data",data));
    }
    
}
