# DbFast
##DbFast解决什么
我们一般在android开发中，会用到本地存储功能，一般有sharedperference,file,sqlite。
Sqlite在我们开发过程中的比例占比更是不小。
  
大家都做过sqlite开发，得从创建数据，到创建表，然后编写增删改查的各种功能。
其实代码并不多，但是如果每次一个新项目需要用到sqlite数据库我们都得重新写这些代码，
那就不符合我们编码思想中的重复造轮子了，我们需要创造一个轮子的机器。

DbFast,顾名思义，让Db的开发更快速。
为了解决之前所说的数据库开发的重复造轮子问题，我从数据库的创建到数据库的调用，从下至上封装了一个lib库，让开发者可以根据自己的业务，快速创建数据库与操作数据库的功能。而不用关心底层数据库、表的创建。
当然这里只是简单介绍，DbFast的源码解析就不在这里说了，只大概介绍一下DbFast是利用了java的继承、泛型、反射等原理搭建而成的ORM(对象关系映射)的开发组件，在下面会介绍DbFast的相关调用。


##DbFast优点与缺点
   优点：
   <br>1.开发配置快速，几行代码搞定。<br>
         2.接口调用简单，与sqlite原生语义基本一样。<br>
         3.开发DbFast的开发者帅气可爱。

   缺点
   <br>1.实现比较粗糙，底层大量用到了反射，貌似性能会有影响<br>
        2.对主外键、多表连接的支持基本无(后续会好好的优化).<br>
        3.最近貌似头发又少了。
  
  
##调用
###1.创建和初始化数据库
我们先看一段代码：<br>
**List<Class<?>> clz_list = new ArrayList<Class<?>>();<br>
clz_list.add(User.class);<br>
clz_list.add(News.class);**

   可以看到，我们先创建了一个List集合，类型为Class<?>。
   然后给这个list添加了许多个class类。

**DBConfig config = DBConfig.getDBConfig();<br>
config.DB_NAME = "Con_Rec.db";<br>
config.DB_VERSION = 1;**

然后通过DBConfig的getDBConfig()方法获得一个DBConfig对象，并设置它的2个属性。<br>
通过名字可以看出是设置了数据库的name和version。<br>

最后，我们来创建和初始化数据库
DbFastControl.getDbFast().init(this, config, clz_list);<br>
调用init方法，并将当前context最好是applicationContext传入，然后config对象，和存满了class的集合传入。

这样子我们数据库就创建好了，你会奇怪，我都没有创建表，就是指定了个数据库的名字和版本怎么数据库就创建好了呢。

DbFast创建的关键的地方就在这里了，基于对象关系映射的原理，我在这里认为每一个对象都是数据库里的一条记录，对象的class则是我数据库的一张表。

所以，list的里面的每一个class对象，都会被我创建成一张表，到这里你应该就会有所领悟了，我们不需要再创建各种create table语句了，只需要把你想要创建的表的信息反过来生成一个class，就可以了。<br>

看class的一个例子<br>
    
    public class News {

    private int _id;
    
    private String news_id;

    private String news_title;

    private String news_content;
    
    private String news_author;
<br>
   像我们创建类一样，类的属性，get setter方法。
   所有你想要在数据库表里有的字段，都是你类的属性映射的。

   要注意的是：
   <br>1.属性的name就是你表的字段名
   <br>2.属性的访问修饰符只能是private
###2.查询接口
######条件查询接口:<br>
public List<Object> query(Class<?> clz, String where, String[] whereArgs)

######实例:<br>
List<'Object> list_obj = DbFastControl.getDbFast().query(User.class,"uid=? and uname=?",new String[]{"1","feng"});<br>
if(list_obj != null && list_obj.size() > 0){<br>
User user = (User)list_obj.get(0);<br>
}<br>
######查询某表全部数据接口:<br>
public List<Object> queryAll(Class<?> clz)<br>

######实例:<br>
List<'Object> list_obj = DbFastControl.getDbFast().query(User.class); <br>
if(list_obj != null && list_obj.size() > 0){<br>
List user = (User)list_obj.get(0);<br>
}<br>

###3.删除接口
######删除接口:<br>
public int delete(Class<?> clz, String where, String[] whereArgs)<br>

######实例:<br>
int count = DbFastControl.getDbFast().delete(User.class,"uid = ?",new String[]{uid});<br>
    if(count > 0){<br>
          delete success<br>
	}<br>
	else{<br>
  	   delete failed<br>
    }<br>

###4.添加数据接口
######增加多条数据接口: <br>
public long insertList(List<Object> clzs)<br>

######实例:<br>
List<CLASS> clz_list = new Arraylist<CLASS>();<br>
User u = new User();<br>
u.setUid("100<br>
u.setUName("fengyagang");<br>
clz_list.add(u);<br>
User u2 = new User();<br>
u2.setUid("100");<br>
u2.setUName("nidaye");<br>
clz_list.add(u2<br>

int count = DbFastControl.getDbFast().insert(clz_list);<br>
if(count > 0)<br>
{<br>
insert success<br>
}<br>
else<br>
{<br>
insert failed<br>
}<br>



######增加单条数据接口: <br>
public long insert(Object clz)

######实例:<br>
User u = new User();<br>
	u2.setUid("100");<br>
	u2.setUName("nidaye");<br>
	clz_list.add(u2);<br>
	int count = DbFastControl.getDbFast().insert(u);<br>
	if(count > 0)<br>
	{<br>
 	    insert success<br>
	}<br>
	else<br>
	{<br>
  	    insert failed<br>
	}<br>
 
###5.修改数据接口
######修改数据接口:<br>
public int update(Object clzs, String where, String[] whereArgs)<br>

######实例:
User u = new User();<br>
	u.setUid("100");<br>
	u.setUName("fengyagang");//原来的数据为yagangfeng，要修改成fengyagang<br>

	DbFastControl.getDbFast().update(u,"uid = ?",new String[]{"100"});<br>


###6.Clear 清空表里所有的数据<br>
######清空数据接口:<br>
public long clear(Class<?> clz)<br>

######实例:
DbFastControl.getDbFast().clear(User.class);

   
