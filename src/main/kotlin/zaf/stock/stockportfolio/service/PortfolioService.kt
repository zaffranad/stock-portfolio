package zaf.stock.stockportfolio.service

import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Component
import zaf.stock.stockportfolio.data.PortfolioRepository
import zaf.stock.stockportfolio.model.Portfolio
import java.util.*

@Component
class PortfolioService(val portfolioRepository: PortfolioRepository) {

    val log = LoggerFactory.getLogger(PortfolioService::class.java.name)!!

    /**
     * Retrieve the portfolio with name $name
     * if found, the portfolio is returned
     * if not, 404 returned
     */
    fun getPortfolio(name: String): Optional<Portfolio> {
        return try {
            Optional.of(portfolioRepository.findByName(name))
        } catch (e: DataAccessException) {
            log.info("exception while retrieving portfolio named: $name - $e")
            Optional.empty()
        }
    }

    /**
     * Construct Portfolio from request body then persist it
     * if saved, the portfolio saved is returned
     * if not, NOT_MODIFIED statut send back
     */
    fun createPortfolio(name: String): Optional<Portfolio> {
        return try{
            val portfolio = Portfolio(name = name)
            val saved = portfolioRepository.save(portfolio)
            Optional.of(saved)
        }catch (e: DataAccessException){
            log.warn("creation of portfolio named $name failed with error: $e")
            Optional.empty()
        }
    }

    fun getAll() : List<Portfolio>{
        return portfolioRepository.findAll()
    }

    /**
     * Fully replace stocks on portfolio
     *//*

    fun updatePositions(request: ServerRequest): Mono<ServerResponse> {
        val portfolioName = request.pathVariable(namePathVariable)

        return try {
//            val portfolio = portfolioRepository.findByName(portfolioName)
            val portfolio = Portfolio(name = "test")
            portfolio.stocks.clear()

            val stocks = getStocks(request)
            portfolio.stocks.addAll(stocks)

            portfolioRepository.save(portfolio)
            toServerResponse(portfolio)
        } catch (e: Exception) {
            log.debug("error while update stocks for portfolio $portfolioName - $e")
            ServerResponse.status(INTERNAL_SERVER_ERROR).build()
        }
    }

    private fun getStocks(request: ServerRequest): List<StockPosition> {
        val flux= request.bodyToFlux<StockPositionDTO>()

        val list = flux.map { t -> StockPosition(isin = t.isin, volume = t.volume, buyPrice = t.buyPrice) }
                .collectList()

        return list.block()!!
    }


    private fun toPortfolio(request: ServerRequest): Portfolio {
        val name = request.body(toFormData()).map {
            it.toSingleValueMap()["name"]
        }.toString()

        return Portfolio(name = name)
    }

    private fun toServerResponse(portfolioSaved: Portfolio): Mono<ServerResponse> =
            ok().body(fromObject(portfolioSaved))

*/


}