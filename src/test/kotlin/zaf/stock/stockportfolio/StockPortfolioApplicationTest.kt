package zaf.stock.stockportfolio

import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import zaf.stock.stockportfolio.web.PortfolioController

@RunWith(SpringRunner::class)
@SpringBootTest
class StockPortfolioApplicationTest{

	@Autowired
	lateinit var portfolioController: PortfolioController

	@Test
	fun contextLoads() {
		Assertions.assertThat(portfolioController).isNotNull
	}

}
