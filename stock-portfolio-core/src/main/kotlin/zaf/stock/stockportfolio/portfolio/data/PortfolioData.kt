package zaf.stock.stockportfolio.portfolio.data

import zaf.stock.stockportfolio.portfolio.model.Portfolio
import java.util.*

interface PortfolioData {

    fun get(portfolioName: String): Optional<Portfolio>

    fun getAll() : List<Portfolio>

    fun save(portfolio: Portfolio): Optional<Portfolio>

    fun update(portfolio: Portfolio): Optional<Portfolio>

}