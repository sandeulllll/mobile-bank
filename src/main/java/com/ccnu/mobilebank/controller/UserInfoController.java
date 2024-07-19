package com.ccnu.mobilebank.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccnu.mobilebank.exception.ConditionException;
import com.ccnu.mobilebank.pojo.JsonResponse;
import com.ccnu.mobilebank.pojo.Mobile;
import com.ccnu.mobilebank.pojo.Personinfo;
import com.ccnu.mobilebank.pojo.UserInfo;
import com.ccnu.mobilebank.service.IMobileService;
import com.ccnu.mobilebank.service.IPersoninfoService;
import com.ccnu.mobilebank.service.IUserInfoService;
import com.ccnu.mobilebank.support.UserSupport;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 个人信息
 * @author Baomidou
 * @since 2024-07-16
 */
@RestController
@RequestMapping("/users")
public class UserInfoController {
    @Autowired
    private IPersoninfoService personinfoService;

    @Autowired
    private IMobileService mobileService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private UserSupport userSupport;

    // 查看个人信息
    @GetMapping("/info")
    public JsonResponse<Personinfo> getPersonInfo() {
        Integer personId = userSupport.getCurrentPersonId();
        Personinfo personinfo = personinfoService.getById(personId);
        return new JsonResponse<>(personinfo);
    }

    // 获取头像
    @GetMapping("/avatar")
    public JsonResponse<String> getAvatar() {
        Integer mobileId = userSupport.getCurrentMobileId();
        String url = userInfoService.selectAvatar(mobileId);
        return new JsonResponse<>(url);
    }

    // 修改头像
    @PostMapping("/avatar/update")
    public JsonResponse updateAvatar(@RequestParam MultipartFile image, HttpServletRequest request) throws IOException {
        // 1. 获取原始文件名
        String originalFilename = image.getOriginalFilename();
        if(originalFilename == null || originalFilename.isEmpty())
            throw new ConditionException("路径错误");

        // 2. 构建新的文件名
        String extname = originalFilename.substring(originalFilename.lastIndexOf(".")); // 文件扩展名
        String newFileName = UUID.randomUUID().toString() + extname; // 随机名+文件扩展名

        // 3. 指定存储文件的目录
        String uploadDir = System.getProperty("user.dir") + "/files";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            // 目录不存在时创建
            boolean mkdirsResult = directory.mkdirs();
            if (!mkdirsResult)
                throw new ConditionException("新建存储目录失败");
        }

        // 4. 将文件存储在服务器的磁盘目录
        File destinationFile = new File(directory, newFileName);
        image.transferTo(destinationFile);

        // 5. 存储完整路径
        String fileUrl = "/files/" + newFileName;
        Integer mobileId = userSupport.getCurrentMobileId();
        QueryWrapper<UserInfo> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("mobileId", mobileId);

        // 5.1 用户尚未上传头像
        UserInfo userInfo = userInfoService.getOne(queryWrapper);
        if(userInfo == null){
            userInfo = new UserInfo(); // 需要初始化UserInfo对象
            userInfo.setAvatar(fileUrl);
            userInfo.setMobileId(mobileId);
            userInfoService.save(userInfo);
        } else {
            // 5.2 用户上传过头像
            userInfo.setAvatar(fileUrl);
            userInfoService.update(userInfo, queryWrapper);
        }

        return JsonResponse.success();
    }

    // 验证登录密码
    @PostMapping("/verifyPassword")
    public JsonResponse<String> verifyPassword(@RequestParam String password){
        Integer mobileId = userSupport.getCurrentMobileId();
        Mobile mobile = mobileService.getById(mobileId);
        if(!mobile.getPassword().equals(password))
            throw new ConditionException("密码错误");
        return JsonResponse.success();
    }

    // 修改登录密码
    @PutMapping("/updatePassword")
    public JsonResponse<String> updatePassword(@RequestParam String password){
        Integer mobileId = userSupport.getCurrentMobileId();
        Mobile mobile = new Mobile();
        mobile.setPassword(password);
        mobile.setId(mobileId);
        mobileService.updateById(mobile);
        return JsonResponse.success();
    }

}
