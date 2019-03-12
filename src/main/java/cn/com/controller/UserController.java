package cn.com.controller;



import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import cn.com.pojo.User;

@RestController
@RequestMapping("/user")
public class UserController {
	 private static Logger logger = Logger.getLogger(UserController.class);  
	
	 //服务发现客户端
	 @Autowired
	 private DiscoveryClient discoveryClient=null;
	 
	 //获取用户信息
	 @GetMapping("/find/{id}")
	 public User getUserPo(@PathVariable("id") Integer id) {
		 	ServiceInstance serviceInstance= discoveryClient.getInstances("WMCUSER").get(0);
		 	logger.info("serviceInstance.getUri():"+serviceInstance.getUri());
		 	logger.info("serviceInstance.getUri():"+serviceInstance.getHost());
		 	logger.info("serviceInstance.getUri():"+serviceInstance.getUri());
		 	User userPo=new User();
		 	userPo.setId(id);
		 	userPo.setLevel((int) (id%2+1));
		 	userPo.setUserName("user_name_"+id);
		 	userPo.setNote("note_"+id);
		 	return userPo;
	 }
	 
	// 新增用户，POST请求，且以请求体(body)形式传递
	 @PostMapping("/addUser")
		public Map<String,Object> addUser(@RequestBody User use){
		 Map<String, Object> map=new HashedMap();
		 map.put("msg1", "111111111用户添加成功："+use);
		 return map;
	 }
	
	 @PostMapping("/update/{userName}")        // URL参数							// 请求头参数
	 public  Map<String,Object>  updateUser(@PathVariable("userName") String userName,@RequestHeader("id") Integer id){
		 Map<String, Object> map=new HashedMap();
		 map.put("msg1", "修改用户姓名："+userName+"用户id:"+id);
		 return map;
	 }
	 
	 
	 //自动获取用户信息
	 @GetMapping("/userinfo")
	 public Map<String, Object> findInfo() {
		 Integer id=000;
		 Map<String, Object> map=new HashedMap();
		 	User userPo=new User();
		 	userPo.setId(id);
		 	userPo.setLevel((int) (id%2+1));
		 	userPo.setUserName("2222利用feign提供的声明是方法调用，并根据用户id："+id);
		 	userPo.setNote("note_"+id);
		 	map.put("msg", "根据用户id，获得用户信息如下："+userPo);
		 	return map;
	 }
	 
	 /*短路测试--------------------------------------------------------start*/
	 @GetMapping("/timeout")
	 public String timeout() {
		    // 生成一个3000之内的随机数
		 long ms=(long)(3000L*Math.random());
		 	try {
				Thread.sleep(ms);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		    return "success11111111111111熔断测试";
		}
	 @GetMapping("/circuitBreaker1timeout")
	 public String circuitBreaker1timeout() {
		 long ms=(long)(3000L*Math.random());
		 	try {
				Thread.sleep(ms);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 	return "1测试成功！！ribbon短路测试";
	 }
	 @GetMapping("/circuitBreaker2timeout")
	 public String circuitBreaker2timeout() {
		 long ms=(long)(3000L*Math.random());
		 	try {
				Thread.sleep(ms);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 	return "1测试成功！！feign短路测试";
	 }
	 /*短路测试--------------------------------------------------------end*/	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

}
