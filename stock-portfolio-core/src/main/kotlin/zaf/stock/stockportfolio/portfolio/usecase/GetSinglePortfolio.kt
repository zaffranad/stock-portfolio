package zaf.stock.stockportfolio.portfolio.usecase

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import zaf.stock.stockportfolio.portfolio.data.PortfolioData
import zaf.stock.stockportfolio.portfolio.model.Portfolio
import java.util.*

@Component
class GetSinglePortfolio(val portfolioData: PortfolioData) {

    val log = LoggerFactory.getLogger(GetSinglePortfolio::class.java.name)!!

    /**
     * Retrieve the portfolio with name $name
     * if found, the portfolio is returned
     * if not, 404 returned
     */
    fun get(name: String): Optional<Portfolio> {
        log.debug("retrieving portfolio with name $name")
        val portfolio = portfolioData.get(name)

        log.debug("portfolio found: $portfolio")
        return portfolio
    }
}