import io.javalin.Javalin;
import io.javalin.validation.ValidationException;
import util.APIError;
import util.Renderer;

import static io.javalin.apibuilder.ApiBuilder.*;


import config.ThymeleafRenderer;
import controller.misc.BrandController;
import controller.misc.ComponentCategoryController;
import controller.misc.ComponentController;
import controller.misc.ModelCategoryController;


public class Main
{
    public static void main(String[] args)
        throws Exception
    {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("static");
            config.fileRenderer(new ThymeleafRenderer());

            config.router.apiBuilder(() -> {
                get("/",  ctx -> ctx.redirect("/misc/component"));
                
                get("/misc/component-category", ComponentCategoryController::page);

                path("/api/misc", () -> {
                    get("/component-category", ComponentCategoryController::index);
                    post("/component-category", ComponentCategoryController::store);
                    get("/component-category/{id}", ComponentCategoryController::show);
                    put("/component-category/{id}", ComponentCategoryController::update);
                    delete("/component-category/{id}", ComponentCategoryController::delete);
                });

                get("/misc/brand", BrandController::page);

                path("/api/misc", () -> {
                    get("/brand", BrandController::index);
                    post("/brand", BrandController::store);
                    get("/brand/{id}", BrandController::show);
                    put("/brand/{id}", BrandController::update);
                    delete("/brand/{id}", BrandController::delete);
                });

                get("/misc/component", ComponentController::page);

                path("/api/misc", () -> {
                    get("/component", ComponentController::index);
                    post("/component", ComponentController::store);
                    get("/component/{id}", ComponentController::show);
                    put("/component/{id}", ComponentController::update);
                    delete("/component/{id}", ComponentController::delete);
                });

                get("/misc/model-category", ModelCategoryController::page);

                path("/api/misc", () -> {
                    get("/model-category", ModelCategoryController::index);
                    post("/model-category", ModelCategoryController::store);
                    get("/model-category/{id}", ModelCategoryController::show);
                    put("/model-category/{id}", ModelCategoryController::update);
                    delete("/model-category/{id}", ModelCategoryController::delete);
                });
            });
        }).start(7000);

        app.exception(Exception.class, APIError::any)
           .exception(ValidationException.class, APIError::validation);
    }
}