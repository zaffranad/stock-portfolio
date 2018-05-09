package zaf.stock.stockportfolio.web

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import zaf.stock.stockportfolio.portfolio.exception.PortfolioOperationException
import zaf.stock.stockportfolio.portfolio.facade.PortfolioFacade
import zaf.stock.stockportfolio.portfolio.model.Portfolio
import zaf.stock.stockportfolio.portfolio.model.Position
import zaf.stock.stockportfolio.web.exception.PortfolioCreationException
import zaf.stock.stockportfolio.web.exception.PortfolioRetrievingException
import zaf.stock.stockportfolio.web.exception.PortfolioStockAddException
import zaf.stock.stockportfolio.web.exception.ResourceNotFoundException

@RestController
@RequestMapping("/portfolios")
class PortfolioController(
        val portfolioFacade: PortfolioFacade
) {

    val log = LoggerFactory.getLogger(PortfolioController::class.java.name)!!

    @GetMapping("/portfolio/{portfolioName}")
    fun getPorftolio(@PathVariable portfolioName: String): Portfolio {
        log.debug("getting portfolio with name $portfolioName")
        val portfolio = portfolioFacade.get(portfolioName)

        if (portfolio.isPresent) {
            log.debug("portfolio found: $portfolio")
            return portfolio.get()
        }

        throw ResourceNotFoundException("portfolio with name $portfolioName not found")
    }

    @PostMapping("/portfolio")
    fun newPortfolio(@RequestParam portfolioName: String): Portfolio {
        log.debug("create portfolio with name $portfolioName")

        val createdPortfolio = portfolioFacade.create(portfolioName)

        val portfolio = createdPortfolio.orElseThrow({ PortfolioCreationException() })
        log.debug("portfolio created: $createdPortfolio")

        return portfolio
    }

    @GetMapping("/")
    fun listPortfolios(): List<Portfolio> {
        log.debug("getting all portfolios")
        try {
            return portfolioFacade.getAll()
        } catch (e: PortfolioOperationException) {
            throw PortfolioRetrievingException("Exception while retrieving all portfolios")
        }
    }

    @PutMapping("/portfolio/{portfolioName}/positions/add")
    fun addPosition(@PathVariable portfolioName: String,
                    @RequestParam isin: String,
                    @RequestParam volume: Int,
                    @RequestParam price: Float): Portfolio {

        log.debug("add position $isin (vol: $volume - price: $price")

        try {
            return portfolioFacade.addPosition(
                    Position(isin = isin, volume = volume, buyPrice = price),
                    portfolioName
            )
        } catch (operationExceptionOperationException: PortfolioOperationException) {
            throw PortfolioStockAddException()
        }

    }
}