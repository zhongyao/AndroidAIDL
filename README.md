###该实例中AIDL的进程间通信的流程如下：
####服务端：
1、服务端创建了一个Service(CalculateService),来监听客户端的连接请求。
2、创建一个AIDL文件(CalculateInterface.aidl)，将暴露给客户端的接口(interface CalculateInterface)在这个AIDL文件中声明。
3、在Service(CalculateService)中实现该AIDL接口即可。

####客户端：
1、绑定服务端的Service（CalculateService）。
2、绑定成功后，将服务端返回的Binder对象（service）转换成AIDL接口所属的类型（CalculateInterface）。
3、然后就可以远程调用AIDL中的方法了（mService.doCalculate(num1, num2)）。