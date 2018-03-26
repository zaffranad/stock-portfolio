package zaf.stock.stockportfolio

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Portfolio(
        @Id val id: String ?= null
        , val name: String
        , val creation: Date = Date()
        , val positions: MutableList<StockPosition> = mutableListOf()
)

class StockPosition(
        val isin: String,
        val volume: Number,
        val buyPrice: Number
)
