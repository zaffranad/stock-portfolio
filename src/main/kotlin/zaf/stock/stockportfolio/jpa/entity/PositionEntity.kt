package zaf.stock.stockportfolio.jpa.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class PositionEntity(
        @Id @GeneratedValue var id: Long = 0
        , val isin: String
        , val volume: Number
        , val buyPrice: Number
)