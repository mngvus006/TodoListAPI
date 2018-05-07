package com.allangray.todolist.service


import com.allangray.common.Application
import com.allangray.common.dbdeploy.DatabaseDeployConfig

object Main extends App {
  Application.run(args, () => new TodoListApplication, Seq(
    DatabaseDeployConfig("TodoListDB", "DB_TODO", Array("dbo")))
  )
}
