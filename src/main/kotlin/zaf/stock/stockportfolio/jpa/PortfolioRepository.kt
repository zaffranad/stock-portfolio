package zaf.stock.stockportfolio.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zaf.stock.stockportfolio.jpa.entity.PortfolioEntity

@Repository
interface PortfolioRepository : JpaRepository<PortfolioEntity, String>{

    fun findByName(name: String): PortfolioEntity
    
}