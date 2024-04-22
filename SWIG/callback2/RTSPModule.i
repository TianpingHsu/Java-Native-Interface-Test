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