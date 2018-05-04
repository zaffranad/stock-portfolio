package zaf.stock.stockportfolio.portfolio.model

import java.util.*

class Portfolio(
        val name: String
        , val creation: Date = Date()
        , val positions: MutableList<Position> = mutableListOf()
)
{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Portfolio

        if (name != other.name) return false
        if (creation != other.creation) return false
        if (positions != other.positions) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + creation.hashCode()
        result = 31 * result + positions.hashCode()
        return result
    }

    override fun toString(): String {
        return "Portfolio(name='$name', creation=$creation, positions=$positions)"
    }
}




