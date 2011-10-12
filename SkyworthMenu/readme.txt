增加：先运行SkyworthMenuRescoure的apk。
1.首先导出src，生成一个jar。
2.在用的时候实现MenuCallbackListener这个抽象类，里面的方法是
  public void CallbackMenuState(String... State)
    {	}
   每次按L键（默认为确定键）会回调这个函数。
3.增加view
例如
	enucontrol Mcontrol = new Menucontrol(this,null);
	Mcontrol.setLayoutParams(new  LinearLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	Mcontrol.setFocusable(true);
	Mcontrol.setMenuCallbackListener(this);