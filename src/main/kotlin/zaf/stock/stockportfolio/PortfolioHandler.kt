package zaf.stock.stockportfolio

import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Component
class PortfolioHandler(val portfolioRepository: PortfolioRepository) {

    companion object {
        val LOG = LoggerFactory.getLogger(PortfolioHandler::class.java.name)
    }

    fun getPortfolio(name: String): Portfolio? = try {
        portfolioRepository.findByName(name)
    } catch (e: EmptyResultDataAccessException) {
        LOG.debug("portfolio not found with name $name : $e")
        null
    }

    fun createPortfolio(name: String): Mono<Portfolio> = portfolioRepository.save(
            Portfolio(name = name)
    ).toMono()
}