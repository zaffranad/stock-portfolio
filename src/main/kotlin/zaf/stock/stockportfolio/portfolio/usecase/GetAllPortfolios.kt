package zaf.stock.stockportfolio.portfolio.usecase

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import zaf.stock.stockportfolio.portfolio.data.PortfolioData
import zaf.stock.stockportfolio.portfolio.model.Portfolio

@Component
class GetAllPortfolios(val portfolioData: PortfolioData) {

    val log = LoggerFactory.getLogger(GetAllPortfolios::class.java.name)!!

    fun get(): List<Portfolio> {
        log.debug("retrieving all portfolios")
        val all = portfolioData.getAll()

        log.debug("${all.size} portfolios found")
        return all
    }
}