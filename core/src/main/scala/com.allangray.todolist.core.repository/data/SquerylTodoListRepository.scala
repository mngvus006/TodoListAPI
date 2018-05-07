package com.allangray.todolist.core.repository.data

import com.allangray.common.database.SessionExecutor
import com.allangray.common.database.SquerylCustomTypes._
import org.squeryl.Schema



  case class Todo(id:Int, task: String,active: Int, completed: Int) {}
  case class TodoWithoutId(task: String,active: Int, completed: Int) {}

  object TodoListSchema extends Schema {
    // Setup the TodoListSchema class to be mapped to the "TodoList" table in SQL Server
    val todoList = table[Todo]("Todo")
    val todoListWithoutId = table[TodoWithoutId]("Todo")
  }

  trait SquerylTodoListRepository  extends SessionExecutor  {
    val databaseConnectionUrl = "jdbc:jtds:sqlserver://localhost;DatabaseName=TodoListDB"
    val databaseUsername = "SA"
    val databasePassword = "Skeleton10111"

    def addTask(task : String) =
      usingSession(){  () =>
        TodoListSchema.todoListWithoutId.insert(new TodoWithoutId(task, 0, 0))
      }

    def deleteTask(id: Int) =
      usingSession(){  () =>
        TodoListSchema.todoList.deleteWhere(x => x.id === id)
      }
    // status = 2 is checked and status = 1 is uncheck
    def setCompleted(status: Int, id: Int) =
      usingSession(){  () =>
        update(TodoListSchema.todoList)(listItem => where(listItem.id === id) set (listItem.completed := status))
      }

    def setActivated(status: Int, id: Int) =
      usingSession(){  () =>
        update(TodoListSchema.todoList)(listItem => where(listItem.id === id) set (listItem.active := status))
      }

    def getActivated(status: Int): List[Todo] =
      usingSession(){  () =>
        from(TodoListSchema.todoList)(listItem => where(listItem.active === status) select (listItem)).toList
      }

    def getCompleted(status: Int): List[Todo] =
      usingSession(){  () =>
        from(TodoListSchema.todoList)(listItem => where(listItem.completed === status) select (listItem)).toList
      }

    def getTodoList(): List[Todo] =
      usingSession(){  () =>
        from(TodoListSchema.todoList)( x => select (x) ).toList
      }


}
