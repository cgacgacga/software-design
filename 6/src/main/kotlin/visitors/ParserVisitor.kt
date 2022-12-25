package visitors

import tokens.*
import tokens.Number
import java.lang.IllegalArgumentException

object ParserVisitor : TokenVisitor {
    private val reversePolishNotationTokens = mutableListOf<Token>()
    private val stackTokens = mutableListOf<Token>()

    fun visit(tokens: List<Token>): List<Token> {
        tokens.forEach{ token ->
            token.accept(this)
        }
        while (stackTokens.isNotEmpty()) {
            reversePolishNotationTokens.add(stackTokens.removeLast())
        }
        return reversePolishNotationTokens
    }

    override fun visit(token: Number) {
        reversePolishNotationTokens.add(token)
    }

    override fun visit(token: Brace) {
        when(token) {
            is LeftBrace -> {
                stackTokens.add(token)
            }
            is RightBrace -> {
                while (stackTokens.isNotEmpty() && stackTokens.last() !is LeftBrace) {
                    reversePolishNotationTokens.add(stackTokens.removeLast())
                }
                if (stackTokens.isEmpty()) {
                    throw IllegalArgumentException("Incorrect expression. Not found left bracket.")
                } else {
                    stackTokens.removeLast()
                }
            }
        }
    }

    override fun visit(token: Operation) {
        when(token) {
            is Plus, is Minus -> {
                if (stackTokens.isNotEmpty() && stackTokens.last() !is LeftBrace) {
                    reversePolishNotationTokens.add(stackTokens.removeLast())
                }
                stackTokens.add(token)
            }
            is Mul, is Div -> {
                if (stackTokens.isNotEmpty() && (stackTokens.last() is Mul || stackTokens.last() is Div)) {
                    reversePolishNotationTokens.add(stackTokens.removeLast())
                }
                stackTokens.add(token)
            }
        }
    }
}