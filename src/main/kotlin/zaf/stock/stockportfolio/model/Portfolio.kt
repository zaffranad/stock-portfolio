package zaf.stock.stockportfolio.model

import java.util.*
import javax.persistence.*

@Entity
class Portfolio(
        @Id @GeneratedValue var id: Long = 0
        , val name: String
        , val creation: Date = Date()
        , @OneToMany(cascade = arrayOf(CascadeType.ALL))
        val stocks: MutableList<StockPortfolio> = mutableListOf()
)




