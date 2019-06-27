## fragment的静态加载与动态加载
	静态加载: 页面中指定fragment的activity
	动态加载: 通过FragmentManager动态加载fragment的activity

## fragment的生命周期
	onAttach()->onCreate()->onCreateView()->onActivityCreated()->onStart()->onResume()->onPause()->onStop()
	->onDestroyView()->onDestroy()->onDetach()
	必须重写onCreateView进行初始化

## fragment通信
	通过getActivity().getFragmentManager().findFragmentByTag获取目标fragment从而进行通信