package com.hungry.taskmanager.dto;

import com.hungry.taskmanager.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private BigInteger teamId;
    private String teamName;
    private String description;
    private String creatTime;
    private String creator;
    private List<String> admins;
    private List<String> members;
}