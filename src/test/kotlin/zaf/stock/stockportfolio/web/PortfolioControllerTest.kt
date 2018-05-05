package zaf.stock.stockportfolio.web

import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import zaf.stock.stockportfolio.portfolio.facade.PortfolioFacade
import zaf.stock.stockportfolio.portfolio.model.Portfolio

@RunWith(SpringRunner::class)
@WebMvcTest
class PortfolioControllerTest{

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var portfolioFacade: PortfolioFacade

    @Test
    fun getAll_Should_return_200_and_empty_list_when_no_portfolio_found() {
        this.mockMvc.perform(get("/portfolios/"))
                    .andDo(print())
                    .andExpect(status().isOk)
                    .andExpect(MockMvcResultMatchers.content().string("[]"))

    }

    @Test
    fun getAll_Should_return_200_and_list_of_portfolio_when_portfolios_found() {

        Mockito.`when`(portfolioFacade.getAll())
                .thenReturn(
                        listOf(
                                Portfolio("ptf1"),
                                Portfolio("ptf2")
                        )
                )

        this.mockMvc.perform(get("/portfolios/"))
                    .andDo(print())
                    .andExpect(status().isOk)
                    .andExpect(
                            MockMvcResultMatchers.content()
                                    .string(CoreMatchers.allOf(
                                            CoreMatchers.containsString("ptf1"),
                                            CoreMatchers.containsString("ptf2")

                                    ))
                    )

    }
}