package com.allangray.todolist.service
import com.allangray.todolist.core.repository.data.SquerylTodoListRepository
import com.allangray.todolist.sync.TodoListRestApplication
import com.allangray.common.{Application, ApplicationSettings}
import com.allangray.common.database.Repository


class TodoListApplication extends ApplicationSettings with Application {

  protected override def processName: String = "api-todolist"

  private val coreTodoListRepository = new Repository(instrumentationFactory,logManager, createSqlConnectionFactory("DB_TODO")) with SquerylTodoListRepository

  private val restService = new TodoListRestApplication(commonDependencies, coreTodoListRepository, setting("TO_DO_LIST_PORT").toInt,logbackLogManager)

  override def start(args: Seq[String]): Unit = restService.start()

  override def stop(): Unit = {}

}
