package visitors

import tokens.Brace
import tokens.Number
import tokens.Operation


interface TokenVisitor {
    fun visit(token: Number)
    fun visit(token: Brace)
    fun visit(token: Operation)
}