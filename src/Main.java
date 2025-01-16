import io.javalin.Javalin;
import io.javalin.validation.ValidationException;
import model.tables.RecommendationComponent;
import model.tables.records.ReparationRecord;
import util.APIError;
import util.Flashdata;
import util.Renderer;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.time.LocalDate;

import config.ThymeleafRenderer;
import controller.misc.BrandController;
import controller.misc.ComponentCategoryController;
import controller.misc.ComponentController;
import controller.misc.ModelCategoryController;
import controller.misc.ModelController;
import controller.recommendation.RecommendationComponentController;
import controller.reparation.CustomerController;
import controller.reparation.ReparationController;
import controller.reparation.ReparationFeedbackController;


public class Main
{
    public static void main(String[] args)
        throws Exception
    {
        /*
         * model: jooq
         * controller: javalin
         * view: thymeleaf
         */

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("static");
            config.fileRenderer(new ThymeleafRenderer());

            config.router.apiBuilder(() -> {
                get("/",  ctx -> ctx.redirect("/misc/component"));

                get("/reparation/reparation", ReparationController::index);

                get("/recommendation/recommendation-component", RecommendationComponentController::index);
                get("/recommendation/recommendation-component/create", RecommendationComponentController::create);
                post("/recommendation/recommendation-component", RecommendationComponentController::store);

                /* FEEDBACK */ {
                    get("/reparation/feedback", ReparationFeedbackController::index);
                    
                    get("/reparation/feedback/create", ReparationFeedbackController::loadForm);
                    post("/reparation/feedback", ReparationFeedbackController::store);
                }
                
                /* COMPONENT CATEGORY */{
                    get("/misc/component-category", ComponentCategoryController::page);

                    path("/api/misc", () -> {
                        get("/component-category", ComponentCategoryController::index);
                        post("/component-category", ComponentCategoryController::store);
                        get("/component-category/{id}", ComponentCategoryController::show);
                        put("/component-category/{id}", ComponentCategoryController::update);
                        delete("/component-category/{id}", ComponentCategoryController::delete);
                    });
                }

                /* BRAND */{
                    get("/misc/brand", BrandController::page);
    
                    path("/api/misc", () -> {
                        get("/brand", BrandController::index);
                        post("/brand", BrandController::store);
                        get("/brand/{id}", BrandController::show);
                        put("/brand/{id}", BrandController::update);
                        delete("/brand/{id}", BrandController::delete);
                    });
                }

                /* COMPONENT */{
                    get("/misc/component", ComponentController::page);
    
                    path("/api/misc", () -> {
                        get("/component", ComponentController::index);
                        post("/component", ComponentController::store);
                        get("/component/{id}", ComponentController::show);
                        put("/component/{id}", ComponentController::update);
                        delete("/component/{id}", ComponentController::delete);
                    });
                }

                /* MODEL CATEGORY */{
                    get("/misc/model-category", ModelCategoryController::page);
    
                    path("/api/misc", () -> {
                        get("/model-category", ModelCategoryController::index);
                        post("/model-category", ModelCategoryController::store);
                        get("/model-category/{id}", ModelCategoryController::show);
                        put("/model-category/{id}", ModelCategoryController::update);
                        delete("/model-category/{id}", ModelCategoryController::delete);
                    });
                }

                /* MODEL */{
                    get("/misc/model", ModelController::page);
    
                    path("/api/misc", () -> {
                        get("/model", ModelController::index);
                        post("/model", ModelController::store);
                        get("/model/{id}", ModelController::show);
                        put("/model/{id}", ModelController::update);
                        delete("/model/{id}", ModelController::delete);
                    });
                }

                /* CUSTOMER */{
                    get("/reparation/customer", CustomerController::index);
                }
            });
        }).start(7000);

        app.before(Flashdata::load);

        app.exception(Exception.class, APIError::any)
           .exception(ValidationException.class, APIError::validation);
    }
}