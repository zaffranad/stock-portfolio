package zaf.stock.stockportfolio.jpa

import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Component
import zaf.stock.stockportfolio.jpa.adapter.DataEntityAdapter
import zaf.stock.stockportfolio.portfolio.data.PortfolioData
import zaf.stock.stockportfolio.portfolio.exception.PortfolioOperationException
import zaf.stock.stockportfolio.portfolio.model.Portfolio
import java.util.*

@Component
class PortfolioJPA(
        val portfolioRepository: PortfolioRepository
        , val dataEntityAdapter: DataEntityAdapter
) : PortfolioData {

    val log = LoggerFactory.getLogger(PortfolioJPA::class.java.name)!!

    override fun get(portfolioName: String): Optional<Portfolio> {

        return try {
            val portfolioFound = portfolioRepository.findByName(portfolioName)
            Optional.of(
                    dataEntityAdapter.toPortfolio(portfolioFound)
            )
        } catch (e: DataAccessException) {
            log.error("error while retrieving portfolio", e)
            Optional.empty()
        }
    }

    override fun getAll(): List<Portfolio> {
        return try {
            val portfolios = portfolioRepository.findAll()
            dataEntityAdapter.toPortfolios(portfolios)
        } catch (e: DataAccessException) {
            log.error("error while retrieving all portfolios", e)
            throw PortfolioOperationException()
        }
    }

    override fun save(portfolio: Portfolio): Optional<Portfolio> {
        // TODO verifier l'existence du portfolio avant?"

        return try {
            val toPortfolioEntity = dataEntityAdapter.toPortfolioEntity(portfolio)
            log.debug("saving the entity $toPortfolioEntity")

            val portfolioEntity = portfolioRepository.save(toPortfolioEntity)
            log.debug("saved entity: $portfolioEntity")

            val portfolioSaved = dataEntityAdapter.toPortfolio(portfolioEntity)
            Optional.of(portfolioSaved)
        } catch (e: DataAccessException) {
            log.error("error while saving new portfolio")
            Optional.empty()
        }
    }

    override fun update(portfolio: Portfolio): Optional<Portfolio> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}