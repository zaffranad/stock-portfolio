package zaf.stock.stockportfolio.web.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
class PortfolioCreationException(message: String = "") : RuntimeException(message)