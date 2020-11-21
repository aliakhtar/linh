package actor.ali.linh.sempre

import actor.ali.linh.config.Env
import actor.ali.linh.input.Command

import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import edu.stanford.nlp.sempre._
import edu.stanford.nlp.sempre.corenlp.CoreNLPAnalyzer
import com.typesafe.scalalogging.Logger
import edu.stanford.nlp.sempre.Master
import edu.stanford.nlp.sempre.Master._


class SempreClient(env: Env) {
    private val log = Logger[SempreClient]

    log.info(s"Grammar file: ${env.conf.sempre.grammarFile}");

    JavaExecutor.opts.classPathPrefix = "actor.ali.linh.input"


    private val builder = new Builder
    private val dataset = new Dataset
    private val grammar = new Grammar
    //private val analyzer = new CoreNLPAnalyzer
    private val analyzer = new SimpleAnalyzer
    LanguageAnalyzer.setSingleton(this.analyzer)

    grammar.read(env.conf.sempre.grammarFile)
    builder.grammar = grammar
    builder.buildUnspecified()


    private def parseRaw(query: String):SempreResponse= {
        val b: Example.Builder = new  Example.Builder()
        b.setId("session:1")
        b.setUtterance( Preprocessor.process(query))
        val ex: Example = b.createExample
        val response: SempreResponse = new SempreResponse(builder)

        ex.preprocess()

        // Parse!
        builder.parser.parse(builder.params, ex, false)
        response.ex = ex
        response.candidateIndex = 0

        response
    }

    def parse(cmd: String):Option[Command] = {
        if (cmd.isBlank)
            return None

        val resp = parseRaw(cmd)
        val predictions = resp.getExample.predDerivations.asScala.toSeq

        if (predictions.isEmpty) {
            log.warn(s"No predictions for $cmd");
            return None
        }

        resp.getDerivation.value match {
            case CommandValue(cmd) => Some(cmd)
            case other =>
                log.warn(s"Unexpected result: $other");
                None
        }
    }
}
