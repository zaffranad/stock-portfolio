package zaf.stock.stockportfolio

import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.BodyExtractors.*
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import javax.sound.sampled.Port

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

    fun createPortfolio(request: ServerRequest): Mono<ServerResponse> {
        val formToPortfolio = formToPortfolio(request)

        return try {
            val portfolioSaved = portfolioRepository.save(formToPortfolio)
            toServerResponse(portfolioSaved)
        } catch (e: Exception) {
            log.debug("error while saving portfolio: $e")
            status(NOT_MODIFIED).build()
        }
    }

    fun formToPortfolio(request: ServerRequest): Portfolio{
        val name = request.body(toFormData()).map {
            it.toSingleValueMap()["name"]
        }.toString()

        return Portfolio(name = name)
    }

    private fun toServerResponse(portfolioSaved: Portfolio): Mono<ServerResponse> {
        ok().body(
                BodyInserters.fromObject(
                        portfolioSaved
                )
        )
    }

}