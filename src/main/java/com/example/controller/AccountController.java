package com.example.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.common.Result;
import com.example.common.ResultCode;
import com.example.entity.Account;
import com.example.entity.AdminInfo;
import com.example.entity.AuthorityInfo;
import com.example.entity.UserInfo;
import com.example.exception.CustomException;
import com.example.service.AdminInfoService;
import com.example.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccountController {
    // 注入配置文件中的 authority.info 属性值
    @Value("${authority.info}")
    private String authorityStr;

    // 自动注入 AdminInfoService 实例
    @Resource
    private AdminInfoService adminInfoService;
    // 自动注入 UserInfoService 实例
    @Resource
    private UserInfoService userInfoService;

    // 用户登录接口
    @PostMapping("/login")
    public Result<Account> login(@RequestBody Account account, HttpServletRequest request) {
        // 检查参数完整性
        if (StrUtil.isBlank(account.getName()) || StrUtil.isBlank(account.getPassword()) || account.getLevel() == null) {
            throw new CustomException(ResultCode.PARAM_LOST_ERROR);
        }
        // 根据用户等级调用不同服务进行登录
        Integer level = account.getLevel();
        Account login = new Account();
        if (1 == level) {
            login = adminInfoService.login(account.getName(), account.getPassword());
        }
        if (2 == level) {
            login = userInfoService.login(account.getName(), account.getPassword());
        }
        // 将登录用户信息存入会话
        request.getSession().setAttribute("user", login);
        return Result.success(login);
    }

    // 用户注册接口
    @PostMapping("/register")
    public Result<Account> register(@RequestBody Account account) {
        // 根据用户等级调用不同服务进行注册
        Integer level = account.getLevel();
        Account login = new Account();
        if (1 == level) {
            AdminInfo info = new AdminInfo();
            BeanUtils.copyProperties(account, info);
            login = adminInfoService.add(info);
        }
        if (2 == level) {
            UserInfo info = new UserInfo();
            BeanUtils.copyProperties(account, info);
            login = userInfoService.add(info);
        }
        return Result.success(login);
    }

    // 用户退出登录接口
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        // 将用户会话中的用户信息设置为 null
        request.getSession().setAttribute("user", null);
        return Result.success();
    }

    // 获取用户认证信息接口
    @GetMapping("/auth")
    public Result getAuth(HttpServletRequest request) {
        // 从会话中获取用户信息，若为空则返回未登录错误结果
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            return Result.error("401", "未登录");
        }
        return Result.success(user);
    }

    // 获取用户账户信息接口
    @GetMapping("/getAccountInfo")
    public Result<Object> getAccountInfo(HttpServletRequest request) {
        // 从会话中获取用户信息，根据用户等级调用不同服务获取账户信息
        Account account = (Account) request.getSession().getAttribute("user");
        if (account == null) {
            return Result.success(new Object());
        }
        Integer level = account.getLevel();
        if (1 == level) {
            return Result.success(adminInfoService.findById(account.getId()));
        }
        if (2 == level) {
            return Result.success(userInfoService.findById(account.getId()));
        }
        return Result.success(new Object());
    }

    // 获取用户会话信息接口
    @GetMapping("/getSession")
    public Result<Map<String, String>> getSession(HttpServletRequest request) {
        // 从会话中获取用户信息，若为空则返回空 Map
        Account account = (Account) request.getSession().getAttribute("user");
        if (account == null) {
            return Result.success(new HashMap<>(1));
        }
        Map<String, String> map = new HashMap<>(1);
        map.put("username", account.getName());
        return Result.success(map);
    }

    // 获取权限信息列表接口
    @GetMapping("/getAuthority")
    public Result<List<AuthorityInfo>> getAuthorityInfo() {
        // 将 authorityStr 解析为 AuthorityInfo 对象列表并返回
        List<AuthorityInfo> authorityInfoList = JSONUtil.toList(JSONUtil.parseArray(authorityStr), AuthorityInfo.class);
        return Result.success(authorityInfoList);
    }

    // 获取当前用户所能看到的模块信息接口
    @GetMapping("/authority")
    public Result<List<Integer>> getAuthorityInfo(HttpServletRequest request) {
        // 从会话中获取用户信息，若为空则返回空列表
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.success(new ArrayList<>());
        }
        // 解析 authorityStr 并根据用户等级获取模块信息列表
        JSONArray objects = JSONUtil.parseArray(authorityStr);
        for (Object object : objects) {
            JSONObject jsonObject = (JSONObject) object;
            if (user.getLevel().equals(jsonObject.getInt("level"))) {
                JSONArray array = JSONUtil.parseArray(jsonObject.getStr("models"));
                List<Integer> modelIdList = array.stream().map((o -> {
                    JSONObject obj = (JSONObject) o;
                    return obj.getInt("modelId");
                })).collect(Collectors.toList());
                return Result.success(modelIdList);
            }
        }
        return Result.success(new ArrayList<>());
    }

    // 获取特定模块的权限信息接口
    @GetMapping("/permission/{modelId}")
    public Result<List<Integer>> getPermission(@PathVariable Integer modelId, HttpServletRequest request) {
        // 解析 authorityStr 为 AuthorityInfo 对象列表，从会话中获取用户信息
        List<AuthorityInfo> authorityInfoList = JSONUtil.toList(JSONUtil.parseArray(authorityStr), AuthorityInfo.class);
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.success(new ArrayList<>());
        }
        // 根据用户等级过滤 AuthorityInfo 对象，再根据模块 ID 获取权限信息列表
        Optional<AuthorityInfo> optional = authorityInfoList.stream().filter(x -> x.getLevel().equals(user.getLevel())).findFirst();
        if (optional.isPresent()) {
            Optional<AuthorityInfo.Model> firstOption = optional.get().getModels().stream().filter(x -> x.getModelId().equals(modelId)).findFirst();
            if (firstOption.isPresent()) {
                List<Integer> info = firstOption.get().getOperation();
                return Result.success(info);
            }
        }
        return Result.success(new ArrayList<>());
    }

    // 更新用户密码接口
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account info, HttpServletRequest request) {
        // 从会话中获取用户信息，若为空则返回用户不存在错误结果
        Account account = (Account) request.getSession().getAttribute("user");
        if (account == null) {
            return Result.error(ResultCode.USER_NOT_EXIST_ERROR.code, ResultCode.USER_NOT_EXIST_ERROR.msg);
        }
        // 校验旧密码，加密新密码，根据用户等级更新密码，使会话失效
        String oldPassword = SecureUtil.md5(info.getPassword());
        if (!oldPassword.equals(account.getPassword())) {
            return Result.error(ResultCode.PARAM_PASSWORD_ERROR.code, ResultCode.PARAM_PASSWORD_ERROR.msg);
        }
        info.setPassword(SecureUtil.md5(info.getNewPassword()));
        Integer level = account.getLevel();
        if (1 == level) {
            AdminInfo adminInfo = new AdminInfo();
            BeanUtils.copyProperties(info, adminInfo);
            adminInfoService.update(adminInfo);
        }
        if (2 == level) {
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(info, userInfo);
            userInfoService.update(userInfo);
        }
        request.getSession().setAttribute("user", null);
        return Result.success();
    }

    // 重置用户密码接口
    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestBody Account account) {
        // 根据用户等级重置密码，若用户不存在则返回错误结果
        Integer level = account.getLevel();
        if (1 == level) {
            AdminInfo info = adminInfoService.findByUserName(account.getName());
            if (info == null) {
                return Result.error(ResultCode.USER_NOT_EXIST_ERROR.code, ResultCode.USER_NOT_EXIST_ERROR.msg);
            }
            info.setPassword(SecureUtil.md5("123456"));
            adminInfoService.update(info);
        }
        if (2 == level) {
            UserInfo info = userInfoService.findByUserName(account.getName());
            if (info == null) {
                return Result.error(ResultCode.USER_NOT_EXIST_ERROR.code, ResultCode.USER_NOT_EXIST_ERROR.msg);
            }
            info.setPassword(SecureUtil.md5("123456"));
            userInfoService.update(info);
        }
        return Result.success();
    }
}