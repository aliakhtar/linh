package actor.ali.linh.sempre

import actor.ali.linh.config.Env

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
    private val builder = new Builder
    private val dataset = new Dataset
    private val grammar = new Grammar
    //private val analyzer = new CoreNLPAnalyzer
    private val analyzer = new SimpleAnalyzer
    LanguageAnalyzer.setSingleton(this.analyzer)

    builder.grammar = grammar
    builder.buildUnspecified()


    def parse(query: String):SempreResponse= {
        val b: Example.Builder = new  Example.Builder()
        b.setId("session:1")
        b.setUtterance(query)
        val ex: Example = b.createExample
        val response: SempreResponse = new SempreResponse(builder)

        ex.preprocess()

        // Parse!
        builder.parser.parse(builder.params, ex, false)
        response.ex = ex
        response.candidateIndex = 0

        response

    }
}
