package com.hungry.taskmanager.controller;

import com.alibaba.fastjson.JSON;
import com.hungry.taskmanager.dto.CreateTeamDTO;
import com.hungry.taskmanager.dto.EditTaskDTO;
import com.hungry.taskmanager.dto.EditTeamDTO;
import com.hungry.taskmanager.dto.OppoTeamMemberDTO;
import com.hungry.taskmanager.entity.Result;
import com.hungry.taskmanager.service.TeamService;
import com.hungry.taskmanager.utils.JWTUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;

@RestController
@RequestMapping("/api/team")
@CrossOrigin
public class TeamController {
    @Resource
    TeamService teamService;

    @PostMapping("/createteam")
    @ApiOperation(value = "创建小组",notes = "后端已测试通过")
    @RequiresAuthentication
    public Result<String> createTeam(@RequestBody CreateTeamDTO createTeamDTO, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        String username = JWTUtil.getUsername(token);
        teamService.createTeam(createTeamDTO,username);
        return Result.succ("创建成功");
    }

    @PostMapping("/addmember")
    @RequiresAuthentication
    @ApiOperation(value = "添加成员",notes = "后端已测试通过,权限已验证")
    public Result<String> addMember(@RequestBody OppoTeamMemberDTO oppoTeamMemberDTO) {
        teamService.addMember(oppoTeamMemberDTO);
        return Result.succ("添加成功");
    }

    @PostMapping("/setadmin")
    @ApiOperation(value = "设置管理员",notes = "后端已测试通过,权限已验证")
    @RequiresAuthentication
    public Result<String> setAdmin(@RequestBody OppoTeamMemberDTO oppoTeamMemberDTO) {
        teamService.setAdmin(oppoTeamMemberDTO);
        return Result.succ("设置管理员成功");
    }

    @PostMapping("/removemember")
    @ApiOperation(value = "移除成员",notes = "后端已测试通过")
    @RequiresAuthentication
    public Result<String> removeMember(@RequestBody OppoTeamMemberDTO oppoTeamMemberDTO) {
        teamService.removeMember(oppoTeamMemberDTO);
        return Result.succ("移除成功");
    }

    @PostMapping("/dismiss")
    @RequiresAuthentication
    @ApiOperation(value = "解散小组",notes = "后端已测试通过,权限已验证")
    public Result<String> dismiss(@RequestBody String teamId) {
        BigInteger teamId_ = JSON.parseObject(teamId).getBigInteger("teamId");
        teamService.dismiss(teamId_);
        return Result.succ("解散成功");
    }

    @PostMapping("/removeadmin")
    @ApiOperation(value = "撤销管理员权限",notes = "后端已测试通过,权限已验证")
    @RequiresAuthentication
    public Result<String> removeAdmin(@RequestBody OppoTeamMemberDTO oppoTeamMemberDTO) {
        teamService.removeAdmin(oppoTeamMemberDTO);
        return Result.succ("移除成功");
    }

    @PostMapping("/editteam")
    @ApiOperation(value = "修改小组信息",notes = "后端测试通过not yet")
    @RequiresAuthentication
    public Result<String> editTeam(@RequestBody EditTeamDTO params, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        String username = JWTUtil.getUsername(token);
        try{
            int result = teamService.editTeam(params);
        }catch(Exception e){
            e.printStackTrace();
            return new Result<String>(500, "server error", "");
        }
        return new Result<String>(200, "successfully edit group", "");
    }


}
