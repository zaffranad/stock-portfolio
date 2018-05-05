package zaf.stock.stockportfolio.portfolio.facade

import org.springframework.stereotype.Component
import zaf.stock.stockportfolio.portfolio.model.Portfolio
import zaf.stock.stockportfolio.portfolio.model.Position
import zaf.stock.stockportfolio.portfolio.usecase.AddPositionOnPortfolio
import zaf.stock.stockportfolio.portfolio.usecase.CreatePortfolio
import zaf.stock.stockportfolio.portfolio.usecase.GetAllPortfolios
import zaf.stock.stockportfolio.portfolio.usecase.GetSinglePortfolio
import java.util.*

@Component
class PortfolioFacade (
        val createPortfolio: CreatePortfolio
        , val getSinglePortfolio: GetSinglePortfolio
        , val getAllPortfolios: GetAllPortfolios
        , val addPositionOnPortfolio: AddPositionOnPortfolio
){
    fun create(name: String): Optional<Portfolio> {
        return createPortfolio.create(name)
    }

    fun get(name: String): Optional<Portfolio> {
        return getSinglePortfolio.get(name)
    }

    fun getAll(): List<Portfolio> {
        return getAllPortfolios.get()
    }

    fun addPosition(position: Position, portfolioName: String) : Portfolio{
        return addPositionOnPortfolio.add(position, portfolioName)
    }
}