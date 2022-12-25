package tokens

import visitors.TokenVisitor

data class Number(val value : Int) : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString() = "NUMBER($value)"
}