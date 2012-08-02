#数据库及接口设计文档

##概述

1. 目前数据库中仅存放今日的详细分析数据，以后可以考虑改为用户设置保存详细数据的时间长度。
2. 除今日外，仅提供曾经某一天的使用总时间，以及从有记录开始到目前的总的分析数据，以节省存储空间。
3. 随着功能的增加接口也会增加，但会尽量保证之前的接口不做改变，除非在与调用着协商之后。
4. 功能上的需求可以随时增加，数据库存放的分析内容也可以随时根据需要进行增加。

##数据库接口

###数据库管理类DataManager

- `public static DataManager getInstance()`

	* ####Description 

		用于获得全局唯一的数据库管理类

- `public void putData(Date begin, Date end)`

	* ####Description 
  
		提供给监控的Listener或Service使用，用于向数据库添加数据。

	* ####Parameters

		begin *java.util.Date*	用户使用手机的起始时间

		end *java.util.Date*	用户使用手机的结束时间

	* ####Returns

		*No Returns Value*

- `public ContentValues getStatOfToday()`

	* ####Description 
  
		获得今日统计数据。

	* ####Parameters

		 *void*
	
	* ####Returns

		ContentValues包含：

		* total *long* 今日使用总时长 单位：秒

		* a0_5, a5_10, a10_30, a30_INF *long* 用户今日使用手机5分钟以下、5～10分钟、10～30分钟、30分钟以上的时间 单位：秒

		* z0_2, z2_4, z4_6, z6_8, z8_10, z10_12, z12_14, z14_16, z16_18, z18_20, z20_22, z22_24 *long* 用户今日在0点～2点、2点～4点、……22点～24点使用手机的时间 单位：秒

- `public ContentValues getStat()`

	* ####Description 
  
		获得总统计数据。

	* ####Parameters

		 *void*
	
	* ####Returns

		参看getStatOfToday函数。

- `public long getHistroy(Date date)`

	* ####Description 
  
		获得某一天的使用手机时长。

	* ####Parameters

		 date *java.util.Date* 需要获取的日期
	
	* ####Returns

		获取某日使用时长。-1表示无记录。

##数据库设计

- ###detail表

	* 存放详细统计数据（各个时间段的使用时间）

	* 列：

		_id INTEGER PRIMARY KEY, z0_2 TEXT, z2_4 TEXT, z4_6 TEXT, z6_8 TEXT, z8_10 TEXT, z10_12 TEXT, z12_14 TEXT, z14_16 TEXT, z16_18 TEXT, z18_20 TEXT, z20_22 TEXT, z22_24 TEXT

- ###detail_length表

	* 存放详细统计数据（用户每次使用手机的时间长度分类）

	* 列

		_id INTEGER PRIMARY KEY, a0_5 TEXT, a5_10 TEXT, a10_30 TEXT, a30_INF TEXT

- ###stat_time表

	* 存放总的详细统计数据（各个时间段的使用时间）

	* 列：同detail表 另多一列from TEXT存储从哪一天开始记录

- ###stat_length表

	* 存放总的详细统计数据（用户每次使用手机的时间长度分类）

	* 列：同detail_length表 另多一列from TEXT存储从哪一天开始记录

- ###histroy表

	* 存放历史数据（每天的使用总时间）

	* 列：

		_id INTEGER PRIMARY KEY, date TEXT, time TEXT