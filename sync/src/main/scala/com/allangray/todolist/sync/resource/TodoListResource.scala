package com.allangray.todolist.sync.resource

import javax.ws.rs._
import javax.ws.rs.core.MediaType

import com.allangray.common.rest.RestResource
import com.allangray.serviceinterfaces.configuration.ICommonDependencies
import com.allangray.serviceinterfaces.logging.ILogger
import com.allangray.todolist.core.repository.data._
import com.codahale.metrics.annotation.ExceptionMetered
import io.swagger.annotations.{Api, ApiOperation}

@Path("/TodoList-Resource")
@Produces(Array(MediaType.APPLICATION_JSON))
@Api(value = "ToDoLIst REST API")
class TodoListResource(override val commonDependencies: ICommonDependencies,squerylTodoListRepository : SquerylTodoListRepository) extends RestResource  {

  protected val logger: ILogger = commonDependencies.getLogManager.getLogger(getClass)

  @POST
  @Path("/addTask/{task}")
  @ApiOperation(value = "Add a new task")
  def addTask(@PathParam("task") task: String) = {
    squerylTodoListRepository.addTask(task)
  }

  @DELETE
  @Path("/deleteTask/{id}")
  @ApiOperation(value = "Removes a done task")
  def deleteTask(@PathParam("id") id: Int) = {
    squerylTodoListRepository.deleteTask(id)
  }

  @PUT
  @Path("/setCompleted/{status}/{id}")
  @ApiOperation(value = "Mark task as completed")
  def setCompleted(@PathParam("status") status: Int, @PathParam("id") id: Int) = {
    squerylTodoListRepository.setCompleted(status, id)
  }

  @PUT
  @Path("/setActivated/{status}/{id}")
  @ApiOperation(value = "Mark task as activated")
  def setActivated(@PathParam("status") status: Int, @PathParam("id") id: Int) = {
    squerylTodoListRepository.setActivated(status, id)
  }

  @GET
  @Path("/getActivatedTasks/{status}")
  // @Produces({"application/json", "application/xml"})
  @ApiOperation(value = "Retrieve all activated tasks", produces = MediaType.APPLICATION_JSON)
  def getActivated(@PathParam("status") status: Int): List[Todo] = {
    squerylTodoListRepository.getActivated(status)
  }

  @GET
  @Path("/getCompletedTasks/{status}")
  @ApiOperation(value = "Retrieve all completed tasks", produces = MediaType.APPLICATION_JSON)
  def getCompleted(@PathParam("s              tatus") status: Int): List[Todo] = {
    squerylTodoListRepository.getCompleted(status)
  }

  @GET
  @Path("/getTodoList/")
  @ApiOperation(value = "Retrieve all tasks noted", produces = MediaType.APPLICATION_JSON)
  def getTodoList(): List[Todo] = {
    squerylTodoListRepository.getTodoList()
  }

  @GET
  @Path("/hello/")
  @ExceptionMetered(name = "hello-errors")
  @ApiOperation(value = "Say Hello World!", notes = "Greets the World.", response = classOf[String], produces = MediaType.TEXT_PLAIN)
  def helloWorld: String = {
    "Hello World"
  }
}