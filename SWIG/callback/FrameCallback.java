/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.1
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */


public class FrameCallback {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected FrameCallback(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(FrameCallback obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        RTSPModuleJNI.delete_FrameCallback(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  protected void swigDirectorDisconnect() {
    swigCMemOwn = false;
    delete();
  }

  public void swigReleaseOwnership() {
    swigCMemOwn = false;
    RTSPModuleJNI.FrameCallback_change_ownership(this, swigCPtr, false);
  }

  public void swigTakeOwnership() {
    swigCMemOwn = true;
    RTSPModuleJNI.FrameCallback_change_ownership(this, swigCPtr, true);
  }

  public void OnFrame(byte[] pData, long timestamp) {
    if (getClass() == FrameCallback.class) RTSPModuleJNI.FrameCallback_OnFrame(swigCPtr, this, pData, timestamp); else RTSPModuleJNI.FrameCallback_OnFrameSwigExplicitFrameCallback(swigCPtr, this, pData, timestamp);
  }

  public FrameCallback() {
    this(RTSPModuleJNI.new_FrameCallback(), true);
    RTSPModuleJNI.FrameCallback_director_connect(this, swigCPtr, true, true);
  }

}
