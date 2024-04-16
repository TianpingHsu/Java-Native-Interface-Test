#include "RTSPClientInterface.h"

//class FrameCallback {
//    public:
//		virtual ~FrameCallback() {}
//		virtual void OnFrame(const void *data, unsigned int len, unsigned int timestamp);
//};
//
//class RTSPClientInterface {
//public:
//    RTSPClientInterface();
//    ~RTSPClientInterface();
//    void SetFrameCallback(FrameCallback* cb);
//    bool Init();
//private:
//    void* rtsp_client_;
//};

class RTSPClientImp {
    public:
        RTSPClientImp(): cb_(nullptr){}
        void SetFrameCallback(FrameCallback* cb) {
            cb_ = cb;
        }
    public:
        FrameCallback* cb_;
};

#define IMP(p) static_cast<RTSPClientImp*>(p)

RTSPClientInterface::RTSPClientInterface() {
    rtsp_client_ = (void*)(new RTSPClientImp);
}

RTSPClientInterface::~RTSPClientInterface() {
    if (rtsp_client_) delete static_cast<RTSPClientImp*>(rtsp_client_);
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
