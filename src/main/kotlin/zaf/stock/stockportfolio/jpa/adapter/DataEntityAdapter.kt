package zaf.stock.stockportfolio.jpa.adapter

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import zaf.stock.stockportfolio.jpa.entity.PortfolioEntity
import zaf.stock.stockportfolio.jpa.entity.PositionEntity
import zaf.stock.stockportfolio.portfolio.model.Portfolio
import zaf.stock.stockportfolio.portfolio.model.Position

@Component
class DataEntityAdapter {

    val log = LoggerFactory.getLogger(DataEntityAdapter::class.java.name)!!

    fun toPortfolio(portfolioEntity: PortfolioEntity): Portfolio {
        log.debug("converting portfolio entity $portfolioEntity to portfolio ")
        val portfolio = Portfolio(
                portfolioEntity.name,
                portfolioEntity.creation,
                portfolioEntity.positions.map { toPosition(it) }.toMutableList()
        )

        log.debug("converted: $portfolio")
        return portfolio
    }

    fun toPosition(positionEntity: PositionEntity): Position {
        log.debug("converting position entity $positionEntity to position ")
        val position = Position(
                positionEntity.isin,
                positionEntity.volume,
                positionEntity.buyPrice
        )

        log.debug("converted: $position")
        return position
    }

    fun toPortfolios(findAll: List<PortfolioEntity>): List<Portfolio> {
        return findAll.map { toPortfolio(it) }.toList()
    }

    fun toPortfolioEntity(portfolio: Portfolio): PortfolioEntity{
        log.debug("converting portfolio $portfolio to portfolio entity")
        val portfolioEntity = PortfolioEntity(
                name = portfolio.name,
                creation = portfolio.creation,
                positions = portfolio.positions.map { toPositionEntity(it) }.toMutableList()
        )
        log.debug("converted: $portfolioEntity")
        return portfolioEntity
    }

    private fun toPositionEntity(position: Position): PositionEntity {
        log.debug("converting position $position to position entity")

        val positionEntity = PositionEntity(
                isin = position.isin,
                buyPrice = position.buyPrice,
                volume = position.volume
        )

        log.debug("converted: $position")
        return positionEntity

    }


}