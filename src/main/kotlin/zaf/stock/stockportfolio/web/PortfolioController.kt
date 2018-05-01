package zaf.stock.stockportfolio.web

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import zaf.stock.stockportfolio.service.PortfolioService
import zaf.stock.stockportfolio.model.Portfolio
import zaf.stock.stockportfolio.model.StockPortfolio
import zaf.stock.stockportfolio.web.exception.PortfolioCreationException
import zaf.stock.stockportfolio.web.exception.ResourceNotFoundException

@RestController
@RequestMapping("/portfolios")
class PortfolioController(val portfolioService: PortfolioService) {

    val log = LoggerFactory.getLogger(PortfolioController::class.java.name)!!

    @GetMapping("/portfolio/{portfolioName}")
    fun getPorftolio(@PathVariable portfolioName: String): Portfolio {
        log.debug("getting portfolio with name $portfolioName")
        val portfolio = portfolioService.getPortfolio(portfolioName)

        portfolio.ifPresent {
            log.debug("portfolio found: $portfolio")
            return@ifPresent
        }

        throw ResourceNotFoundException()
    }

    @PostMapping("/portfolio")
    fun newPortfolio(@RequestParam portfolioName: String): Portfolio {
        log.debug("create portfolio with name $portfolioName")

        val createdPortfolio = portfolioService.createPortfolio(portfolioName)

        createdPortfolio.ifPresent {
            log.debug("portfolio created: $createdPortfolio")
            return@ifPresent
        }

        throw PortfolioCreationException()
    }

    @GetMapping("/")
    fun listPortfolios() {
        log.debug("getting all portfolios")

        portfolioService.getAll()
    }

    @PutMapping("/portfolio/{portfolioName}")
    fun addStock(@PathVariable portfolioName: String,
                 @RequestParam isin: String,
                 @RequestParam volume: Int,
                 @RequestParam price: Float): Portfolio {

        log.debug("add stock $isin (vol: $volume - price: $price")
        val portfolio = portfolioService.getPortfolio(portfolioName)

        portfolio.ifPresent {
            log.debug("portfolio found: $portfolio")

            it.stocks.add(
                    StockPortfolio(isin = isin, volume = volume, buyPrice = price)
            )
        }
    }
}