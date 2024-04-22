import java.util.Arrays; 
public class JavaFrameCallback extends FrameCallback{
    public JavaFrameCallback()
    {
        
        super();
        System.out.println("==================");
    }
    public void OnFrame(byte[] pData)
    {
        System.out.println("==================");
        System.out.println("pData legth=" + pData.length);
        // System.out.println(Arrays.toString(Arrays.copyOf(pData, pData.length)));
        for (byte b : pData) {  
            System.out.printf("%02X ", b);  
        } 
        System.out.println();
        System.out.println("==================");
    }
    public void GetFrame(byte[] data)
    {
        System.out.println("~~~~~~~~~~~~~~~");
        System.out.println("buf legth=" + data.length);
        for (byte b : data) {  
            System.out.printf("%02X ", b);  
        } 
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~");
    }
}
