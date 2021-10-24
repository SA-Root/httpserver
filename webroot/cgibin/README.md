1. 需要安装**pymysql**库，可以直接借助conda或者pip下载；同理，下载到运行cgi程序的默认解释器中；所给的初始 data.py CGI程序的默认解释器需要按照对应运行环境进行修改：此处解释器名为basic![76668f6aef129e59c85457290aefd44](C:\Users\nh\AppData\Local\Temp\WeChat Files\76668f6aef129e59c85457290aefd44.png)
2. 下载完毕之后，可能会出现pymysql库引入失败的情况，可以尝试下面网址中第三步所给解决问题的方法：https://www.jianshu.com/p/192ade53c6de
3. 需要下载**mysql 8.0**或更高版本，配置信息不限，具体参见官网下载链接 https://dev.mysql.com/downloads/
4. 下载mysql之后，如果需要在SHELL窗体内新建登录用户（不建议这么做，因为也可以在Install程序内建立），需注意**mysql 8.0**版本及以上的密码字段从**password**变更成为**authentication string**字段
5. 初始化完毕mysql之后，至少需要在相对应的登录用户底下构建一个登录数据库

![a88fc139caa3c6ed33ab9a7ecfb0d67](C:\Users\nh\AppData\Local\Temp\WeChat Files\a88fc139caa3c6ed33ab9a7ecfb0d67.png)

> 如上图所示，Python CGI程序中建立的数据库为entry，作为python cgi程序链接的依据；
>
> 登录用户为gt，密码为111111；在连接完毕之后可以进行建表、选择数据段等操作

