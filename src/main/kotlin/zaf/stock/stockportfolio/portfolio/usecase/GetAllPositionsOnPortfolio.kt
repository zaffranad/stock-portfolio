package zaf.stock.stockportfolio.portfolio.usecase

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import zaf.stock.stockportfolio.portfolio.model.Position

@Component
class GetAllPositionsOnPortfolio(val getSinglePortfolio: GetSinglePortfolio) {

    val log = LoggerFactory.getLogger(GetAllPositionsOnPortfolio::class.java.name)!!

    fun getAll(portfolioName: String): List<Position> {
        val portfolio = getSinglePortfolio.get(portfolioName)

        if (portfolio.isPresent) {
            return portfolio.get().positions
        }

        log.debug("no portfolio found with name $portfolioName")
        return emptyList()
    }
}