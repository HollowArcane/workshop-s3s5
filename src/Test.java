import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import database.DB;
import model.dto.ticket.TicketDTO;
import toolkit.util.Debug;

public class Test
{
    public static void main(String[] args)
        throws ClassNotFoundException, SQLException, RuntimeException
    {
        try
        {
            component3ShouldHave1Ticket();
            component5ShouldHave2Ticket();
            component6ShouldHave0Ticket();
            Debug.success("All test passed");
        }
        catch (AssertionError e)
        {
            Debug.error(e.getMessage());
            e.printStackTrace();
        }
        
    }

    public static void component3ShouldHave1Ticket()
        throws ClassNotFoundException, SQLException, RuntimeException
    {
        assertEquals(DB.handle(ctx -> TicketDTO.fetchByComponentCategory(ctx, 3)).size(), 1);
    }

    public static void component5ShouldHave2Ticket()
        throws ClassNotFoundException, SQLException, RuntimeException
    {
        assertEquals(DB.handle(ctx -> TicketDTO.fetchByComponentCategory(ctx, 5)).size(), 2);
    }

    public static void component6ShouldHave0Ticket()
        throws ClassNotFoundException, SQLException, RuntimeException
    {
        assertEquals(DB.handle(ctx -> TicketDTO.fetchByComponentCategory(ctx, 6)).size(), 0);
    }
}
