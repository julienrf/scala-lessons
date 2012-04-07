package parsers.exercises

trait Exercise5 {

  // --- AST Definition

  /* A CSS selector, e.g. “div > span.foo” */
  case class Selector(value: String)

  sealed trait Statement

  /* A CSS property, e.g. “color: white;” */
  case class Property(value: String) extends Statement

  /* A CSS block applies a set of properties to a selector */
  case class Block(selector: Selector, statements: List[Statement]) extends Statement {
    lazy val properties = statements collect { case p: Property => p }
    lazy val subBlocks = statements collect { case s: Block => s }
  }

  case class Document(blocks: List[Block])


  // --- Parser

  import util.parsing.combinator.RegexParsers

  class LessParser extends RegexParsers {

    val selector: Parser[Selector] = sys.error("TODO")
    val property: Parser[Property] = sys.error("TODO")
    val block: Parser[Block] = sys.error("TODO")
    def statements: Parser[Statement] = sys.error("TODO")
    val document: Parser[Document] = sys.error("TODO")

    def parse(input: String): ParseResult[Document] = parse(document, input)
  }


  // --- Code generator

  object LessCompiler {

    lazy val parser = new LessParser

    def compile(input: String) = parser.parse(input).map(emitDocument)

    def emitDocument(document: Document) = (document.blocks map (block => emitBlock(block))).mkString

    private def emitBlock(block: Block, prefix: String = ""): String = sys.error("TODO")
  }

}