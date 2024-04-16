class JavaCallback extends FrameCallback
{
    public JavaCallback()
    {
        super();
    }

    public void OnFrame(String data, long len, long timestamp)
    {
        System.out.println("JavaCallback.run()");
    }
}