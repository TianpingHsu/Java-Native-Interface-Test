/* File : example.i */
%module(directors="1") RTSPModule
%{
#include "RTSPClientInterface.h"
%}

/* turn on director wrapping Callback */
%feature("director") FrameCallback;

%include "RTSPClientInterface.h"
