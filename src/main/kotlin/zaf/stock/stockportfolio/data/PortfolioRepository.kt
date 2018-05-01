package zaf.stock.stockportfolio.data

import org.springframework.data.jpa.repository.JpaRepository
import zaf.stock.stockportfolio.model.Portfolio

interface PortfolioRepository : JpaRepository<Portfolio, Long>{

    fun findByName(name: String): Portfolio
}

