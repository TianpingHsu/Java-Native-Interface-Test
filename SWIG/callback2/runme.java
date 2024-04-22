
public class runme
{
    static {
        try {
            //加载.so库
            System.loadLibrary("RTSPClientInterface");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load. See the chapter on Dynamic Linking Problems in the SWIG Java documentation for help.\n" + e);
            System.exit(1);
        }
    }

    public static void main(String[] args)
    {

        RTSPClientInterface  caller = new RTSPClientInterface();

        // JavaMessageCallBack message_callback = new JavaMessageCallBack();
        MessageCallback message_callback = new JavaMessageCallBack();
        FrameCallback frame_callback = new JavaFrameCallback();

        System.out.println();
        System.out.println("Adding and calling a Java callback");
        System.out.println("------------------------------------");

        //注册回调
        caller.SetMessageCallback(message_callback);
        caller.SetFrameCallback(frame_callback);

        //调用messageCallback;
        caller.Init();

        //调用c++底层的SendFrameData向java层发送回调信息
        caller.SendFrameData();

        caller.SendFrameVoidData();
        
        System.out.println("------------------------------------");
        StringVector stringVector = caller.GetClientList();
        System.out.println("------------------------------------");
        for(String vec:stringVector)
        {
            System.out.println(vec);
        }

        
        
        System.out.println();
        System.out.println("java exit");
    }
}
