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




