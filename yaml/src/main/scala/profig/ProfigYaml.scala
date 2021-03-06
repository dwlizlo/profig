package profig

import io.circe.Json
import moduload.Moduload
import io.circe.yaml.parser._

import scala.concurrent.{ExecutionContext, Future}

object ProfigYaml extends Moduload with ProfigJson {
  override def load()(implicit ec: ExecutionContext): Future[Unit] = {
    ProfigJson.register(this, "yaml", "yml")
    Future.successful(())
  }

  override def error(t: Throwable): Unit = throw t

  override def apply(content: String): Json = parse(content) match {
    case Left(failure) => throw new RuntimeException(s"Unable to parse $content (YAML) to JSON.", failure)
    case Right(value) => value
  }
}
