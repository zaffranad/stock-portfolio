package zaf.stock.stockportfolio

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import zaf.stock.stockportfolio.PortfolioHandler.Companion.namePathVariable

@Configuration
class RoutingConfiguration {

    @Bean
    fun routerFunction(handler: PortfolioHandler) : RouterFunction<ServerResponse> = router {

        ("/portfolios").nest {

            GET("/portfolio/{$namePathVariable}"){
                handler.getPortfolio(it)
            }

            POST("/portfolio/new"){
                handler.createPortfolio(it)
            }

            PATCH("/portfolio/{$namePathVariable}/positions"){
                handler.updatePositions(it)
            }
        }
    }
}