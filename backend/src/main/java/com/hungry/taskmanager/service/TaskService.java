package com.hungry.taskmanager.service;

import com.hungry.taskmanager.dto.*;
import com.hungry.taskmanager.entity.Result;
import com.hungry.taskmanager.entity.Task;

import org.springframework.lang.NonNull;

import java.math.BigInteger;
import java.util.List;

public interface TaskService {

    BigInteger addTask(CreateTaskDTO params) throws Exception;

    int deleteTask(@NonNull long taskId) throws Exception;

    List<Task> queryTask(QueryTaskDTO filter) throws Exception;

    void editTask(EditTaskDTO params) throws Exception;

    Result assignTask(AssignTaskDTO assignTaskDTO, String username);

    Result editPrivilege(EditPrivilegeDTO editPrivilegeDTO);


    Result editStatus(EditStatusDTO editStatusDTO);

    Result editTaskName(EditTaskNameDTO editTaskNameDTO);
}
