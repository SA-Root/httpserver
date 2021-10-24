#!D:\Anaconda\envs\basic\python.exe

# CGI处理模块
import cgi

# 创建 FieldStorage 的实例化
form = cgi.FieldStorage()

# 获取数据
site_name = form.getvalue('name')
site_url = form.getvalue('url')

print("Content-type:text/html")
print()
print("<html>")
print("<head>")
print("<meta charset=\"utf-8\">")
print("<title>DataBase\n</title>")
print("</head>")
print("<body>")

inputs = input()  # get a and b
inputs = inputs.split("\0")[0]
length = len(inputs)

Number = inputs.split("=")[1]
Name, Class = None, None
print("<h2>Data Query by Group X</h2>")

import pymysql

database = pymysql.connect(host="localhost",
                           user="gt",
                           password="111111",
                           database="entry"
                           )

cursor = database.cursor()
cursor.execute("DROP TABLE IF EXISTS stuinfo")

create_tbl = """CREATE TABLE stuinfo (
         id INT NOT NULL AUTO_INCREMENT,
         name VARCHAR(40) NOT NULL,
         class VARCHAR(80) NOT NULL,
         PRIMARY KEY (id))ENGINE=InnoDB DEFAULT CHARSET=utf8;
         """
cursor.execute(create_tbl)

s_name = ['student1', 'student2', 'student3']
s_number = [1120181111, 1120182222, 1120183333]
s_class = ['07111802', '07111802', '07111802']

for i in range(0, 3):
    try:
        insert_bdata = "INSERT INTO stuinfo\
                (id, name, class)\
                VALUES (%s, '%s', '%s')" % \
                       (s_number[i], s_name[i], s_class[i])
        cursor.execute(insert_bdata)
        database.commit()
    except:
        database.rollback()

query = "SELECT name, class FROM stuinfo \
         WHERE id = %s" % (int(Number))

try:
    cursor.execute(query)
    result = cursor.fetchone()
    if len(result) > 0:
        Name = result[0]
        Class = result[1]
    else:
        Name, Class = None, None
except:
    Name, Class = None, None

if Name != None and Class != None:
    print("<li><span style='color:green'> Number: </span> : %s </li>" % (Number))
    print("<li><span style='color:green'> Name: </span> : %s </li>" % (Name))
    print("<li><span style='color:green'> Class: </span> : %s </li>" % (Class))
else:
    print("<li><span style='color:red'> Student of Number %s is not found! </span></li>" % (Number))

print("</ul>")
print("</body>")
print("</html>")

database.close()
