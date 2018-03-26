package zaf.stock.stockportfolio

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Portfolio(@Id val id: String ?= null, val name: String)
