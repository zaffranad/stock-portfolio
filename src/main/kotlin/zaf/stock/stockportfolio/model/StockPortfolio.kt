package zaf.stock.stockportfolio.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class StockPortfolio(
        @Id @GeneratedValue var id: Long = 0
        , val isin: String
        , val volume: Number
        , val buyPrice: Number
)