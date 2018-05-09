package zaf.stock.stockportfolio.web

import java.util.*

class ExceptionEntity(val timestamp: Date, val status:Int, val error: String, val message: String, val path: String)