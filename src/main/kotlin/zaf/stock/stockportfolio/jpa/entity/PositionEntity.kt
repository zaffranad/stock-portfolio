package zaf.stock.stockportfolio.jpa.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class PositionEntity(
        @Id @GeneratedValue var id: Long = 0
        , var isin: String
        , var volume: Number
        , var buyPrice: Number
){


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PositionEntity

        if (id != other.id) return false
        if (isin != other.isin) return false
        if (volume != other.volume) return false
        if (buyPrice != other.buyPrice) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + isin.hashCode()
        result = 31 * result + volume.hashCode()
        result = 31 * result + buyPrice.hashCode()
        return result
    }

    override fun toString(): String {
        return "PositionEntity(id=$id, isin='$isin', volume=$volume, buyPrice=$buyPrice)"
    }
}