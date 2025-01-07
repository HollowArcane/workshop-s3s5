package util;

import java.sql.SQLException;

import org.jooq.Record1;
import org.jooq.TableField;

import database.DB;

public class Validation
{
    public static void check(boolean result, String errorMessage)
        throws IllegalArgumentException
    {
        if(!result)
        { throw new IllegalArgumentException(errorMessage); }
    }

    public static <T> boolean unique(T value, TableField<?, T> field)
    {
        Record1<T> result;
        try
        {
            result = DB.handle(ctx ->
                ctx.select(field)
                    .from(field.getTable())
                    .where(field.eq(value))
                    .fetchOne()
            );
        }
        catch (ClassNotFoundException | SQLException e)
        { throw new RuntimeException(e); }
        return result == null;
    }

    public static <T> boolean exists(T value, TableField<?, T> field)
    {
        Record1<T> result;
        try
        {
            result = DB.handle(ctx ->
                ctx.select(field)
                    .from(field.getTable())
                    .where(field.eq(value))
                    .fetchOne()
            );
        }
        catch (ClassNotFoundException | SQLException e)
        { throw new RuntimeException(e); }
        return result != null;
    }

    public static boolean notNull(Object value)
    { return value != null; }

    public static boolean notBlank(String value)
    { return value != null && !value.isBlank(); }
}
