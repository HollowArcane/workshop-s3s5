package util;

import java.util.Map;
import java.util.Optional;

import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

public class APIError
{
    public static void any(Exception exception, Context context)
    {
        exception.printStackTrace();
        APIResponse.error(context, 500, Map.of("message", Optional.ofNullable(exception.getMessage()).orElse("")));
    }

    public static void validation(ValidationException exception, Context context)
    {
        exception.printStackTrace();
        APIResponse.error(context, 400, Map.ofEntries(
            Map.entry("message", Optional.ofNullable(exception.getMessage()).orElse("")),
            Map.entry("details", exception.getErrors())
        ));
    }
}
