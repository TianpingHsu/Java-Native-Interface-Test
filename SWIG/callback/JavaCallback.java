import java.io.*;
import java.util.*;
class JavaCallback extends FrameCallback
{
    public JavaCallback()
    {
        super();
    }

    public void OnFrame(byte[] pData, long timestamp) 
    {
        System.out.println("JavaCallback.run()");
        System.out.println(Arrays.toString(pData));
    }
}
