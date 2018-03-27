package zaf.stock.stockportfolio

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/portfolios")
class PortfolioController(val portfolioHandler: PortfolioHandler) {

    val log = LoggerFactory.getLogger(PortfolioController::class.java.name)!!

    @GetMapping("/portfolio/{portfolioName}")
    fun getPorftolio(@PathVariable portfolioName: String): Portfolio {
        log.debug("getting portfolio with name $portfolioName")
        val portfolio = portfolioHandler.getPortfolio(portfolioName)

        portfolio.ifPresent {
            log.debug("portfolio found: $portfolio")
            return@ifPresent
        }

        throw ResourceNotFoundException()
    }


    /*POST("/new", handler::createPortfolio)
    POST("/{${PortfolioHandler.namePathVariable}}/positions", handler::updatePositions)
}*/
}