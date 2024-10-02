package com.example.todo.controller;


import com.example.todo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TaskController {


    private final TaskService taskService;

//    public TaskController(TaskService taskService) {
//        this.taskService = taskService;
//    }

    @GetMapping("/tasks")
    public String list(Model model) {
        var taskList = taskService.find()
                .stream()
                .map(TaskDTO::toDTO)
                .toList();//entity kara DTO ni
        model.addAttribute("taskList", taskList);
        return "tasks/list";
    }

    @GetMapping("/tasks/{id}") //GET /tasks/$(id)
    public String showDetail(@PathVariable("id") long taskId, Model model) { //PathVariable 알아두기.값을 가져오는 기능
//        model.addAttribute( "taskId", taskId); 밑으로 내림
        //taskId >TaskEntity
        var taskEntity = taskService.findById(taskId) // findById를 지금 처음 씀. ; 지우고 컨톨 쉬프트 p 눌러보면 어디서 온 정보인지 알 수 있다
                .orElseThrow(() -> new IllegalArgumentException("Task not found: id = " + taskId)); // sql문에서 null이 뜰 때 이 내용이 뜸.
//        model.addAttribute( "taskID", taskEntity.id()); << 이건 task 의 iD 값만 보낼 때
        model.addAttribute( "task", TaskDTO.toDTO(taskEntity)); //<<이건 task 전부 다 보내려고 할때
        return "tasks/detail";
    }
}
