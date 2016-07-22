# CardStackViewpager
通过继承`Viewpager`的`PageTransformer`类，重写里面的`onTransform(View page, float position)`方法，可以实现各种各样的Viewpager变化效果。

这个`CardStackViewpager `的灵感来自Github上面的	[`FlippableStackView`](https://github.com/blipinsk/FlippableStackView)开源项目，而我想实现的效果方向上恰好与`FlippableStackView`相反，并且细节上也有些区别，详见下面的效果对比图：

######FlippableStackView运行效果图：
![ enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/two.gif?raw=true)

######CardStackViewpager运行效果图：
![ enter image description here ](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/one.gif?raw=true)

