
name := "TodoListAPI"

version := "0.1"

scalaVersion := "2.12.4"


lazy val Api = Project("api", file("api"))
  .settings(Common.Settings: _*)
  .settings(Common.PublishSettings: _*)
  .disablePlugins(sbtassembly.AssemblyPlugin)
  .settings(
    name := "todolist",
    libraryDependencies ++= Seq(Dependencies.apiCommonPrimitives))

lazy val Core = Project("core", file("core"))
  .dependsOn(Api)
  .settings(Common.Settings: _*)
  .settings(Common.PublishDisableSettings: _*)
  .disablePlugins(sbtassembly.AssemblyPlugin)
  .settings(
    name := "todolist-core",
    libraryDependencies ++= Seq(
      Dependencies.apiCommonInterfaces,
      Dependencies.apiCommonTestImplementations % Test,
      Dependencies.oracleThinClient,
      Dependencies.jtds % Test,
      Dependencies.apiCommonImplementations % "test->test"))

lazy val Sync = Project("sync", file("sync"))
  .dependsOn(
    Core % "test->test;compile->compile",
    Api % "test->test")
  .settings(Common.Settings: _*)
  .settings(Common.PublishDisableSettings: _*)
  .disablePlugins(sbtassembly.AssemblyPlugin)
  .settings(
    name := "todolist-sync",
    libraryDependencies ++= Seq(
      Dependencies.apiCommonRest,
      Dependencies.apiCommonBusinessMappers))

lazy val Main = Project("main", file("main"))
  .dependsOn(Sync)
  .settings(Common.Settings: _*)
  .settings(Common.PublishDisableSettings: _*)
  .settings(Common.AssemblySettings: _*)
  .enablePlugins(sbtassembly.AssemblyPlugin)
  .settings(
    name := "api-todolist",
    assemblyJarName in assembly := "service.jar",
    assemblyOutputPath in assembly := file("dist/service.jar"),
    mainClass in assembly := Some("com.allangray.todolist.service.Main"),
    assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false),
    libraryDependencies ++= Seq(Dependencies.apiCommonImplementations))

lazy val apiBank: Project = project.in(file("."))
  .aggregate(
    Api,
    Core,
    Sync,
    Main)
  .disablePlugins(sbtassembly.AssemblyPlugin)
  .settings(Common.PublishDisableSettings: _*)
  .settings(
    scalaVersion := Dependencies.ScalaVersion,
    aggregate in update := false)