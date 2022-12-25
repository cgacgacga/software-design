package tokens

import visitors.TokenVisitor

sealed class Brace: Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

object LeftBrace: Brace() {
    override fun toString() = "LEFT"
}

object RightBrace: Brace() {
    override fun toString() = "RIGHT"
}