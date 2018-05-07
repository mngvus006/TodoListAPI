import java.io.File

import sbt.Keys._
import sbt.{Level, Resolver, _}
import sbtassembly.AssemblyPlugin.autoImport._
import sbtassembly.{MergeStrategy, PathList}
import scoverage.ScoverageKeys._

import scala.sys.process.Process

object Common {


  val DefaultOrganization = "com.allangray"
  val RootDirectory: String = file(".").getCanonicalPath
 // val GitCurrentBranch: String = sys.props.getOrElse("git.branch",
   // sys.env.getOrElse("CI_BUILD_VERSION_BRANCH", default = Process("git rev-parse --abbrev-ref HEAD").lineStream.head))


  lazy val Settings = Seq(
    organization := DefaultOrganization,
    version := "1.0.0",

    scalaVersion := Dependencies.ScalaVersion,
    scalacOptions ++= Seq("-feature", "-deprecation"),
    crossPaths := false,
    fork := false,
    updateOptions := updateOptions.value.withCachedResolution(true),
    // Required for Spring named constructor arguments
    javacOptions ++= Seq("-g:vars", "-encoding", "UTF-8"),
    coverageMinimum := 80,
    coverageFailOnMinimum := false,
    coverageExcludedPackages := "com.allangray.common.testinterfaces.*;com.allangray.cxfgenerator.*;com.allangray.dbdeploy.*;com.allangray.environment.*;com.allangray.exceptions.*;com.allangray.infrastructure.*;com.allangray.integrityverifier.*;com.allangray.monitoring.*;com.allangray.testinglog.*;com.allangray.*.Main;com.allangray.*Application;com.allangray.*Schema;"
  )


  lazy val PublishDisableSettings = Seq(
    publish := {},
    publishLocal := {}
  )

  lazy val PublishSettings = Seq(
    organization := DefaultOrganization + ".api",
    version := "SNAPSHOT-1.0.0",

    publishMavenStyle := false,
    isSnapshot := false,

    publishArtifact := true,
    publishArtifact in (Compile, packageBin) := true,
    publishArtifact in (Test, packageBin) := true,

    publishArtifact in (Compile, packageDoc) := true,
    publishArtifact in (Test, packageDoc) := false,

    publishArtifact in (Compile, packageSrc) := true,
    publishArtifact in (Test, packageSrc) := true,

    publishTo := Some(Resolver.file("file",  new File(s"$RootDirectory/dist/api-client")))
  )

  lazy val AssemblySettings = Seq(
    isSnapshot := false,

    logLevel in assembly := Level.Error,
    test in assembly := {},
    mainClass in assembly := None,
    mainClass in packageBin := None,
    mainClass in run := None,
    assemblyMergeStrategy in assembly := {
      case PathList("org", "xmlpull", "v1", _*) => MergeStrategy.first
      case x: Any => (assemblyMergeStrategy in assembly).value(x)
    },
    assemblyExcludedJars in assembly := {
      val exclusions = (fullClasspath in assembly).value filter { jar =>
        jar.data.getName == "c3p0-0.9.1.1.jar" ||
          jar.data.getName == "annotations-2.0.1.jar" ||
          jar.data.getName == "javax.inject-1.jar" ||
          jar.data.getName == "aopalliance-repackaged-2.5.0-b32.jar" ||
          jar.data.getName == "jsr311-api-1.1.1.jar"
      }
      sLog.value.warn(s"\rExclusions:\r${exclusions.mkString(";\r")}\r")
      exclusions
    }
  )




}
