package parsers.solutions

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

    val selector: Parser[Selector] = """[^{;]+""".r ^^ { s => Selector(s) }
    val property: Parser[Property] = """[\w\-]+:[^;]+;""".r ^^ { p => Property(p) }
    val block: Parser[Block] = (selector ~ ("{" ~> (statements*) <~ "}")) ^^ { case s ~ ss => Block(s, ss) }
    def statements: Parser[Statement] = property | block
    val document: Parser[Document] = (block*) ^^ { bs => Document(bs) }

    def parse(input: String): ParseResult[Document] = parse(document, input)
  }


  // --- Code generator

  object LessCompiler {

    lazy val parser = new LessParser

    def compile(input: String) = parser.parse(input).map(emitDocument)

    def emitDocument(document: Document) = (document.blocks map (block => compileBlock(block))).mkString

    private def compileBlock(block: Block, prefix: String = ""): String = {
      ("""|%s {
          |  %s
          |}""".stripMargin.format(prefix + block.selector.value, (for (prop <- block.properties) yield prop.value).mkString("\n"))
      + (for (sub <- block.subBlocks) yield {
        compileBlock(sub, block.selector.value + " ")
      }).mkString("\n"))
    }
  }

}