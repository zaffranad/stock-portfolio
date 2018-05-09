package zaf.stock.stockportfolio.web

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import zaf.stock.stockportfolio.portfolio.exception.PortfolioOperationException
import zaf.stock.stockportfolio.portfolio.facade.PortfolioFacade
import zaf.stock.stockportfolio.portfolio.model.Portfolio
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PortfolioControllerIntegrationTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @MockBean
    lateinit var portfolioFacade: PortfolioFacade

    @Test
    fun getAllPortfolios_noPortfoliosFound_200AndEmptyBody() {

        // given
        Mockito.`when`(portfolioFacade.getAll()).thenReturn(emptyList())

        // when
        val results = testRestTemplate.getForEntity(
                "http://localhost:$port/portfolios/",
                List::class.java
        )

        //then
        assertThat(
                results.statusCode
        ).isEqualTo(HttpStatus.OK)

        assertThat(
                results.body
        ).isEmpty()
    }

    @Test
    fun getAllPortfolios_portfoliosFound_200AndAllPortfolios() {

        // given
        val name1 = "nnn"
        val name2 = "ooo"
        Mockito.`when`(portfolioFacade.getAll()).thenReturn(listOf(
                Portfolio(name1),
                Portfolio(name2)
        ))

        // when
        val results = testRestTemplate.getForEntity(
                "http://localhost:$port/portfolios/",
                List::class.java
        )

        //then
        assertThat(
                results.statusCode
        ).isEqualTo(HttpStatus.OK)

        assertThat(
                results.body
        ).isNotEmpty
                .extracting("name")
                .containsExactly(name1, name2)

    }

    @Test
    fun getAllPortfolios_PortfolioOperationException_500() {

        // given
        Mockito.`when`(portfolioFacade.getAll()).thenThrow(PortfolioOperationException())

        // when
        val response = testRestTemplate.getForEntity(
                "http://localhost:$port/portfolios/",
                ExceptionEntity::class.java
        )

        //then
        assertThat(
                response.statusCode
        ).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)

        val body: ExceptionEntity = response.body!!
        assertThat(body).isNotNull
        assertThat(body.message).isEqualTo("Exception while retrieving all portfolios")
    }

    @Test
    fun getPortfolio_portfolioFound_200AndPortfolio() {

        // given
        val portfolioName = "PTFTEST"
        Mockito.`when`(portfolioFacade.get(portfolioName))
                .thenReturn(
                        Optional.of(Portfolio(portfolioName, Date(), mutableListOf()))
                )

        // when
        val results = testRestTemplate.getForEntity(
                "http://localhost:$port/portfolios/portfolio/$portfolioName",
                Portfolio::class.java
        )

        //then
        assertThat(
                results.statusCode
        ).isEqualTo(HttpStatus.OK)

        val body: Portfolio = results.body!!
        assertThat(
                body.name
        ).isEqualTo(portfolioName)
    }

    @Test
    fun getPortfolio_portfolioNotFound_404AndEmptyBody() {
        // given
        Mockito.`when`(portfolioFacade.get(Mockito.anyString())).thenReturn(Optional.empty())
        val portfolioName = "PTFTEST"

        // when
        val results = testRestTemplate.getForEntity(
                "http://localhost:$port/portfolios/portfolio/$portfolioName",
                ExceptionEntity::class.java
        )

        //then
        assertThat(
                results.statusCode
        ).isEqualTo(HttpStatus.NOT_FOUND)

        val body: ExceptionEntity = results.body!!
        assertThat(
                body.message
        ).isEqualTo("portfolio with name $portfolioName not found")
    }

    @Test
    fun getPortfolio_PortfolioOperationException_500() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}