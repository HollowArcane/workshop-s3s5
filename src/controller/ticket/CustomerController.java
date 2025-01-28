package controller.ticket;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

import org.jooq.Result;

import database.DB;
import io.javalin.http.Context;
import model.dto.ticket.CustomerDTO;
import model.tables.records.CustomerRecord;
import util.Renderer;

public class CustomerController {

    public static void index(Context context)
            throws ClassNotFoundException,
            SQLException,
            RuntimeException {

        Result<CustomerRecord> data = DB.handle(ctx -> {
            LocalDate date = context.queryParamAsClass("date", LocalDate.class).getOrDefault(null);
            return CustomerDTO.fetchByDate(ctx, date);
        });

        Renderer.usingDefault()
                .render("/ticket/customer/index")
                .with(context, Map.of(
                    "data", data,
                    "active", "/ticket/customer"
                ));
    }

}
