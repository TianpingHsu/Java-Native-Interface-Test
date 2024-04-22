#ifndef __RTSP_CLIENT_INTERFACE_H__
#define __RTSP_CLIENT_INTERFACE_H__


class FrameCallback {
    public:
		virtual ~FrameCallback() {}
		virtual void OnFrame(const void* pData, int nSize, unsigned int timestamp)
        {
            
        }
};

class RTSPClientInterface {
public:
    RTSPClientInterface();
    ~RTSPClientInterface();
    void SetFrameCallback(FrameCallback* cb);
    bool Init();
private:
    void* rtsp_client_;
};

#endif
