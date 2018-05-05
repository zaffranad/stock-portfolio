package zaf.stock.stockportfolio.web

import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import zaf.stock.stockportfolio.portfolio.exception.PortfolioOperationException
import zaf.stock.stockportfolio.portfolio.facade.PortfolioFacade
import zaf.stock.stockportfolio.portfolio.model.Portfolio

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PortfolioControllerTestIntegration {

    private final inline fun <reified T : Any> typeRef(): ParameterizedTypeReference<T> = object : ParameterizedTypeReference<T>() {}

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @MockBean
    lateinit var portfolioFacade: PortfolioFacade

    @Test
    fun getAllPortfolios_Should_return_empty_list_when_no_portfolios_exists() {

        // given
        Mockito.`when`(portfolioFacade.getAll()).thenReturn(emptyList())

        // when
        val results = testRestTemplate.getForEntity(
                "http://localhost:$port/portfolios/",
                List::class.java
        )

        //then
        Assertions.assertThat(
                results.statusCode
        ).isEqualTo(HttpStatus.OK)

        Assertions.assertThat(
                results.body
        ).isEmpty()
    }

    @Test
    fun getAllPortfolios_Should_return_all_portfolios_when_portfolios_exists() {

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
        Assertions.assertThat(
                results.statusCode
        ).isEqualTo(HttpStatus.OK)

        Assertions.assertThat(
                results.body
        ).isNotEmpty
                .extracting("name")
                .containsExactly(name1, name2)

    }

    @Test
    fun getAllPortfolios_Should_return_5OO_when_exception_while_processing() {

        // given
        Mockito.`when`(portfolioFacade.getAll()).thenThrow(PortfolioOperationException())

        // when
        val response = testRestTemplate.getForEntity(
                "http://localhost:$port/portfolios/",
                Any::class.java
        )

        //then
        Assertions.assertThat(
                response.statusCode
        ).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)

    }
}