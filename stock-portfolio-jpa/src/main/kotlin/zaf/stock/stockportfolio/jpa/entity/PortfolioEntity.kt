package zaf.stock.stockportfolio.jpa.entity

import java.util.*
import javax.persistence.*

@Entity
class PortfolioEntity (
        @Id @GeneratedValue var id: Long = 0
        , var name: String = ""
        , var creation: Date = Date()
        , @OneToMany(cascade = [(CascadeType.ALL)])
        var positions: MutableList<PositionEntity> = mutableListOf()
){
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as PortfolioEntity

                if (id != other.id) return false
                if (name != other.name) return false
                if (creation != other.creation) return false
                if (positions != other.positions) return false

                return true
        }

        override fun hashCode(): Int {
                var result = id.hashCode()
                result = 31 * result + name.hashCode()
                result = 31 * result + creation.hashCode()
                result = 31 * result + positions.hashCode()
                return result
        }

        override fun toString(): String {
                return "PortfolioEntity(id=$id, name='$name', creation=$creation, positions=$positions)"
        }
}

