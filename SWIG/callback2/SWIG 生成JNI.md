#### SWIG 生成JNI



##### RTSPModule.i

```
%module(directors="1") RTSPModule
%{
#include <string> 
#include <vector>
#include "RTSPClientInterface.h"   
%}
//使用std::string
%include "std_string.i"

//C++回调给上层
%feature("director") MessageCallback;
%feature("director") FrameCallback;
 

// 告诉 SWIG 我们想要使用 std::vector<std::string> 暴露的接口 
%include "std_vector.i"
%template(StringVector) std::vector<std::string>; 

//转换C++的void * 到java层的byte[]
%include "various.i"
//按照char *STRING, size_t LENGTH的规则，来处理const void *, int，即将两个参数转成一个参数byte[] 后面是字符数组长度
%apply (char *STRING, size_t LENGTH) {(const void* pData, int nSize)};

//转换C++的const char * 到Java的byte[]
%apply (const char *STRING, size_t LENGTH) {(const char* data, int size)};


%include "RTSPClientInterface.h"
```



##### RTSPClientInterface.h

```cpp
#include "RTSPClientInterface.h"
#include <functional>
#include <cstring>
#define IMP(p) static_cast<RTSPClientImp*>(p)
class RTSPClientImp {
    public:
        RTSPClientImp(): cb_(nullptr){
            list_ = {"cgh", "xtp", "wjh", "wdx", "wlz"};
        }
        void SetMessageCallback(MessageCallback* cb) {
            cb_ = cb;
        }

        void SetFrameCallback(FrameCallback* cb) {
            
            frame_cb_  = cb;
        }

        std::vector<std::string> GetList()
        {
            return list_;
        }

    public:
        MessageCallback* cb_;
        FrameCallback* frame_cb_;
        std::vector<std::string> list_;
};



RTSPClientInterface::RTSPClientInterface() {
    rtsp_client_ = (void*)(new RTSPClientImp);
}

RTSPClientInterface::~RTSPClientInterface() {
    if (rtsp_client_) delete static_cast<RTSPClientImp*>(rtsp_client_);
}




void RTSPClientInterface::SetMessageCallback(MessageCallback* cb) {
    IMP(rtsp_client_)->SetMessageCallback(cb); 
}

void RTSPClientInterface::SetFrameCallback(FrameCallback *cb)
{
    IMP(rtsp_client_)->SetFrameCallback(cb);
}

std::vector<std::string> RTSPClientInterface::GetClientList()
{
    return IMP(rtsp_client_)->GetList();
}

bool RTSPClientInterface::Init() {
    if(IMP(rtsp_client_)->cb_)
    {
        IMP(rtsp_client_)->cb_->OnMessage("cgh test");
    }
    return true;
}

void RTSPClientInterface::SendFrameData()
{
    // const char* str = "RTSPCLIENT";  
    const char str[] = "RTSPCLIENT"; 
    if(IMP(rtsp_client_)->frame_cb_)
    {
        printf("sendframedata \n");
        // IMP(rtsp_client_)->frame_cb_->OnFrame(str, 10 );
        IMP(rtsp_client_)->frame_cb_->OnFrame(&str[0], 10 );
    }
}

void RTSPClientInterface::SendFrameVoidData()
{
    const char *str = "rtsp client";

    if(IMP(rtsp_client_)->frame_cb_)
    {
        printf("sendframedata \n");
        IMP(rtsp_client_)->frame_cb_->GetFrame(str, strlen(str));
    }
}

```

##### RTSPClientInterface.cpp

```cpp
#include "RTSPClientInterface.h"
#include <functional>
#include <cstring>
#define IMP(p) static_cast<RTSPClientImp*>(p)
class RTSPClientImp {
    public:
        RTSPClientImp(): cb_(nullptr){
            list_ = {"cgh", "xtp", "wjh", "wdx", "wlz"};
        }
        void SetMessageCallback(MessageCallback* cb) {
            cb_ = cb;
        }

        void SetFrameCallback(FrameCallback* cb) {
            
            frame_cb_  = cb;
        }

        std::vector<std::string> GetList()
        {
            return list_;
        }

    public:
        MessageCallback* cb_;
        FrameCallback* frame_cb_;
        std::vector<std::string> list_;
};



RTSPClientInterface::RTSPClientInterface() {
    rtsp_client_ = (void*)(new RTSPClientImp);
}

RTSPClientInterface::~RTSPClientInterface() {
    if (rtsp_client_) delete static_cast<RTSPClientImp*>(rtsp_client_);
}




void RTSPClientInterface::SetMessageCallback(MessageCallback* cb) {
    IMP(rtsp_client_)->SetMessageCallback(cb); 
}

void RTSPClientInterface::SetFrameCallback(FrameCallback *cb)
{
    IMP(rtsp_client_)->SetFrameCallback(cb);
}

std::vector<std::string> RTSPClientInterface::GetClientList()
{
    return IMP(rtsp_client_)->GetList();
}

bool RTSPClientInterface::Init() {
    if(IMP(rtsp_client_)->cb_)
    {
        IMP(rtsp_client_)->cb_->OnMessage("cgh test");
    }
    return true;
}

void RTSPClientInterface::SendFrameData()
{
    // const char* str = "RTSPCLIENT";  
    const char str[] = "RTSPCLIENT"; 
    if(IMP(rtsp_client_)->frame_cb_)
    {
        printf("sendframedata \n");
        // IMP(rtsp_client_)->frame_cb_->OnFrame(str, 10 );
        IMP(rtsp_client_)->frame_cb_->OnFrame(&str[0], 10 );
    }
}

void RTSPClientInterface::SendFrameVoidData()
{
    const char *str = "rtsp client";

    if(IMP(rtsp_client_)->frame_cb_)
    {
        printf("sendframedata \n");
        IMP(rtsp_client_)->frame_cb_->GetFrame(str, strlen(str));
    }
}





```



##### JavaMessageCallBack.java

```java
class JavaMessageCallBack extends MessageCallback
{
    public JavaMessageCallBack()
    {
        super();
    }

    public void OnMessage(String message)
    {
        System.out.println("java OnMessage");
        System.out.println(message);
    }
}
```



##### JavaFrameCallback.java

```java
import java.util.Arrays; 
public class JavaFrameCallback extends FrameCallback{
    public JavaFrameCallback()
    {
        
        super();
        System.out.println("==================");
    }
    public void OnFrame(byte[] pData)
    {
        System.out.println("==================");
        System.out.println("pData legth=" + pData.length);
        // System.out.println(Arrays.toString(Arrays.copyOf(pData, pData.length)));
        for (byte b : pData) {  
            System.out.printf("%02X ", b);  
        } 
        System.out.println();
        System.out.println("==================");
    }
    public void GetFrame(byte[] data)
    {
        System.out.println("~~~~~~~~~~~~~~~");
        System.out.println("buf legth=" + data.length);
        for (byte b : data) {  
            System.out.printf("%02X ", b);  
        } 
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~");
    }
}

```

##### runme.java测试demo

```java

public class runme
{
    static {
        try {
            //加载.so库
            System.loadLibrary("RTSPClientInterface");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load. See the chapter on Dynamic Linking Problems in the SWIG Java documentation for help.\n" + e);
            System.exit(1);
        }
    }

    public static void main(String[] args)
    {

        RTSPClientInterface  caller = new RTSPClientInterface();

        // JavaMessageCallBack message_callback = new JavaMessageCallBack();
        MessageCallback message_callback = new JavaMessageCallBack();
        FrameCallback frame_callback = new JavaFrameCallback();

        System.out.println();
        System.out.println("Adding and calling a Java callback");
        System.out.println("------------------------------------");

        //注册回调
        caller.SetMessageCallback(message_callback);
        caller.SetFrameCallback(frame_callback);

        //调用messageCallback;
        caller.Init();

        //调用c++底层的SendFrameData向java层发送回调信息
        caller.SendFrameData();

        caller.SendFrameVoidData();
        
        System.out.println("------------------------------------");
        StringVector stringVector = caller.GetClientList();
        System.out.println("------------------------------------");
        for(String vec:stringVector)
        {
            System.out.println(vec);
        }

        
        
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
javac ./*.java
//加入环境
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:./
//运行
java runme
```

