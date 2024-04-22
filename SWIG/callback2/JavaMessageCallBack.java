class JavaMessageCallBack extends MessageCallback
{
    public JavaMessageCallBack()
    {
        super();
    }

    public void OnMessage(String message)
    {
        System.out.println("java OnMessage");
        System.out.println(message);
    }
}