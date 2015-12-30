package com.borui.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.borui.third.im.api.EasemobChatGroups;
import com.borui.third.im.api.EasemobIMUsers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
/**
 * 环信用户工具类(单例模式)
 * @author yuandong lin
 *
 */
public class HXUserUtil {

	private static JsonNodeFactory factory = new JsonNodeFactory(false);
	
	static ObjectNode objectNode = factory.objectNode();
	
	private static HXUserUtil instance = new HXUserUtil();  
	
	private HXUserUtil (){}
	
	public static HXUserUtil getInstance() {  
		return instance;  
	}
	/**
	 *注册用户的同时注册环信用户
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String createNewIMUserSingle(String username,String userPassword){
		EasemobIMUsers em = new EasemobIMUsers();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode userNode = mapper.createObjectNode();
		userNode.put("username", username);
		userNode.put("password",userPassword);
		objectNode = em.createNewIMUserSingle(userNode);
		String msg = null;
		if(StringUtil.strIsObjNotNull((objectNode.get("error")))){
			msg = objectNode.get("error").toString();
		}else{
			msg = "success";
		}
		return msg;
	}
	
	/**
	 * 环信设置新密码(旧密码)
	 */
	@SuppressWarnings("static-access")
	public static String modifyIMUserPasswordWithAdminTokenByPassword(String newPassword,String mobileNumber){
		EasemobIMUsers em = new EasemobIMUsers();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode userNode = mapper.createObjectNode();
		userNode.put("newpassword", newPassword);
		userNode.put("mobileNumber", mobileNumber);
		objectNode = em.modifyIMUserPasswordWithAdminToken(mobileNumber,userNode);
		String msg="";
		if(StringUtil.strIsObjNotNull((objectNode.get("action")))){
			msg="success";
		}else{
			msg="false";
		}
		return msg;
	}

	/**
	 * 创建群组
	 * @author yuandong lin
	 * @param groupName
	 * @param desc
	 * @param userName
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public static String creatChatGroups(String groupName,String desc,String userName,Integer num) throws Exception{
		objectNode.put("groupname",groupName);
		objectNode.put("desc",desc);
		objectNode.put("public", true);
		objectNode.put("approval", false);
		objectNode.put("maxusers", num);
		objectNode.put("owner",userName);
		objectNode = EasemobChatGroups.creatChatGroups(objectNode);
		String groupId = objectNode.get("data").findValue("groupid").toString();
		String groupID = groupId.substring(1, groupId.length()-1);
		return groupID;
	}
	
	/**
	 * 根据群组id删除群组
	 * @author yuandong lin
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public static String deleteChatGroups(String groupId) throws Exception{
		objectNode = EasemobChatGroups.deleteChatGroups(groupId);
		JSONObject jsonObject = JSONObject.parseObject((objectNode.get("data").toString()));
		return jsonObject.get("success").toString();
	}
	
	/**
	 * 根据群组id获取群组成员
	 * @author yuandong lin
	 * @param chatgroupid
	 * @return
	 */
	public static int getAllMemberssByGroupId(String chatgroupid){
		objectNode = EasemobChatGroups.getAllMemberssByGroupId(chatgroupid);
		JsonNode node = objectNode.get("data");
		if (node != null && node.get("result") != null) {
		    return node.get("result").size();
		}
		return 0;
	}
	
	/**
	 * 加入环信群组
	 * @author yuandong lin
	 * @param chatgroupid
	 * @param userprimarykey
	 */
	public static ObjectNode addUserToGroup(String chatgroupid,String userprimarykey){
		return EasemobChatGroups.addUserToGroup(chatgroupid, userprimarykey);
	}
	
	/**
	 * 退出环信群组
	 * @param chatgroupid
	 * @param userprimarykey
	 * @return
	 */
	public static ObjectNode deleteUserFromGroup(String chatgroupid,String userprimarykey){
		return EasemobChatGroups.deleteUserFromGroup(chatgroupid, userprimarykey);
	}
	
	/**
	 * 删除用户
	 * @author yuandong lin
	 * @param userPrimaryKey
	 * @return
	 */
	public static ObjectNode deleteIMUserByUserPrimaryKey(String userPrimaryKey) {
		return EasemobIMUsers.deleteIMUserByUserPrimaryKey(userPrimaryKey);
	}
	
	/**
	 * 
	* @function 添加好友[单个]
	* @date 2015-1-4 下午08:08:01
	* @author v_lianghua
	* @param userPrimaryKey
	* @return
	* @return ObjectNode
	 */
	public static String addFriendSingle(String ownerUserPrimaryKey, String friendUserPrimaryKey) {
	    objectNode = EasemobIMUsers.addFriendSingle(ownerUserPrimaryKey,friendUserPrimaryKey);
        String msg = null;
        if(StringUtil.strIsObjNotNull(objectNode.get("error"))){
            msg = objectNode.get("error").toString();
        }else{
            msg = "success";
        }
        return msg;
	}
	
	/**
	 * 
	 * @function 解除好友关系[单个]
	 * @date 2015-1-4 下午08:08:01
	 * @author v_lianghua
	 * @param userPrimaryKey
	 * @return
	 * @return ObjectNode
	 */
	public static String deleteFriendSingle(String ownerUserPrimaryKey, String friendUserPrimaryKey) {
	    objectNode = EasemobIMUsers.deleteFriendSingle(ownerUserPrimaryKey,friendUserPrimaryKey);
	    String msg = null;
	    if(StringUtil.strIsObjNotNull((objectNode.get("error")))){
	        msg = objectNode.get("error").toString();
	    }else{
	        msg = "success";
	    }
	    return msg;
	}
	
	/**
	 * 
	* @function 获取好友列表
	* @date 2015-1-4 下午03:23:34
	* @author v_lianghua
	* @param userPrimaryKey
	* @return
	* @return ObjectNode
	 */
	public static List<String> getFriendsByUserPrimaryKey(String ownerUserPrimaryKey) {
	    
	    // 好友列表(手机号)
	    List<String> returnValue = new ArrayList<String>();
	    
	    ObjectNode node = EasemobIMUsers.getFriends(ownerUserPrimaryKey);
	    JsonNode value = node.get("data");
	    for (int i = 0; i < value.size(); i++) {
	        returnValue.add(String.valueOf(value.get(i).textValue()));
	    }
	    
	    return returnValue;
	}
}
