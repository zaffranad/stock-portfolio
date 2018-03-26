package zaf.stock.stockportfolio

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

@Configuration
class RoutingConfiguration {

    @Bean
    fun routerFunction(handler: PortfolioHandler) : RouterFunction<ServerResponse> = router {

        ("/portfolios").nest {
            val namePathVariable = "portfolioName"

            GET("/portfolio/{$namePathVariable}"){
                val name = it.pathVariable(namePathVariable)

                val portfolio = handler.getPortfolio(name)

                if(portfolio == null){
                    ServerResponse.notFound().build()
                }else{
                    ServerResponse.ok().body(BodyInserters.fromObject(portfolio))
                }
            }

            POST("/portfolio"){
                val name = it.body(BodyExtractors.toFormData()).map {
                  it.toSingleValueMap().get("name")

                }.toString()

                ServerResponse.ok().body(
                        handler.createPortfolio(name)
                )
            }
        }
    }
}