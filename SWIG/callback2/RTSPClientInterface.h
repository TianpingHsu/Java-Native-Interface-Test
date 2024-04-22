#ifndef __RTSP_CLIENT_INTERFACE_H__
#define __RTSP_CLIENT_INTERFACE_H__
#include <vector>
#include <string>

class MessageCallback {
    public:
		virtual ~MessageCallback() {}
		virtual void OnMessage(std::string message)
        {
            // printf("%s \n", message.c_str());
        }
};
class FrameCallback {
    public:
		virtual ~FrameCallback() {}
		virtual void OnFrame(const void* pData, int nSize)
        {
            
        }   
        virtual void GetFrame(const char* data, int size)
        {
            
        } 
};

class RTSPClientInterface {
public:
    RTSPClientInterface();
    ~RTSPClientInterface();
    void SetMessageCallback(MessageCallback* cb);
    void SetFrameCallback(FrameCallback *cb);
    std::vector<std::string> GetClientList();
    //调用message回调
    bool Init();
    //调用frame回调 const void * 转byte[]
    void SendFrameData();

    //调用frame回调 char *转byte[]
    void SendFrameVoidData();


private:
    void* rtsp_client_;
};

#endif
