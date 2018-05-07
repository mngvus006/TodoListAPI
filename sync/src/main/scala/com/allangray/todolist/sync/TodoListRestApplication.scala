package com.allangray.todolist.sync

import com.allangray.common.rest.RestApplication
import com.allangray.serviceinterfaces.configuration.ICommonDependencies
import com.allangray.serviceinterfaces.logging.ILogManager
import com.allangray.todolist.core.repository.data.SquerylTodoListRepository
import com.allangray.todolist.sync.resource.TodoListResource

class TodoListRestApplication(commonDependencies: ICommonDependencies, squerylTodoListRepository : SquerylTodoListRepository, port: Int, logManager: ILogManager) extends RestApplication( commonDependencies,
  port,
  logManager,
  Seq(new TodoListResource(commonDependencies,squerylTodoListRepository)), "1.0",
  "/api/v1/todo") {

  override def getName: String = "todoList-api"
}
