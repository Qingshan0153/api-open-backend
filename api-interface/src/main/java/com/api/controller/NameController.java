package openapi.controller;

import com.openapi.openapiclientsdk.pojo.User;
import org.springframework.web.bind.annotation.*;

/**
 * @author mendax
 * @version 2023/7/20 - 15:43
 */

@RestController
@RequestMapping("/name")
public class NameController {


    @GetMapping("/get")
    public String getNameByGet(String name) {

        return "GET你的名字是" + name;
    }

    @PostMapping("/post")
    public String getNaneByPost(@RequestParam String name) {
        return "POST你的名字是" + name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user) {

        return "P0ST 用户名字是" + user.getUsername();
    }
}
