package visitors

import tokens.*
import tokens.Number

object CalcVisitor : TokenVisitor {
    private val calcStack = mutableListOf<Int>()

    fun visit(tokens: List<Token>): Int {
        tokens.forEach {
            it.accept(this)
        }
        if (calcStack.size != 1) {
            throw java.lang.IllegalArgumentException("Incorrect input data")
        }
        return calcStack.last()
    }

    override fun visit(token: Number) {
        calcStack.add(token.value)
    }

    override fun visit(token: Brace) {
        throw IllegalArgumentException("Input data must not contain brackets")
    }

    override fun visit(token: Operation) {
        if (calcStack.size < 2) {
            throw IllegalArgumentException("Incorrect input data")
        }
        val second = calcStack.removeLast()
        val first = calcStack.removeLast()
        when (token) {
            is Plus -> {
                calcStack.add(first + second)
            }
            is Minus -> {
                calcStack.add(first - second)
            }
            is Mul -> {
                calcStack.add(first * second)
            }
            is Div -> {
                calcStack.add(first / second)
            }
        }
    }
}