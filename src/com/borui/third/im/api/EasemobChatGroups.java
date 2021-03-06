package com.borui.third.im.api;

import org.glassfish.jersey.client.JerseyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.borui.third.im.common.Constants;
import com.borui.third.im.common.HTTPMethod;
import com.borui.third.im.common.Roles;
import com.borui.third.im.utils.JerseyUtils;
import com.borui.third.im.vo.Credentail;
import com.borui.third.im.vo.EndPoints;
import com.borui.third.im.vo.UsernamePasswordCredentail;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * REST API Demo : 群组管理 Jersey2.9实现
 * 
 * Doc URL: http://www.easemob.com/docs/rest/groups/
 * 
 * @author Lynch 2014-09-12
 * 
 */
public class EasemobChatGroups {

	private static Logger LOGGER = LoggerFactory
			.getLogger(EasemobChatGroups.class);

	private static JsonNodeFactory factory = new JsonNodeFactory(false);

	private static final String APPKEY = Constants.APPKEY;

	private static EasemobChatGroups instance = new EasemobChatGroups();

	public static EasemobChatGroups getInstance() {
		return instance;
	}

	/**
	 * 获取APP中所有的群组ID
	 * 
	 * 
	 * @return
	 */
	public static ObjectNode getAllChatgroupids() {

		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		try {

			Credentail credentail = new UsernamePasswordCredentail(
					Constants.APP_ADMIN_USERNAME, Constants.APP_ADMIN_PASSWORD,
					Roles.USER_ROLE_APPADMIN);

			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.CHATGROUPS_TARGET.resolveTemplate("org_name",
					APPKEY.split("#")[0]).resolveTemplate("app_name",
					APPKEY.split("#")[1]);

			objectNode = JerseyUtils.sendRequest(webTarget, null, credentail,
					HTTPMethod.METHOD_GET, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 获取一个或者多个群组的详情
	 * 
	 * @return
	 */
	public static ObjectNode getGroupDetailsByChatgroupid(String[] chatgroupIDs) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		try {

			Credentail credentail = new UsernamePasswordCredentail(
					Constants.APP_ADMIN_USERNAME, Constants.APP_ADMIN_PASSWORD,
					Roles.USER_ROLE_APPADMIN);

			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.CHATGROUPS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.path(chatgroupIDs.toString());

			objectNode = JerseyUtils.sendRequest(webTarget, null, credentail,
					HTTPMethod.METHOD_GET, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 创建群组
	 * 
	 */
	public static ObjectNode creatChatGroups(ObjectNode dataObjectNode) {

		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		// check properties that must be provided
		if (!dataObjectNode.has("groupname")) {
			LOGGER.error("Property that named groupname must be provided .");

			objectNode.put("message",
					"Property that named groupname must be provided .");

			return objectNode;
		}
		if (!dataObjectNode.has("desc")) {
			LOGGER.error("Property that named desc must be provided .");

			objectNode.put("message",
					"Property that named desc must be provided .");

			return objectNode;
		}
		if (!dataObjectNode.has("public")) {
			LOGGER.error("Property that named public must be provided .");

			objectNode.put("message",
					"Property that named public must be provided .");

			return objectNode;
		}
		if (!dataObjectNode.has("approval")) {
			LOGGER.error("Property that named approval must be provided .");

			objectNode.put("message",
					"Property that named approval must be provided .");

			return objectNode;
		}
		if (!dataObjectNode.has("owner")) {
			LOGGER.error("Property that named owner must be provided .");

			objectNode.put("message",
					"Property that named owner must be provided .");

			return objectNode;
		}
//		if (!dataObjectNode.has("members")
//				|| !dataObjectNode.path("members").isArray()) {
//			LOGGER.error("Property that named members must be provided .");
//
//			objectNode.put("message",
//					"Property that named members must be provided .");
//
//			return objectNode;
//		}

		try {

			Credentail credentail = new UsernamePasswordCredentail(
					Constants.APP_ADMIN_USERNAME, Constants.APP_ADMIN_PASSWORD,
					Roles.USER_ROLE_APPADMIN);

			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.CHATGROUPS_TARGET.resolveTemplate("org_name",
					APPKEY.split("#")[0]).resolveTemplate("app_name",
					APPKEY.split("#")[1]);

			objectNode = JerseyUtils.sendRequest(webTarget, dataObjectNode,
					credentail, HTTPMethod.METHOD_POST, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 删除群组
	 * 
	 */
	public static ObjectNode deleteChatGroups(String chatgroupid) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		try {

			Credentail credentail = new UsernamePasswordCredentail(
					Constants.APP_ADMIN_USERNAME, Constants.APP_ADMIN_PASSWORD,
					Roles.USER_ROLE_APPADMIN);

			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.CHATGROUPS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.path(chatgroupid);

			objectNode = JerseyUtils.sendRequest(webTarget, null, credentail,
					HTTPMethod.METHOD_DELETE, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 获取群组中的所有成员
	 * 
	 */
	public static ObjectNode getAllMemberssByGroupId(String chatgroupid) {

		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		try {

			Credentail credentail = new UsernamePasswordCredentail(
					Constants.APP_ADMIN_USERNAME, Constants.APP_ADMIN_PASSWORD,
					Roles.USER_ROLE_APPADMIN);

			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.CHATGROUPS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.path(chatgroupid).path("users");

			objectNode = JerseyUtils.sendRequest(webTarget, null, credentail,
					HTTPMethod.METHOD_GET, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 在群组中添加一个人
	 * 
	 */
	public static ObjectNode addUserToGroup(String chatgroupid,
			String userprimarykey) {

		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		try {

			Credentail credentail = new UsernamePasswordCredentail(
					Constants.APP_ADMIN_USERNAME, Constants.APP_ADMIN_PASSWORD,
					Roles.USER_ROLE_APPADMIN);

			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.CHATGROUPS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.path(chatgroupid).path("users").path(userprimarykey);

			objectNode = JerseyUtils.sendRequest(webTarget, null, credentail,
					HTTPMethod.METHOD_POST, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 在群组中减少一个人
	 * 
	 */
	public static ObjectNode deleteUserFromGroup(String chatgroupid,
			String userprimarykey) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		try {

			Credentail credentail = new UsernamePasswordCredentail(
					Constants.APP_ADMIN_USERNAME, Constants.APP_ADMIN_PASSWORD,
					Roles.USER_ROLE_APPADMIN);

			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.CHATGROUPS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.path(chatgroupid).path("users").path(userprimarykey);

			objectNode = JerseyUtils.sendRequest(webTarget, null, credentail,
					HTTPMethod.METHOD_DELETE, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	public static ObjectNode queryMemberGroup(String username) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		try {

			Credentail credentail = new UsernamePasswordCredentail(
					Constants.APP_ADMIN_USERNAME, Constants.APP_ADMIN_PASSWORD,
					Roles.USER_ROLE_APPADMIN);

			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.USERS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.path(username).path("joined_chatgroups");

			objectNode = JerseyUtils.sendRequest(webTarget, null, credentail,
					HTTPMethod.METHOD_GET, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	
	public static void main(String[] args) {
		// ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode();
		// dataObjectNode.put("groupname", "测试群组");
		// dataObjectNode.put("desc", "测试群组");
		// dataObjectNode.put("approval", false);
		// dataObjectNode.put("public", false);
		// dataObjectNode.put("owner", "vrhfk5lxsz");
		// ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
		// arrayNode.add("pfxfc9ggkz");
		// arrayNode.add("xffslraxae");
		// dataObjectNode.put("members", arrayNode);
		// creatChatGroups(dataObjectNode);

		// getAllMemberssByGroupId("141334312527350") ;
		ObjectNode objectNode = factory.objectNode();
		// addUserToGroup("141334312527350","") ;
		// deleteUserFromGroup("141334312527350","123") ;
		// System.out.println(getAllMemberssByGroupId("141334312527350"));
		objectNode=queryMemberGroup("13512341234");
	//	Object[] it = objectNode.findValues("data").size();
		System.out.println(objectNode.get("data").findValue("groupid"));
//		for(int i=0;i<it.length;i++){
//			System.out.println(it[i]);
//		}
	}

}
