package com.borui.web.quartz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.borui.web.jpush.JPushUtils;
import com.borui.web.jpush.PushAPI;
import com.borui.web.mapper.MessageMapper;
import com.borui.web.mapper.PartyMapper;
import com.borui.web.mapper.ScheduleMapper;
import com.borui.web.model.Party;
import com.borui.web.model.Schedule;

public class ScanActivityQuartzJob {
	private Logger log = Logger.getLogger(ScanActivityQuartzJob.class);

	@Resource
	private PartyMapper<Party> partyMapper;

	@Resource
	private ScheduleMapper<Schedule> scheduleMapper;
	
	@Resource
	private MessageMapper messageMapper;

	public void scanActivities() {
		
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("alert", 0);
			
			List<Map<String, Object>> rel = new ArrayList<Map<String, Object>>();
			
			rel = scheduleMapper.search(param);//获取所有事项列表
			
			for(Map<String, Object> m : rel){
				int alertStatus = Integer.parseInt(m.get("alert").toString());
				
				
				if (alertStatus == 1) {//推送过通知不作处理
					continue;// has alerted
				} else {//没推过通知

					Long start = Long.parseLong(m.get("start").toString());
					Long now = System.currentTimeMillis();
					
					// type value: 0(task)
					if(m.get("type").toString().equals("0") || m.get("type").toString() == null){
						scan(now, JPushUtils.taskAlertTime, start, m, param, false);
					} else {
						scan(now, JPushUtils.partyAlertTime, start, m, param, true);
					}
				}
			}
		} catch(Exception e){
			log.error("执行推送任务失败！ push msg is error " + e.toString());
		}
	}

	private void scan(Long now, Long span, Long start,Map<String, Object> m, Map<String, Object> param, boolean isPartyType) throws Exception {
		if ((now + span) >= start) {

			List<String> receiverList = null;
			param.clear();
			
			
			boolean msgPush = true;

			if (isPartyType) {
				param.put("pid", m.get("partyId"));
				receiverList = JPushUtils.getJoinedUserIdList(param,
						partyMapper);
				if (receiverList != null && receiverList.size() > 0) {
					param.put("pid", m.get("partyId"));
					param.put("title", m.get("title"));
					param.put("receivers", receiverList);
					PushAPI.getInstance().pushPartyAlertMessage(param);
					for(String str: receiverList){

						param = new HashMap<String, Object>();
						param.put("userId", str);
						param.put("info", m.get("title"));
						param.put("time", System.currentTimeMillis());
						param.put("type", 4);
						
						messageMapper.insert(param);
					}
				}else{
					msgPush = false;
				}
			} else {
				receiverList = new ArrayList<String>();
				receiverList.add(JPushUtils.userTagPrefix
						+ m.get("owner").toString());
				param.put("pid", m.get("pid"));
				param.put("receivers", receiverList);
				param.put("title", m.get("title"));

				PushAPI.getInstance().pushTaskAlertMessage(param);
				
				
				param.clear();
				param.put("userId", m.get("owner"));
				param.put("info", m.get("title"));
				param.put("time", System.currentTimeMillis());
				param.put("type", 5);
				
				messageMapper.insert(param);
			}

			if (msgPush){
				// modify schedule table's extInt1 to be 1
				param.clear();
				param.put("pid", m.get("pid"));
				param.put("alert", 1);
				
				int flag = 0;
				
				flag = scheduleMapper.modify(param);
				
				if (flag > 0) {
					log.info("successfully updated alert status");
				} else {
					log.info("failed to update alert status");
				}
			}

		}
	}
}
