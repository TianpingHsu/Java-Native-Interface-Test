#### SWIG 生成JNI



##### RTSPModule.i

```
%module(directors="1") RTSPModule
%{
#include <string> 
#include <vector>
#include "RTSPClientInterface.h"
%}

%include "std_string.i"

// 导入 std::vector 的支持  
%include "std_vector.i" 

// 告诉 SWIG 我们想要使用 std::vector<std::string>  
%template(StringVector) std::vector<std::string>; 

//用于实现 Java 回调函数的代码，以便在 Java 中实现接口，并将这些接口作为参数传递给 C++ 函数
%feature("director") FrameCallback;

%include "RTSPClientInterface.h"

```



##### RTSPClientInterface.h

```cpp
#ifndef __RTSP_CLIENT_INTERFACE_H__
#define __RTSP_CLIENT_INTERFACE_H__
#include <vector>
#include <string>

class FrameCallback {
    public:
		virtual ~FrameCallback() {}
		virtual void OnFrame(const char* data, unsigned int len, unsigned int timestamp)
        {

        }
};

class RTSPClientInterface {
public:
    RTSPClientInterface();
    ~RTSPClientInterface();
    void SetFrameCallback(FrameCallback* cb);
    std::vector<std::string> GetClientList();
    bool Init();
private:
    void* rtsp_client_;
};

#endif

```

##### RTSPClientInterface.cpp

```cpp
#include "RTSPClientInterface.h"


class RTSPClientImp {
    public:
        RTSPClientImp(): cb_(nullptr){
            list_ = {"cgh", "xtp", "wjh", "wdx", "wlz"};
        }
        void SetFrameCallback(FrameCallback* cb) {
            cb_ = cb;
        }

        std::vector<std::string> GetList()
        {
            return list_;
        }

    public:
        FrameCallback* cb_;
        std::vector<std::string> list_;
};

#define IMP(p) static_cast<RTSPClientImp*>(p)

RTSPClientInterface::RTSPClientInterface() {
    rtsp_client_ = (void*)(new RTSPClientImp);
}

RTSPClientInterface::~RTSPClientInterface() {
    if (rtsp_client_) delete static_cast<RTSPClientImp*>(rtsp_client_);
}

std::vector<std::string> RTSPClientInterface::GetClientList()
{
    return IMP(rtsp_client_)->GetList();
}

bool RTSPClientInterface::Init() {
    if(IMP(rtsp_client_)->cb_)
    {
        IMP(rtsp_client_)->cb_->OnFrame("test",0,0);
    }
    return true;
}

void RTSPClientInterface::SetFrameCallback(FrameCallback* cb) {
    IMP(rtsp_client_)->SetFrameCallback(cb); 
}

```

##### JavaCallback.java

```java
//JavaCallback 类继承了 FrameCallback 类中的属性和方法,
class JavaCallback extends FrameCallback
{
    public JavaCallback()
    {
        super();
    }

    public void OnFrame(String data, long len, long timestamp)
    {
        System.out.println("JavaCallback.run()");
    }
}
```

##### runme.java测试demo

```java


public class runme
{
    static {
        try {
            System.loadLibrary("RTSPClientInterface");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load. See the chapter on Dynamic Linking Problems in the SWIG Java documentation for help.\n" + e);
            System.exit(1);
        }
    }

    public static void main(String[] args)
    {
        //System.out.println("Adding and calling a normal C++ callback");
        //System.out.println("----------------------------------------");

        
        RTSPClientInterface              caller = new RTSPClientInterface();
        //Callback            callback = new Callback();

        //caller.setCallback(callback);
        //caller.call();
        //caller.delCallback();
        JavaCallback callback;
        callback = new JavaCallback();

        System.out.println();
        System.out.println("Adding and calling a Java callback");
        System.out.println("------------------------------------");

        caller.SetFrameCallback(callback);
        caller.Init();

        StringVector stringVector = caller.GetClientList();
        for(String vec:stringVector)
        {
            System.out.println(vec);
        }
        // caller.OnFrame("hello world", 0, 0);
        // caller.delCallback();

        System.out.println();
        System.out.println("java exit");
    }
}

```

##### 生成指令

```shell
//生成JNI以及暴露给java的接口(RTSPClientInterface.java)
swig -c++ -java RTSPModule.i

//.cxx是自动生成JNI 后面是自己java环境路径
g++ -fPIC -c RTSPClientInterface.cpp  RTSPModule_wrap.cxx -I/usr/lib/jvm/java-1.11.0-openjdk-amd64/include/ -I/usr/lib/jvm/java-1.11.0-openjdk-amd64/include/linux

//生成.so库
g++ -shared RTSPClientInterface.o RTSPModule_wrap.o -o libRTSPClientInterface.so

//生成头文件
javac FrameCallback.java 
javac JavaCallBack.java 
javac runme.java

//运行
java runme
```

