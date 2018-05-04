package zaf.stock.stockportfolio.portfolio.model

import java.util.*

class Portfolio(
        val name: String
        , val creation: Date = Date()
        , val positions: MutableList<Position> = mutableListOf()
)




