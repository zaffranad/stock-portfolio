package zaf.stock.stockportfolio.web.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_MODIFIED)
class PortfolioStockAddException(message: String = "") : RuntimeException(message)

