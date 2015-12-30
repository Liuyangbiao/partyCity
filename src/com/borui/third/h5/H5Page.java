package com.borui.third.h5;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


class H5Page{
	// UI can be divided into 5 parts

	// 1st: creator and Hongbao
	private String avatar;
	private String nickName;
	private String ownerSex;
	private String constellation;
	private String age;

	// date type
	private String bonusNum;

	// 2cnd: bar name
	private String barName;

	// 3rd: date info
	private String title;
	private String sex;
	private String feeType;
	private String time;

	// 4th: joined user info
	private List<String> personJoinedList;
	private int personJoinedNum;

	// 5th: date desc
	private String desc;

	/////
	protected Integer id;
	protected Integer action;// create or update
	protected String sharePage;// existing h5
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAction() {
		return action;
	}
	public void setAction(Integer action) {
		this.action = action;
	}
	public String getSharePage() {
		return sharePage;
	}
	public void setSharePage(String sharePage) {
		this.sharePage = sharePage;
	}

	public void fillData(boolean createNew, Map<String, Object> map) {
		Object value = null;
		
		// 1
		value = map.get("avatar");
		if (value != null){
			avatar = (String) map.get("avatar");
		}
		
		value = map.get("nickName");
		if (value != null){
			nickName = (String) map.get("nickName");
		}
		
		value = map.get("age");
		if (value != null){
			age = (String) map.get("age");
		}
		
		
		value = map.get("constellation");
		if (value != null){
			constellation = (String) map.get("constellation");
			constellation = getConstellationNameById(constellation);
		}


		value = map.get("ownerSex");
		if (value != null){
			ownerSex = (String) map.get("ownerSex");
			if (ownerSex == null || ownerSex.equals(H5Config.sex_male)) {
				// "../resources/boy.png"
				ownerSex = H5Config.resouceRelativePath + "boy.png";
			} else {
				ownerSex = H5Config.resouceRelativePath + "girl.png";
			}
		}



		// 1: bonus kind; 2: free.
		value = map.get("dateType");
		int dateType = 0;
		if (value != null){
			dateType = (Integer)map.get("dateType");
		}

		if(dateType == 2){
			bonusNum = null;
		}
		
		value = map.get("bonusNum");
		if (value != null){
			bonusNum = String.valueOf(value);
			bonusNum = getBonusPath();
		}else{
			bonusNum = null;
		}


		// 2
		value = map.get("barName");
		if (value != null){
			barName = (String) map.get("barName");
		}
		
		

		// 3
		value = map.get("title");
		if (value != null){
			title = (String) map.get("title");
		}
		
		
		value = map.get("sex");
		if (value != null){
			int intSex = 0;
			if (createNew){
				// create时，返回是int
				intSex = (Integer) map.get("sex");
			}else{
				// update: 时返回的是string
				String str = (String)map.get("sex");
				intSex = Integer.parseInt(str);
			}
			
			if (intSex == H5Config.sex_male) {
				sex = "男";
			} else if (intSex == H5Config.sex_female){
				sex = "女";
			}else{
				sex = "不限";
			}

		}
		

		value = map.get("feeType");
		if (value != null){
			int intFeeType = 0;
			if (createNew){
				// create时，返回是int
				intFeeType = (Integer) map.get("feeType");
			}else{
				// update: 时返回的是string
				String str = (String)map.get("feeType");
				intFeeType = Integer.parseInt(str);
			}
			
			if (intFeeType == 1) {
				feeType = "我请";
			} else if (intFeeType == 2) {
				feeType = "你请";
			} else {
				feeType = "AA";
			}
		}


		value = map.get("time");
		if (value != null){
			time = (String) map.get("time");
			// 1436427120.000000
			if(time != null && time.isEmpty() == false){
				String date = time;
				try {
					date = H5Utils.transformDate(time);
				} catch (ParseException e) {
					e.printStackTrace();
				}finally{
					time = date;
				}
			}
		}

		// 4
		if (createNew){
			personJoinedNum = 0;
			personJoinedList = null;
			
			id = 0;
			sharePage = null;
		}else{
			this.personJoinedList = new ArrayList<String>();
			
			personJoinedNum = 0;
			value = map.get("personJoinedNum");
			if (value != null){
				personJoinedNum = (Integer) map.get("personJoinedNum");
			}
			
			if (personJoinedNum > 0) {
				List<Map<String, Object>> joined = (List<Map<String, Object>>) map
						.get("personJoinedList");
				for (int i = 0; i < joined.size(); i++) {
					Map<String, Object> item = joined.get(i);
					String avatar = null;
					if (item != null) {
						avatar = (String) item.get("avatar");
						this.personJoinedList.add(avatar);
					}
				}
			}
			
			/// more
			value = map.get("pid");
			if (value != null){
				// no matter create a new, or update, type is int
				id = (Integer) map.get("pid");
			}
			
			value = map.get("sharePage");
			if (value != null){
				sharePage = (String)map.get("sharePage");
			}
			
			
		}


		// 5
		value = map.get("desc");
		if (value != null){
			desc = (String) map.get("desc");
		}
		
	}
	
	private String getBonusPath() {
		String bonusImgPath = null;
		if (bonusNum == null || bonusNum.isEmpty()) {
			return bonusImgPath;
		}

		String index;
		for (int i = 0; i < H5Config.bonus_type; i++) {
			index = (i + 1) + "";

			if (bonusNum.equals(index)) {
				// "../resources/red-packet_1.png"
				bonusImgPath = H5Config.resouceRelativePath + "red-packet_" + index + ".png";
				break;
			}
		}

		return bonusImgPath;
	}

	private String getConstellationNameById(String id) {
		if (id == null || id.isEmpty()){
			return "";
		}
		
		String v = null;
		int key = Integer.parseInt(id);

		switch (key) {
		case 11:
			v = "射手座";
			break;
		case 10:
			v = "摩羯座";
			break;
		case 9:
			v = "水瓶座";
			break;
		case 8:
			v = "双鱼座";
			break;
		case 7:
			v = "白羊座";
			break;
		case 6:
			v = "金牛座";
			break;
		case 5:
			v = "双子座";
			break;
		case 4:
			v = "巨蟹座";
			break;
		case 3:
			v = "狮子座";
			break;
		case 2:
			v = "处女座";
			break;
		case 1:
			v = "天蝎座";
			break;
		}

		return v;
	}

	
	
	
	/* getter*/

	public String getAvatar() {
		return avatar;
	}

	public String getNickName() {
		return nickName;
	}

	public String getOwnerSex() {
		return ownerSex;
	}

	public String getConstellation() {
		return constellation;
	}

	public String getAge() {
		return age;
	}

	public String getBonusNum() {
		return bonusNum;
	}

	public String getBarName() {
		return barName;
	}

	public String getTitle() {
		return title;
	}

	public String getSex() {
		return sex;
	}

	public String getFeeType() {
		return feeType;
	}

	public String getTime() {
		return time;
	}

	public List<String> getPersonJoinedList() {
		return personJoinedList;
	}

	public int getPersonJoinedNum() {
		return personJoinedNum;
	}

	public String getDesc() {
		return desc;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public void setOwnerSex(String ownerSex) {
		this.ownerSex = ownerSex;
	}
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public void setBonusNum(String bonusNum) {
		this.bonusNum = bonusNum;
	}
	public void setBarName(String barName) {
		this.barName = barName;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setPersonJoinedList(List<String> personJoinedList) {
		this.personJoinedList = personJoinedList;
	}
	public void setPersonJoinedNum(int personJoinedNum) {
		this.personJoinedNum = personJoinedNum;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}