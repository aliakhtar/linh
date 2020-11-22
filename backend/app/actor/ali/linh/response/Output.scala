package actor.ali.response

import actor.ali.search.model.Question

case class Output(content: Seq[OutputItem], suggestions: Option[Suggestions] = None)

case class OutputItem(kind: OutputKind,
                      text: Option[String])



case class Suggestions(label: String, items: Seq[String])



case object Output {
    def single(o: OutputItem):Output = Output(Seq(o))
    def single(o: OutputItem, s: Suggestions):Output = Output(Seq(o), Some(s))

    def answersOf(q: Question, suggestions: Option[Suggestions] = None):Output = {
        val answers = q.answers.map(OutputItem.text)
        Output(answers, suggestions)
    }
}

case object OutputItem {
    def text(text: String):OutputItem = OutputItem(OutputKind.PLAIN, Some(text))

    def logg(text: String):OutputItem = OutputItem(OutputKind.LOG, Some(text))

    def success(text: String):OutputItem = OutputItem(OutputKind.SUCCESS, Some(text))

    def error(text: String):OutputItem = OutputItem(OutputKind.ERROR, Some(text))
}