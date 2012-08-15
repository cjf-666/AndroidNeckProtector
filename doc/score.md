##ScoreManager接口文档

- `public static ScoreManager getInstance()`

	* ####Description 

		用于获得全局唯一的分数管理类

- `public int deductScore(int n)`

	* ####Description 
  
		扣分函数

	* ####Parameters

		n *int*	要扣除的分数

	* ####Returns

		*int*	扣除后的总分

- `public int rewardScore(int n)`

	* ####Description 
  
		奖励函数

	* ####Parameters

		n *int*	要增加的分数

	* ####Returns

		*int*	增加后的总分

- `public int getScore()`

	* ####Description 
  
		得到目前分数

	* ####Parameters

		*void*

	* ####Returns

		*int*	当前总分

- `public void reset()`

	* ####Description 
  
		重置分数为100

	* ####Parameters

		*void*

	* ####Returns

		*void*	