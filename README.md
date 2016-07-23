#CardStackViewpager 卡片翻页效果的Viewpager

通过继承`Viewpager`的`PageTransformer`类，重写里面的`onTransform(View page, float position)`方法，可以实现各种各样的Viewpager变化效果。

这个`CardStackViewpager `的灵感来自Github上面的	[`FlippableStackView`](https://github.com/blipinsk/FlippableStackView)开源项目，而我想实现的效果方向上恰好与`FlippableStackView`相反，并且细节上也有些区别，详见下面的效果对比图：

######FlippableStackView运行效果图：
![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/one.gif?raw=true)

######CardStackViewpager运行效果图：
![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/two.gif?raw=true)

这里讲一个小插曲，自己尝试实现`CardStackViewpager`的过程中，由于一开始对`PageTransformer`的`onTransform(View page, float position)`实在很困惑，于是我用自己小学般的英语写了一封邮件给`FlippableStackView`的开发者，尴尬的是，至今他没回我邮件。

插曲讲完，下面我就来具体讲一下`CardStackViewpager `的实现思路，其实整个核心就在下面这一段代码，把下面这段代码搞懂了，就可以通过自定义自己的`PageTransformer`实现各种各样想要的Viewpager效果了。

####核心的VerticalStackTransformer的onTransform方法最终版

```
    @Override
    protected void onTransform(View page, float position) {
        if (position <= -1f) {
            Log.e("onTransform","position <= -1f ==>"+position);
            page.setTranslationY(-page.getHeight());
        } else if (position <= 0.0f) {
            Log.e("onTransform","position <= 0.0f ==>"+position);
            page.setTranslationY(0f);
            //控制停止滑动切换的时候，只有最上面的一张卡片可以点击
            page.setClickable(true);
        } else if (position <= 3.0f) {
            Log.e("onTransform","position <= 3.0f ==>"+position);
            float scale1 = (float) (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, spaceBetweenFirAndSecWith * (1 + position))) / (float) (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, spaceBetweenFirAndSecWith));
            //控制下面卡片的可见度
            page.setAlpha(1.0f);
            //控制停止滑动切换的时候，只有最上面的一张卡片可以点击
            page.setClickable(false);
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX(scale1);
            page.setScaleY(scale1);
            float shiftY1 = (page.getHeight() * 0.5f) * (1 - scale1) + ScreenUtils.dp2px(context, spaceBetweenFirAndSecHeight) * position;
            page.setTranslationY(-page.getHeight() * position + shiftY1);
        }
    }
```

######在分析上面的代码之前，我们需要有以下几个知识准备：
1. Viewpager的`setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer)`方法的第一个参数，用来控制加入到Viewpager的Views对象是正序的还是倒序的，这里为了实现我们想要的效果，需要让第一个添加到布局的View来到第一个展示，所以传入`true`；
2. Viewpager的`setOffscreenPageLimit(int limit)`方法，设置有多少的缓存Views，这个将决定我们的卡片重叠展示的效果显示几层卡片效果。

现在我们继续看上面的`onTransform(View page, float position)`方法，这个方法设计的很巧妙，当初我在探索的时候，通过打印日志来判断这个方法是如何执行的时候，发现这这个`position`的值看似毫无规律，后来我想到以前数学里推理定理时的方法，从`特殊情况入手`,再`一点点分析其他情况`，然后一步步的实现上面的代码。

特殊情况就是应用初始化进来的时候，这个时候的效果图如下：

![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/four.png?raw=true)


####第一步，分析position
此时的`onTransform(View page, float position)`方法如下：

```
    @Override
    protected void onTransform(View page, float position) {
        Log.e("onTransform","position  ==>"+position);
    }
```

对应日志如下：

![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/three.png?raw=true)

根据这个日志很明显的可以判断得到：