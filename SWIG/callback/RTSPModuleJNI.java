/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.1
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */


public class RTSPModuleJNI {
  public final static native void delete_FrameCallback(long jarg1);
  public final static native void FrameCallback_OnFrame(long jarg1, FrameCallback jarg1_, String jarg2, long jarg3, long jarg4);
  public final static native void FrameCallback_OnFrameSwigExplicitFrameCallback(long jarg1, FrameCallback jarg1_, String jarg2, long jarg3, long jarg4);
  public final static native long new_FrameCallback();
  public final static native void FrameCallback_director_connect(FrameCallback obj, long cptr, boolean mem_own, boolean weak_global);
  public final static native void FrameCallback_change_ownership(FrameCallback obj, long cptr, boolean take_or_release);
  public final static native long new_RTSPClientInterface();
  public final static native void delete_RTSPClientInterface(long jarg1);
  public final static native void RTSPClientInterface_SetFrameCallback(long jarg1, RTSPClientInterface jarg1_, long jarg2, FrameCallback jarg2_);
  public final static native boolean RTSPClientInterface_Init(long jarg1, RTSPClientInterface jarg1_);

  public static void SwigDirector_FrameCallback_OnFrame(FrameCallback jself, String data, long len, long timestamp) {
    jself.OnFrame(data, len, timestamp);
  }

  private final static native void swig_module_init();
  static {
    swig_module_init();
  }
}