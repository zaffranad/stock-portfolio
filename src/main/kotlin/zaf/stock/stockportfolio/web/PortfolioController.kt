package zaf.stock.stockportfolio.web

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import zaf.stock.stockportfolio.portfolio.exception.PortfolioOperationException
import zaf.stock.stockportfolio.portfolio.model.Portfolio
import zaf.stock.stockportfolio.portfolio.model.Position
import zaf.stock.stockportfolio.portfolio.usecase.AddPositionOnPortfolio
import zaf.stock.stockportfolio.portfolio.usecase.CreatePortfolio
import zaf.stock.stockportfolio.portfolio.usecase.GetAllPortfolios
import zaf.stock.stockportfolio.portfolio.usecase.GetSinglePortfolio
import zaf.stock.stockportfolio.web.exception.PortfolioCreationException
import zaf.stock.stockportfolio.web.exception.PortfolioStockAddException
import zaf.stock.stockportfolio.web.exception.ResourceNotFoundException

@RestController
@RequestMapping("/portfolios")
class PortfolioController(
        val createPortfolio: CreatePortfolio
        , val getSinglePortfolio: GetSinglePortfolio
        , val getAllPortfolios: GetAllPortfolios
        , val addPositionOnPortfolio: AddPositionOnPortfolio
) {

    val log = LoggerFactory.getLogger(PortfolioController::class.java.name)!!

    @GetMapping("/portfolio/{portfolioName}")
    fun getPorftolio(@PathVariable portfolioName: String): Portfolio {
        log.debug("getting portfolio with name $portfolioName")
        val portfolio = getSinglePortfolio.get(portfolioName)

        portfolio.ifPresent {
            log.debug("portfolio found: $portfolio")
            return@ifPresent
        }

        throw ResourceNotFoundException()
    }

    @PostMapping("/portfolio")
    fun newPortfolio(@RequestParam portfolioName: String): Portfolio {
        log.debug("create portfolio with name $portfolioName")

        val createdPortfolio = createPortfolio.create(portfolioName)

        createdPortfolio.ifPresent {
            log.debug("portfolio created: $createdPortfolio")
            return@ifPresent
        }

        throw PortfolioCreationException()
    }

    @GetMapping("/")
    fun listPortfolios(): List<Portfolio> {
        log.debug("getting all portfolios")
        return getAllPortfolios.get()
    }

    @PutMapping("/portfolio/{portfolioName}/positions/add")
    fun addPosition(@PathVariable portfolioName: String,
                    @RequestParam isin: String,
                    @RequestParam volume: Int,
                    @RequestParam price: Float): Portfolio {

        log.debug("add position $isin (vol: $volume - price: $price")

        try {
            return addPositionOnPortfolio.add(
                    Position(isin = isin, volume = volume, buyPrice = price),
                    portfolioName
            )
        }catch (operationExceptionOperationException: PortfolioOperationException){
            throw PortfolioStockAddException()
        }

    }
}