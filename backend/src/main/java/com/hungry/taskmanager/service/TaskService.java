package com.hungry.taskmanager.service;

import com.hungry.taskmanager.dto.CreateTaskDTO;
import com.hungry.taskmanager.dto.EditTaskDTO;
import com.hungry.taskmanager.dto.QueryTaskDTO;
import com.hungry.taskmanager.entity.Task;

import org.springframework.lang.NonNull;

import java.math.BigInteger;
import java.util.List;

public interface TaskService {

    BigInteger addTask(CreateTaskDTO params) throws Exception;

    int deleteTask(@NonNull long taskId) throws Exception;

    List<Task> queryTask(QueryTaskDTO filter) throws Exception;

    void editTask(EditTaskDTO params) throws Exception;

}
