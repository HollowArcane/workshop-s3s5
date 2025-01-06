import io.javalin.Javalin;
import io.javalin.validation.ValidationException;
import toolkit.util.Arrays;
import util.APIError;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.swing.JButton;
import javax.swing.JPanel;

import config.ThymeleafRenderer;
import controller.misc.ComponentCategoryController;


public class Main
{
    public static void main(String[] args)
        throws Exception
    {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("static");
            config.fileRenderer(new ThymeleafRenderer());

            config.router.apiBuilder(() -> {
                get("/misc/component-category", ComponentCategoryController::page);

                path("/api/misc", () -> {
                    get("/component-category", ComponentCategoryController::index);
                    post("/component-category", ComponentCategoryController::create);
                    get("/component-category/{id}", ComponentCategoryController::show);
                    put("/component-category/{id}", ComponentCategoryController::update);
                    delete("/component-category/{id}", ComponentCategoryController::delete);
                });
            });
        }).start(7000);

        app.exception(Exception.class, APIError::any)
           .exception(ValidationException.class, APIError::validation);
    }
}