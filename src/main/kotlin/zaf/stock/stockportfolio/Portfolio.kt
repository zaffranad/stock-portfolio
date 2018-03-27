package zaf.stock.stockportfolio

import java.util.*
import javax.persistence.*

@Entity
class Portfolio(
        @Id @GeneratedValue var id: Long = 0
        , val name: String
        , val creation: Date = Date()
        , @OneToMany(cascade = arrayOf(CascadeType.ALL)) val positions: MutableList<StockPosition> = mutableListOf()
)

@Entity
class StockPosition(
        @Id @GeneratedValue var id: Long = 0
        , val isin: String
        , val volume: Number
        , val buyPrice: Number
)
