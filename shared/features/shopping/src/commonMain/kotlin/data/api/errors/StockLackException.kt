package data.api.errors

class StockLackException(name: String): Exception("Infelizmente o produto $name esgotou, que tal escolher um novo produto? ;)")