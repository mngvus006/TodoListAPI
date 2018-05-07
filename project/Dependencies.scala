import sbt._

object Dependencies {
  val ScalaVersion = "2.12.4"

  private object VersionNumbers {
    val ApiCommon = "20.0.5"
    val ApiCommonPrimitives = "7.0.1"
  }

  val apiCommonPrimitives: ModuleID = "com.allangray.common" % "common-primitives" % VersionNumbers.ApiCommonPrimitives
  val apiCommonImplementations: ModuleID = "com.allangray.common" % "common-implementations" % VersionNumbers.ApiCommon exclude("javax.mail", "mail") exclude("org.commonjava.googlecode.markdown4j", "markdown4j")
  val apiCommonInterfaces: ModuleID = "com.allangray.common" % "common-interfaces" % VersionNumbers.ApiCommon
  val apiCommonRest: ModuleID = "com.allangray.common" % "common-rest" % VersionNumbers.ApiCommon exclude("javax.ws.rs", "jsr311-api")
  val apiCommonBusinessMappers: ModuleID = "com.allangray.common" % "common-business-mappers" % VersionNumbers.ApiCommon
  val apiCommonTestImplementations: ModuleID = "com.allangray.common" % "common-test-implementations" % VersionNumbers.ApiCommon

  val oracleThinClient: ModuleID = "com.oracle" % "ojdbc6" % "11.2.0.3"
  val jtds: ModuleID = "net.sourceforge.jtds" % "jtds" % "1.3.1"
}
