# extension-fields
kotlin extension fields, 支持backing field，线程安全

例子：

1:给MainActivity添加一个叫test的只读属性，值为100

val MainActivity.test : Int by field { 100 }



2:给MainActivity添加一个叫test2的可读可写属性，默认值为100

var MainActivity.test2 : Int by field { 100 }
