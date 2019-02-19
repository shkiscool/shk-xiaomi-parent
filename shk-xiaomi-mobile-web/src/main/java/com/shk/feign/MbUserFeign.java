package com.shk.feign;

import com.shk.api.service.UserService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("member")
public interface MbUserFeign extends UserService {
//   /* *
//     * 注册服务
//     * @param mbUserEntity
//     * @return
//
//    @PostMapping("/regist")
//    public Map<String,Object> regist(@RequestBody MbUserEntity mbUserEntity);
//*/
}
