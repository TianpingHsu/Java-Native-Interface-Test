
// class JavaCallback extends FrameCallback
// {
//     public JavaCallback()
//     {
//         super();
//     }

//     public void OnFrame(String data, long len, long timestamp)
//     {
//         System.out.println("JavaCallback.run()");
//     }
// }

public class runme
{
    static {
        try {
            System.loadLibrary("RTSPClientInterface");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load. See the chapter on Dynamic Linking Problems in the SWIG Java documentation for help.\n" + e);
            System.exit(1);
        }
    }

    public static void main(String[] args)
    {
        //System.out.println("Adding and calling a normal C++ callback");
        //System.out.println("----------------------------------------");

        
        RTSPClientInterface              caller = new RTSPClientInterface();
        //Callback            callback = new Callback();

        //caller.setCallback(callback);
        //caller.call();
        //caller.delCallback();
        JavaCallback callback;
        callback = new JavaCallback();

        System.out.println();
        System.out.println("Adding and calling a Java callback");
        System.out.println("------------------------------------");

        caller.SetFrameCallback(callback);
        caller.Init();
        // caller.OnFrame("hello world", 0, 0);
        // caller.delCallback();

        System.out.println();
        System.out.println("java exit");
    }
}
