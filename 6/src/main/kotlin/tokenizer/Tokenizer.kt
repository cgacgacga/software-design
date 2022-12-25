package tokenizer

import tokens.*
import java.lang.IllegalArgumentException


object Tokenizer {
    private val EXPRESSION_STATE = ExpressionState()
    private val EOF_STATE = EofState()
    private var currentState: State = EXPRESSION_STATE
    private var tokens = mutableListOf<Token>()

    fun tokenize(expression: String): List<Token> {
        tokens.clear()
        currentState = EXPRESSION_STATE
        expression.forEach {
            currentState.handleChar(it)
        }
        currentState.handleEof()
        return tokens
    }

    private abstract class State {
        abstract fun handleChar(char: Char)
        open fun handleEof() {
            currentState = EOF_STATE
        }
    }

    private class ExpressionState : State() {
        override fun handleChar(char: Char) {
            when (char) {
                '+' -> tokens.add(Plus)
                '-' -> tokens.add(Minus)
                '*' -> tokens.add(Mul)
                '/' -> tokens.add(Div)
                '(' -> tokens.add(LeftBrace)
                ')' -> tokens.add(RightBrace)
                in '0'..'9' -> {
                    currentState = NumberState()
                    currentState.handleChar(char)
                }
                else -> {
                    if (!char.isWhitespace()) {
                        throw IllegalArgumentException("Unknown char: $char.")
                    }
                }
            }
        }
    }

    private class NumberState : State() {
        private var number = 0
        override fun handleChar(char: Char) {
            when (char) {
                in '0'..'9' -> {
                    number = number * 10 + char.toString().toInt()
                }
                else -> {
                    tokens.add(Number(number))
                    currentState = EXPRESSION_STATE
                    currentState.handleChar(char)
                }
            }
        }

        override fun handleEof() {
            tokens.add(Number(number))
        }
    }

    private class EofState : State() {
        override fun handleChar(char: Char) {}
    }
}