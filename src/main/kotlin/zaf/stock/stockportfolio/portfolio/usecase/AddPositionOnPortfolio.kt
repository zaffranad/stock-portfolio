package zaf.stock.stockportfolio.portfolio.usecase

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import zaf.stock.stockportfolio.portfolio.data.PortfolioData
import zaf.stock.stockportfolio.portfolio.exception.PortfolioOperationException
import zaf.stock.stockportfolio.portfolio.model.Portfolio
import zaf.stock.stockportfolio.portfolio.model.Position

@Component
class AddPositionOnPortfolio(
        val portfolioData: PortfolioData
        , val getSinglePortfolio: GetSinglePortfolio
) {

    val log = LoggerFactory.getLogger(AddPositionOnPortfolio::class.java.name)!!

    fun add(position: Position, portfolioName: String): Portfolio {
        val portfolioToUpdate = getSinglePortfolio.get(portfolioName)

        if(portfolioToUpdate.isPresent){
            portfolioToUpdate.get().positions.add(position)

            val updatedPortfolio = portfolioData.update(portfolioToUpdate.get())
            log.debug("portfolio updated with new position: $updatedPortfolio")

            return updatedPortfolio.get()
        }

        log.error("Trying to add position on portfolio $portfolioName which was not found")
        throw PortfolioOperationException("Trying to add position on portfolio $portfolioName which was not found")

    }

}