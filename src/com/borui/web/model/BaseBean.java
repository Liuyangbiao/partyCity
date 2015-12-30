package com.borui.web.model;

public class BaseBean {

	/**
	 * 
	 * 状态枚举
	 * 
	 */
	public static enum STATE {
		ENABLE(0, "可用"), DISABLE(1, "禁用");
		public int key;
		public String value;

		private STATE(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public static STATE get(int key) {
			STATE[] values = STATE.values();
			for (STATE object : values) {
				if (object.key == key) {
					return object;
				}
			}
			return null;
		}
	}
	
	public static enum ArtistType {
		ROCK(1, "摇滚"), JAZZ(2, "爵士"), COUNTRY(3, "乡村"), POP(4, "POP"), MENTAL(5, "重金属"), BLUE(6, "蓝调");
		public int key;
		public String value;

		private ArtistType(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public static ArtistType get(int key) {
			ArtistType[] values = ArtistType.values();
			for (ArtistType object : values) {
				if (object.key == key) {
					return object;
				}
			}
			return null;
		}
	}

}
