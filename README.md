
#### Demo实例：进程间通信实现两个整形参数求和。

### 该实例中AIDL的进程间通信的流程如下：
#### 服务端：

1、服务端创建了一个Service(CalculateService),来监听客户端的连接请求。

2、创建一个AIDL文件(CalculateInterface.aidl)，将暴露给客户端的接口(interface CalculateInterface)在这个AIDL文件中声明。

3、在Service(CalculateService)中实现该AIDL接口即可。

#### 客户端：

1、绑定服务端的Service（CalculateService）。

2、绑定成功后，将服务端返回的Binder对象（service）转换成AIDL接口所属的类型（CalculateInterface）。

3、然后就可以远程调用AIDL中的方法了（mService.doCalculate(num1, num2)）。



#### Binder连接池
背景：
实际的项目开发中，如果有多个业务使用到AIDL通信，按照上面介绍的方式，那么我们可能会建立多个Service，这将会
严重地加重APP的负担。最好的方式就是将所有的AIDL放在同一个Service中取管理。

实现：
它的工作机制是这样的---

1、创建Server多个AIDL接口（定义）

2、将相应AIDL接口实现（实现）

3、BinderPool AIDL文件定义（定义）

4、BinderPool AIDL文件实现（实现），根据不同业务模块标识，返回不同的Binder对象。

5、通过不同的binderCode进行AIDL调用，来获取不同的Binder。【客户端】

6、这样就可以进行实际的业务接口调用了。
