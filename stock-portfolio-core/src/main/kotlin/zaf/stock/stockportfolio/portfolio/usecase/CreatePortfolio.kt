package zaf.stock.stockportfolio.portfolio.usecase

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import zaf.stock.stockportfolio.portfolio.data.PortfolioData
import zaf.stock.stockportfolio.portfolio.model.Portfolio
import java.util.*

@Component
class CreatePortfolio(val portfolioData: PortfolioData) {

    val log = LoggerFactory.getLogger(CreatePortfolio::class.java.name)!!

    /**
     * Construct Portfolio from request body then persist it
     * if saved, the portfolio saved is returned
     * if not, NOT_MODIFIED statut send back
     */
    fun create(name: String): Optional<Portfolio> {
        log.debug("creating new portfolio with name: $name")

        val portfolio = Portfolio(name = name)

        val saved = portfolioData.save(portfolio)

        log.debug("saved portfolio: $saved")
        return saved
    }
}