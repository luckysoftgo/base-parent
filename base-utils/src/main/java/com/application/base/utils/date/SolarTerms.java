package com.application.base.utils.date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc 输出24节气.
 * @author 孤狼.
 *
 */
public class SolarTerms {

	private static int mYear;
	private static final double D = 0.2422;
	/**
	 * +1偏移
	 */
	private final static Map<String, Integer[]> INCREASE_OFFSETMAP = new HashMap<String, Integer[]>();
	/**
	 *  -1偏移
	 */
	private final static Map<String, Integer[]> DECREASE_OFFSETMAP = new HashMap<String, Integer[]>();
	private static List<String> mSolarData = new ArrayList<String>();
	private static List<String> mSolarName = new ArrayList<String>();

	/** 24节气 **/
	private static enum SolarTermsEnum {
		/**
		 * 立春
		 */
		LICHUN,
		/**
		 * 雨水
		 */
		YUSHUI,
		/**
		 *惊蛰
		 */
		JINGZHE,
		/**
		 * 春分
		 */
		CHUNFEN,
		/**
		 * 清明
		 */
		QINGMING,
		/**
		 * 谷雨
		 */
		GUYU,
		/**
		 * 立夏
		 */
		LIXIA,
		/**
		 *小满
		 */
		XIAOMAN,
		/**
		 * 芒种
		 */
		MANGZHONG,
		/**
		 * 夏至
		 */
		XIAZHI,
		/**
		 * 小暑
		 */
		XIAOSHU,
		/**
		 * 大暑
		 */
		DASHU,
		/**
		 * 立秋
		 */
		LIQIU,
		/**
		 * 处暑
		 */
		CHUSHU,
		/**
		 * 白露
		 */
		BAILU,
		/**
		 * 秋分
		 */
		QIUFEN,
		/**
		 * 寒露
		 */
		HANLU,
		/**
		 * 霜降
		 */
		SHUANGJIANG,
		/**
		 * 立冬
		 */
		LIDONG,
		/**
		 * 小雪
		 */
		XIAOXUE,
		/**
		 * 大雪
		 */
		DAXUE,
		/**
		 * 冬至
		 */
		DONGZHI,
		/**
		 * 小寒
		 */
		XIAOHAN,
		/**
		 * 大寒
		 */
		DAHAN;
	}
	
	/**
	 * 初始化操作..
	 */
	static {
		DECREASE_OFFSETMAP.put(SolarTermsEnum.YUSHUI.name(), new Integer[] { 2026 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.CHUNFEN.name(), new Integer[] { 2084 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOMAN.name(), new Integer[] { 2008 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.MANGZHONG.name(), new Integer[] { 1902 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAZHI.name(), new Integer[] { 1928 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOSHU.name(), new Integer[] { 1925, 2016 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.DASHU.name(), new Integer[] { 1922 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.LIQIU.name(), new Integer[] { 2002 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.BAILU.name(), new Integer[] { 1927 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.QIUFEN.name(), new Integer[] { 1942 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.SHUANGJIANG.name(), new Integer[] { 2089 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.LIDONG.name(), new Integer[] { 2089 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOXUE.name(), new Integer[] { 1978 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.DAXUE.name(), new Integer[] { 1954 });
		DECREASE_OFFSETMAP.put(SolarTermsEnum.DONGZHI.name(), new Integer[] { 1918, 2021 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOHAN.name(), new Integer[] { 1982 });
		DECREASE_OFFSETMAP.put(SolarTermsEnum.XIAOHAN.name(), new Integer[] { 2019 });
		INCREASE_OFFSETMAP.put(SolarTermsEnum.DAHAN.name(), new Integer[] { 2082 });
	}
	
	/**
	 * 定义一个二维数组，第一维数组存储的是20世纪的节气C值，第二维数组存储的是21世纪的节气C值,0到23个，依次代表立春、雨水...大寒节气的C值
	 */
	private static final double[][] CENTURY_ARRAY = {
			{ 4.6295, 19.4599, 6.3826, 21.4155, 5.59, 20.888, 6.318, 21.86, 6.5, 22.2, 7.928, 23.65, 8.35, 23.95, 8.44,
					23.822, 9.098, 24.218, 8.218, 23.08, 7.9, 22.6, 6.11, 20.84 },
			{ 3.87, 18.73, 5.63, 20.646, 4.81, 20.1, 5.52, 21.04, 5.678, 21.37, 7.108, 22.83, 7.5, 23.13, 7.646, 23.042,
					8.318, 23.438, 7.438, 22.36, 7.18, 21.94, 5.4055, 20.12 } };

	/**
	 * 
	 * @param year
	 *            年份
	 * @param name
	 *            节气的名称
	 * @return 返回节气是相应月份的第几天
	 */
	public static int getSolarTermNum(int year, String name) {
		
		/**
		 * 节气的世纪值，每个节气的每个世纪值都不同
		 */
		double centuryValue = 0;
		name = name.trim().toUpperCase();
		int ordinal = SolarTermsEnum.valueOf(name).ordinal();

		int centuryIndex = -1,num1=1901,num2=2000,num3=2001,num4=2100;
		// 20世纪
		if (year >= num1 && year <= num2) {
			centuryIndex = 0;
		}
		// 21世纪
		else if (year >= num3 && year <= num4) {
			centuryIndex = 1;
		}
		else {
			throw new RuntimeException("不支持此年份：" + year + "，目前只支持1901年到2100年的时间范围");
		}
		centuryValue = CENTURY_ARRAY[centuryIndex][ordinal];
		int dateNum = 0;
		/**
		 * 计算 num =[Y*D+C]-L这是传说中的寿星通用公式 公式解读：年数的后2位乘0.2422加C(即：centuryValue)取整数后，减闰年数
		 */
		// 步骤1:取年分的后两位数
		int y = year % 100;
		// 闰年
		int num5=4,num6=100,num7=400;
		boolean result = false;
		if (year % num5 == 0 && year % num6 != 0){
			result = true;
		}
		if (year % num7 == 0){
			result = true;
		}
		if (result) {
			if (ordinal == SolarTermsEnum.XIAOHAN.ordinal() || ordinal == SolarTermsEnum.DAHAN.ordinal()
					|| ordinal == SolarTermsEnum.LICHUN.ordinal() || ordinal == SolarTermsEnum.YUSHUI.ordinal()) {
				// 注意：凡闰年3月1日前闰年数要减一，即：L=[(Y-1)/4],因为小寒、大寒、立春、雨水这两个节气都小于3月1日,所以 y = y-1
				// 步骤2
				y = y - 1;
			}
		}
		// 步骤3，使用公式[Y*D+C]-L计算
		dateNum = (int) (y * D + centuryValue) - (int) (y / 4);
		// 步骤4，加上特殊的年分的节气偏移量
		dateNum += specialYearOffset(year, name);
		return dateNum;
	}

	/**
	 * 特例,特殊的年分的节气偏移量,由于公式并不完善，所以算出的个别节气的第几天数并不准确，在此返回其偏移量
	 * 
	 * @param year
	 *            年份
	 * @param name
	 *            节气名称
	 * @return 返回其偏移量
	 */
	private static int specialYearOffset(int year, String name) {
		int offset = 0;
		offset += getOffset(DECREASE_OFFSETMAP, year, name, -1);
		offset += getOffset(INCREASE_OFFSETMAP, year, name, 1);

		return offset;
	}

	private static int getOffset(Map<String, Integer[]> map, int year, String name, int offset) {
		int off = 0;
		Integer[] years = map.get(name);
		if (null != years) {
			for (int i : years) {
				if (i == year) {
					off = offset;
					break;
				}
			}
		}
		return off;
	}

	/**
	 * 判断一天是什么节气
	 * 
	 * @data2015-12-2下午2:49:32
	 * @param year
	 * @param data
	 *            月份占两位，日不确定，如一月一日为：011，五月十日为0510
	 * @return
	 */
	public static String getSolatName(int year, String data) {
		if (year != mYear) {
			solarTermToString(year);
		}
		if (mSolarData.contains(data)) {
			return mSolarName.get(mSolarData.indexOf(data));
		}
		else {
			return null;
		}
	}

	private static void solarTermToString(int year) {
		mYear = year;
		if (mSolarData != null) {
			mSolarData.clear();
		}
		else {
			mSolarData = new ArrayList<String>();
		}
		if (mSolarName != null) {
			mSolarName.clear();
		}
		else {
			mSolarName = new ArrayList<String>();
		}
		// 1
		mSolarName.add("立春");
		mSolarData.add("02" + getSolarTermNum(year, SolarTermsEnum.LICHUN.name()));
		// 2
		mSolarName.add("雨水");
		mSolarData.add("02" + getSolarTermNum(year, SolarTermsEnum.YUSHUI.name()));
		// 3
		mSolarName.add("惊蛰");
		mSolarData.add("03" + getSolarTermNum(year, SolarTermsEnum.JINGZHE.name()));
		// 4
		mSolarName.add("春分");
		mSolarData.add("03" + getSolarTermNum(year, SolarTermsEnum.CHUNFEN.name()));
		// 5
		mSolarName.add("清明");
		mSolarData.add("04" + getSolarTermNum(year, SolarTermsEnum.QINGMING.name()));
		// 6
		mSolarName.add("谷雨");
		mSolarData.add("04" + getSolarTermNum(year, SolarTermsEnum.GUYU.name()));
		// 7
		mSolarName.add("立夏");
		mSolarData.add("05" + getSolarTermNum(year, SolarTermsEnum.LIXIA.name()));
		// 8
		mSolarName.add("小满");
		mSolarData.add("05" + getSolarTermNum(year, SolarTermsEnum.XIAOMAN.name()));
		// 9
		mSolarName.add("芒种");
		mSolarData.add("06" + getSolarTermNum(year, SolarTermsEnum.MANGZHONG.name()));
		// 10
		mSolarName.add("夏至");
		mSolarData.add("06" + getSolarTermNum(year, SolarTermsEnum.XIAZHI.name()));
		// 11
		mSolarName.add("小暑");
		mSolarData.add("07" + getSolarTermNum(year, SolarTermsEnum.XIAOSHU.name()));
		// 12
		mSolarName.add("大暑");
		mSolarData.add("07" + getSolarTermNum(year, SolarTermsEnum.DASHU.name()));
		// 13
		mSolarName.add("立秋");
		mSolarData.add("08" + getSolarTermNum(year, SolarTermsEnum.LIQIU.name()));
		// 14
		mSolarName.add("处暑");
		mSolarData.add("08" + getSolarTermNum(year, SolarTermsEnum.CHUSHU.name()));
		// 15
		mSolarName.add("白露");
		mSolarData.add("09" + getSolarTermNum(year, SolarTermsEnum.BAILU.name()));
		// 16
		mSolarName.add("秋分");
		mSolarData.add("09" + getSolarTermNum(year, SolarTermsEnum.QIUFEN.name()));
		// 17
		mSolarName.add("寒露");
		mSolarData.add("10" + getSolarTermNum(year, SolarTermsEnum.HANLU.name()));
		// 18
		mSolarName.add("霜降");
		mSolarData.add("10" + getSolarTermNum(year, SolarTermsEnum.SHUANGJIANG.name()));
		// 19
		mSolarName.add("立冬");
		mSolarData.add("11" + getSolarTermNum(year, SolarTermsEnum.LIDONG.name()));
		// 20
		mSolarName.add("小雪");
		mSolarData.add("11" + getSolarTermNum(year, SolarTermsEnum.XIAOXUE.name()));
		// 21
		mSolarName.add("大雪");
		mSolarData.add("12" + getSolarTermNum(year, SolarTermsEnum.DAXUE.name()));
		// 22
		mSolarName.add("冬至");
		mSolarData.add("12" + getSolarTermNum(year, SolarTermsEnum.DONGZHI.name()));
		// 23
		mSolarName.add("小寒");
		mSolarData.add("01" + getSolarTermNum(year, SolarTermsEnum.XIAOHAN.name()));
		// 24
		mSolarName.add("大寒");
		mSolarData.add("01" + getSolarTermNum(year, SolarTermsEnum.DAHAN.name()));

	}

}
