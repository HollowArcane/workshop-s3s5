import io.javalin.Javalin;
import io.javalin.validation.ValidationException;
import model.tables.RecommendationComponent;
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
import controller.ticket.CustomerController;
import controller.staff.EngineerController;
import controller.ticket.MvtTicketStateController;
import controller.ticket.TicketComponentController;
import controller.ticket.TicketController;


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
            config.validation.register(LocalDate.class, LocalDate::parse);
            config.staticFiles.add("static");
            config.fileRenderer(new ThymeleafRenderer());

            config.router.apiBuilder(() -> {
                get("/",  ctx -> ctx.redirect("/misc/component"));
             
                
                /* TICKET */{
                    get("/ticket/ticket", TicketController::page);
    
                    path("/api/ticket", () -> {
                        get("/ticket", TicketController::index);
                        post("/ticket", TicketController::store);
                        get("/ticket/{id}", TicketController::show);
                        put("/ticket/{id}", TicketController::update);
                        delete("/ticket/{id}", TicketController::delete);
                    });
                }

                /* TICKET COMPONENT */{
                    get("/ticket/ticket-component", TicketComponentController::page);
    
                    path("/api/ticket", () -> {
                        get("/ticket-component", TicketComponentController::index);
                        post("/ticket-component", TicketComponentController::store);
                        get("/ticket-component/{id}", TicketComponentController::show);
                        put("/ticket-component/{id}", TicketComponentController::update);
                        delete("/ticket-component/{id}", TicketComponentController::delete);
                    });
                }

                /* TICKET STATE */{
                    get("/ticket/mvt-ticket-state", MvtTicketStateController::page);
    
                    path("/api/ticket", () -> {
                        get("/mvt-ticket-state", MvtTicketStateController::index);
                        post("/mvt-ticket-state", MvtTicketStateController::store);
                        get("/mvt-ticket-state/{id}", MvtTicketStateController::show);
                        put("/mvt-ticket-state/{id}", MvtTicketStateController::update);
                        delete("/mvt-ticket-state/{id}", MvtTicketStateController::delete);
                    });
                }

                /* CUSTOMER */{
                    get("/ticket/customer", CustomerController::index);
                }



                /* RECOMMENDATION */ {
                    get("/recommendation/recommendation-component", RecommendationComponentController::index);
                    get("/recommendation/recommendation-component/create", RecommendationComponentController::create);
                    post("/recommendation/recommendation-component", RecommendationComponentController::store);
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
                    get("/misc/component/history", ComponentController::history);
                    
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

                
                /* ENGINEER */{
                    get("/staff/engineer", EngineerController::index);
                }
            });
        }).start(7000);

        app.before(Flashdata::load);

        app.exception(Exception.class, APIError::any)
           .exception(ValidationException.class, APIError::validation);
    }
}