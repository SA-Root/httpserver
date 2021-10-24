#!/usr/bin/python3

# CGI处理模块
import cgi, cgitb

# 创建 FieldStorage 的实例化
form = cgi.FieldStorage()

# 获取数据
site_name = form.getvalue('name')
site_url  = form.getvalue('url')


print ("Content-type:text/html")
print ()
print ("<html>")
print ("<head>")
print ("<meta charset=\"utf-8\">")
print ("<title>Calculator\n</title>")
print ("</head>")
print ("<body>")


inputs=input()                              # get a and b
inputs=inputs.split("\0")[0]
length = len(inputs)

a, b = inputs.split("&")
a = int(a.split('=')[1])
b = int(b.split('=')[1])

# inputs=input().split('')
print ("<h2>Calculator by Group X</h2>")

print ("<meta charset=\"utf-8\">")
# print ("<b>环境变量</b><br>")
print ("<ul>")
print ("<li><span style='color:green'>%d + %d </span> = %d </li>" % (a, b, a+b))
print ("<li><span style='color:green'>%d * %d </span> = %d </li>" % (a, b, a*b))
print ("</ul>")
print ("</body>")
print ("</html>")