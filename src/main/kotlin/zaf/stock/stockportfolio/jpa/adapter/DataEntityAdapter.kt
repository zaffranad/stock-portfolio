package zaf.stock.stockportfolio.jpa.adapter

import org.springframework.stereotype.Component
import zaf.stock.stockportfolio.jpa.entity.PortfolioEntity
import zaf.stock.stockportfolio.jpa.entity.PositionEntity
import zaf.stock.stockportfolio.portfolio.model.Portfolio
import zaf.stock.stockportfolio.portfolio.model.Position

@Component
class DataEntityAdapter {

    fun toPortfolio(portfolioEntity: PortfolioEntity): Portfolio {
        return Portfolio(
                portfolioEntity.name,
                portfolioEntity.creation,
                portfolioEntity.positions.map { toPosition(it) }.toMutableList()
        )
    }

    fun toPosition(positionEntity: PositionEntity): Position {
        return Position(
                positionEntity.isin,
                positionEntity.volume,
                positionEntity.buyPrice
        )
    }

    fun toPortfolios(findAll: List<PortfolioEntity>): List<Portfolio> {
        return findAll.map { toPortfolio(it) }.toList()
    }

    fun toPortfolioEntity(portfolio: Portfolio): PortfolioEntity{
        return PortfolioEntity(
                name = portfolio.name,
                creation = portfolio.creation,
                positions = portfolio.positions.map { toPositionEntity(it) }.toMutableList()
        )
    }

    private fun toPositionEntity(position: Position): PositionEntity {
        return PositionEntity(
                isin = position.isin,
                buyPrice = position.buyPrice,
                volume = position.volume
        )

    }


}