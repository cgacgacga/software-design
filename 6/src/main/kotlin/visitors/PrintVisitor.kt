package visitors

import tokens.*
import tokens.Number

object PrintVisitor : TokenVisitor {
    private val printStack = mutableListOf<String>()

    fun visit(tokens: List<Token>): List<String> {
        tokens.forEach {
            it.accept(this)
        }
        return printStack
    }

    override fun visit(token: Number) {
        printStack.add(token.value.toString())
    }

    override fun visit(token: Brace) {
        throw IllegalArgumentException("Incorrect input data")
    }

    override fun visit(token: Operation) {
        when (token) {
            is Plus -> printStack.add("+")
            is Minus -> printStack.add("-")
            is Mul -> printStack.add("*")
            is Div -> printStack.add("/")
        }
    }
}