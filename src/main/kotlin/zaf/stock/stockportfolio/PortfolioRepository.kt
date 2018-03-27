package zaf.stock.stockportfolio

import org.springframework.data.jpa.repository.JpaRepository

interface PortfolioRepository : JpaRepository<Portfolio, Long>{

    fun findByName(name: String): Portfolio
}

