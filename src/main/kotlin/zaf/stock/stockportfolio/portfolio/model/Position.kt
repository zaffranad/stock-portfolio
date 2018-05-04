package zaf.stock.stockportfolio.portfolio.model

class Position(
        val isin: String
        , val volume: Number
        , val buyPrice: Number
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (isin != other.isin) return false
        if (volume != other.volume) return false
        if (buyPrice != other.buyPrice) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isin.hashCode()
        result = 31 * result + volume.hashCode()
        result = 31 * result + buyPrice.hashCode()
        return result
    }

    override fun toString(): String {
        return "Position(isin='$isin', volume=$volume, buyPrice=$buyPrice)"
    }
}