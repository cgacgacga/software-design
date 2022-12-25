package tokens

import visitors.TokenVisitor

sealed class Operation : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

object Plus : Operation() {
    override fun toString() = "PLUS"
}

object Minus: Operation() {
    override fun toString() = "MINUS"
}

object Mul: Operation() {
    override fun toString() = "MUL"
}

object Div: Operation() {
    override fun toString() = "DIV"
}
