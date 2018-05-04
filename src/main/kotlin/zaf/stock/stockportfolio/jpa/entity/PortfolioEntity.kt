package zaf.stock.stockportfolio.jpa.entity

import java.util.*
import javax.persistence.*

@Entity
class PortfolioEntity (
        @Id @GeneratedValue var id: Long = 0
        , val name: String
        , val creation: Date = Date()
        , @OneToMany(cascade = [(CascadeType.ALL)])
        val positions: MutableList<PositionEntity> = mutableListOf()
)
