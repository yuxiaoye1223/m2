���ӣ�������SkyworthMenuRescoure��apk��
1.���ȵ���src������һ��jar��
2.���õ�ʱ��ʵ��MenuCallbackListener��������࣬����ķ�����
  public void CallbackMenuState(String... State)
    {	}
   ÿ�ΰ�L����Ĭ��Ϊȷ��������ص����������
3.����view
����
	enucontrol Mcontrol = new Menucontrol(this,null);
	Mcontrol.setLayoutParams(new  LinearLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	Mcontrol.setFocusable(true);
	Mcontrol.setMenuCallbackListener(this);