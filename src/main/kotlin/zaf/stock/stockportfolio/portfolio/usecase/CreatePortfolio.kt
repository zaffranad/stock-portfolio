package zaf.stock.stockportfolio.portfolio.usecase

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import zaf.stock.stockportfolio.portfolio.data.PortfolioData
import zaf.stock.stockportfolio.portfolio.exception.PortfolioOperationException
import zaf.stock.stockportfolio.portfolio.model.Portfolio
import java.util.*

@Component
class CreatePortfolio(val portfolioData: PortfolioData) {

    val log = LoggerFactory.getLogger(CreatePortfolio::class.java.name)!!

    fun create(name: String): Optional<Portfolio> {
        log.debug("creating new portfolio with name: $name")

        if(portfolioData.get(name).isPresent){
            throw PortfolioOperationException("portfolio with name $name already existing")
        }

        val saved = portfolioData.save(Portfolio(name = name))

        log.debug("saved portfolio: $saved")
        return saved
    }
}