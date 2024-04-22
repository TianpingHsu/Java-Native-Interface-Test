/* File : example.i */
%module(directors="1") RTSPModule
%{
#include "RTSPClientInterface.h"
%}

/* turn on director wrapping Callback */
%feature("director") FrameCallback;

%include "various.i"
%apply (char *STRING, size_t LENGTH) {(const void* pData, int nSize)};


%include "RTSPClientInterface.h"

