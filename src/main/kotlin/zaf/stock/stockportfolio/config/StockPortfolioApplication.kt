package zaf.stock.stockportfolio.config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockPortfolioApplication

fun main(args: Array<String>) {
    runApplication<StockPortfolioApplication>(*args)
}
