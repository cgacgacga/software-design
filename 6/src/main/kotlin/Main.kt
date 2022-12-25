import tokenizer.Tokenizer
import visitors.CalcVisitor
import visitors.ParserVisitor
import visitors.PrintVisitor



fun main(args: Array<String>) {
    val expression = "(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2"
    val expTokenized = Tokenizer.tokenize(expression)
    val rpnInTokenList = ParserVisitor.visit(expTokenized)
    val rpnInString = PrintVisitor.visit(rpnInTokenList)
    val calcExpression = CalcVisitor.visit(rpnInTokenList)
    println(expTokenized.joinToString(" "))
    println(rpnInTokenList.joinToString(" "))
    println(rpnInString.joinToString(" "))
    println("$expression = $calcExpression")
}