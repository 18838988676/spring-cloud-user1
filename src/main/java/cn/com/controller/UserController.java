package cn.com.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import cn.com.pojo.UserPo;

@RestController
@RequestMapping("/user")
public class UserController {
	 private static Logger logger = Logger.getLogger(UserController.class);  
	
	 //服务发现客户端
	 @Autowired
	 private DiscoveryClient discoveryClient=null;
	 
	 //获取用户信息
	 @GetMapping("/user/{id}")
	 public UserPo getUserPo(@PathVariable("id") long id) {
		 	ServiceInstance serviceInstance= discoveryClient.getInstances("WMCUSER").get(0);
		 	logger.info("serviceInstance.getUri():"+serviceInstance.getUri());
		 	logger.info("serviceInstance.getUri():"+serviceInstance.getHost());
		 	logger.info("serviceInstance.getUri():"+serviceInstance.getUri());
		 	UserPo userPo=new UserPo();
		 	userPo.setId(id);
		 	userPo.setLevel((int) (id%2+1));
		 	userPo.setUserName("user_name_"+id);
		 	userPo.setNote("note_"+id);
		 	return userPo;
	 }
	 
	
	

}
