package zaf.stock.stockportfolio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@SpringBootApplication
class StockPortfolioApplication

fun main(args: Array<String>) {
    runApplication<StockPortfolioApplication>(*args)
}
