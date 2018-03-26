package zaf.stock.stockportfolio

import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_MODIFIED
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors.toFormData
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono

@Component
class PortfolioHandler(val portfolioRepository: PortfolioRepository) {

    val log = LoggerFactory.getLogger(PortfolioHandler::class.java.name)

    companion object {
        val namePathVariable = "portfolioName"
    }

    /**
     * Retrieve the portfolio with name $name
     * if found, the portfolio is returned
     * if not, 404 returned
     */
    fun getPortfolio(request: ServerRequest): Mono<ServerResponse> {
        val name = request.pathVariable(namePathVariable)
        return try {
            toServerResponse(portfolioRepository.findByName(name))
        } catch (e: EmptyResultDataAccessException) {
            log.debug("portfolio not found with name $name - $e")
            notFound().build()
        }
    }

    /**
     * Construct Portfolio from request body then persist it
     * if saved, the portfolio saved is returned
     * if not, NOT_MODIFIED statut send back
     */
    fun createPortfolio(request: ServerRequest): Mono<ServerResponse> {
        val portfolio = toPortfolio(request)

        return try {
            val portfolioSaved = portfolioRepository.save(portfolio)
            toServerResponse(portfolioSaved)
        } catch (e: Exception) {
            log.debug("error while saving portfolio: $e")
            status(NOT_MODIFIED).build()
        }
    }

    /**
     * Fully replace positions on portfolio
     */
    fun updatePositions(request: ServerRequest): Mono<ServerResponse> {
        val name = request.pathVariable(namePathVariable)
        val positions = getPositions(request)

        return try {
            val portfolio = portfolioRepository.findByName(name)
            portfolio.positions.clear()
            portfolio.positions.addAll(positions)

            portfolioRepository.save(portfolio)
            toServerResponse(portfolio)
        } catch (e: Exception) {
            log.debug("error while update positions for portfolio $name - $e")
            ServerResponse.status(INTERNAL_SERVER_ERROR).build()
        }
    }

    private fun getPositions(request: ServerRequest): List<StockPosition> {
        return listOf(StockPosition("AAAAA", 100, 1.24))
    }

    private fun toPortfolio(request: ServerRequest): Portfolio {
        val name = request.body(toFormData()).map {
            it.toSingleValueMap()["name"]
        }.toString()

        return Portfolio(name = name)
    }

    private fun toServerResponse(portfolioSaved: Portfolio): Mono<ServerResponse> =
            ok().body(fromObject(portfolioSaved))



}