# file: common.struct

namespace java com.moseeker.thrift.gen.common.struct

struct Response { 
    1: i32 status,
    2: string message,
    3: optional string data
}

enum SelectOp {
    FIELD,DISTINCT,COUNT,COUNT_DISTINCT,SUM,AVG,MAX,MIN,UCASE,LCASE,LEN,ROUND,TRIM
}

struct Select {
    1: string field,
    2: SelectOp op = SelectOp.FIELD
}

//OrderBy枚举
enum Order{
    ASC,DESC
}

//OrderBy
struct OrderBy{
    1: string field,
    2: Order order
}

//两个条件之间关系枚举
enum  ConditionOp{
    AND,OR
}

//字段和值之间的关系枚举
enum  ValueOp{
    EQ,NEQ,GT,GE,LT,LE,IN,NIN,BT,NBT,LIKE,NLIKE
}

//两个条件之间关系
struct InnerCondition{
    1: Condition firstCondition,	//第一个条件
    2: Condition secondCondition,	//第二个条件
    3: ConditionOp conditionOp,		//条件枚举
}

//字段和值之间的关系
struct ValueCondition{
    1: string field,				//字段名字
    2: string value,				//目标参考值
    3: ValueOp valueOp				//对比关系枚举
}

//条件节点，指的是里面条件的类型，两种条件只能存在一个
struct Condition{
    1: optional InnerCondition innerCondition,	//不为空的话表示里面有两个子条件
    2: optional ValueCondition valueCondition	//不为空的话表示里面只有一个条件
}

//单表查询结构
struct CommonQuery {
    1: optional list<Select> attributes,	    //选择
    2: optional Condition conditions,			//表示Condition的字段，替换掉之前的equalFilter
    3: optional list<OrderBy> orders,			//替换掉原来的order和sortby
    4: optional list<string> groups,
    5: optional i32 pageSize,
    6: optional i32 page,
    7: optional map<string,string> extras
}

//单表更新结构
struct CommonUpdate {
    1: optional Condition conditions,			//表示Condition的字段，替换掉之前的equalFilter
    2: optional map<string,string> fieldValues,
    3: optional map<string,string> extras
}

//单表删除操作直接使用Conndition

//单表添加直接使用map<string,string>

/**
*例：
*			条件								表示方法
*
*			A: id = 1							Condition condition1 = new Condition();
*												condition1.setValuecondition(new ValueCondition("id","1",ValueOp.EQ));
*
*
*			B: age >= 18						Condition condition2 = new Condition();
*												condition2.setValueCondition(new ValueCondition("age","3",ValueOp.GE));
*
*			C: sallery in (3500,10000)			Condition condition3 = new Condition();
*												condition3.setValueCondition(new ValueCondition("sallery","[3500,10000]",ValueOp.IN));
*
*			D: A AND B OR C						Condition condition4 = new Condition();//A和B的关系
*												condition4.setInnercondition(new InnerCondition(condition1,condition2,ConditionOp.AND));
*												InnerCondition innerCondition2 = new InnerCondition(condition4,condition3,ConditionOp.OR);//（A AND B）和 C的关系
*												Condition condition5 = new Condition();
*												condition5.setInnercondition(innerCondition2);
*/

exception CURDException {
    1: i32 code,
    2: string message
}