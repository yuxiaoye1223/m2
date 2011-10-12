package com.amlogic.android.serialport;


public class SerialPort {
    static {        
		System.loadLibrary("serialport");
    }
        public static final String TAG = "SerialPort";
        public static native String add(String x, int y);
        public static native int startmain();
        public static native int DetectPort();
		
        public static native int ClosePortJ();
        public static native int SerialCommReadJ(String buf,int nbyte );
        public static native int OpenDevJ(String Dev);
        public static native int SerialCommWriteJ(String buf,int nbyte );
        public static native int getcmdtypeJ(int cmdname);
        public static native int ParseReceivedDataJ(String data,int cmdname,int buf);
        public static native char GetSumJ(String buf);
        public static native int UartSendJ(int cmdname,int data);
        public static native Object[] UartGetJ();
        public static native int SendACKJ(int cmdname, int read_buf);
		
        public native String  stringFromJNI();
		public native String  unimplementedStringFromJNI();
	
	
		private static SerialPort instance=null;		
		public static SerialPort createInstance() {
			if (instance == null){
				instance = new SerialPort();
			}			
			return instance;
		}
}

