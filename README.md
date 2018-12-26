# CardStackViewpager 卡片翻页效果的Viewpager

这个`CardStackViewpager `的灵感来自Github上面的	[`FlippableStackView`](https://github.com/blipinsk/FlippableStackView)开源项目，而我想实现的效果方向上恰好与`FlippableStackView`相反，并且细节上也有些区别，详见下面的效果对比图：

###### FlippableStackView运行效果图：
![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/two.gif?raw=true)

###### CardStackViewpager运行效果图：
![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/one.gif?raw=true)

这里讲一个小插曲，自己尝试实现`CardStackViewpager`的过程中，由于一开始对`PageTransformer`的`onTransform(View page, float position)`实在很困惑，于是我用自己小学般的英语写了一封邮件给`FlippableStackView`的开发者，尴尬的是，至今他没回我邮件。

回归正题，下面我就来具体讲一下`CardStackViewpager `的实现思路，其实整个核心就在下面这一段代码，把下面这段代码搞懂了，就可以通过自定义自己的`PageTransformer`实现各种各样想要的Viewpager效果了。

#### 核心的VerticalStackTransformer的onTransform方法最终版

```
    @Override
    protected void onTransform(View page, float position) {
        if (position <= 0.0f) {
            page.setAlpha(1.0f);
            Log.e("onTransform", "position <= 0.0f ==>" + position);
            page.setTranslationY(0f);
            //控制停止滑动切换的时候，只有最上面的一张卡片可以点击
            page.setClickable(true);
        } else if (position <= 3.0f) {
            Log.e("onTransform", "position <= 3.0f ==>" + position);
            float scale = (float) (page.getWidth() - ScreenUtils.dp2px(context, spaceBetweenFirAndSecWith * position)) / (float) (page.getWidth());
            //控制下面卡片的可见度
            page.setAlpha(1.0f);
            //控制停止滑动切换的时候，只有最上面的一张卡片可以点击
            page.setClickable(false);
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setTranslationY(-page.getHeight() * position + (page.getHeight() * 0.5f) * (1 - scale) + ScreenUtils.dp2px(context, spaceBetweenFirAndSecHeight) * position);
        }
    }
```

###### 在分析上面的代码之前，我们需要有以下几个知识准备：
1. Viewpager的`setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer)`方法的第一个参数，用来控制加入到Viewpager的Views对象是正序的还是倒序的，这里为了实现我们想要的效果，需要让第一个添加到布局的View来到第一个展示，所以传入`true`；
2. Viewpager的`setOffscreenPageLimit(int limit)`方法，设置有多少的缓存Views，这个将决定我们的卡片重叠展示的效果显示几层卡片效果。

现在我们继续看上面的`onTransform(View page, float position)`方法，这个方法设计的很巧妙，当初我在探索的时候，通过打印日志来判断这个方法是如何执行的时候，发现这这个`position`的值看似毫无规律，后来我想到以前数学里推理定理时的方法，从`特殊情况入手`,再`一点点分析其他情况`，然后一步步的实现上面的代码。

#### 第一步，分析应用初始化进来的时候的position
此时的`onTransform(View page, float position)`方法如下：

```
    @Override
    protected void onTransform(View page, float position) {
        Log.e("onTransform","position  ==>"+position);
        //设置每个卡片y方向偏移量，这样可以使卡片都完全叠加起来
        page.setTranslationY(-page.getHeight() * position);
    }
```

对应日志如下：

![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/three.png?raw=true)

根据这个日志很明显的可以判断得到：由于我现在设置的`setOffscreenPageLimit(int limit)`值为4，所以可以看到position有上面几种情况，显而易见，每个position对应了一张卡片，这个时候界面的效果如图：

![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/five.png?raw=true)

现在猜想2,3,4,5号卡片就在1号卡片下面，现在要想个法子证实我们的猜想，将`onTransform(View page, float position)`方法改成下面这样：

```
    @Override
    protected void onTransform(View page, float position) {
        Log.e("onTransform","position  ==>"+position);
        //设置卡片透明度
        page.setAlpha(0.5f);
        //设置缩放中点
        page.setPivotX(page.getWidth() / 2f);
        page.setPivotY(page.getHeight() / 2f);
        //设置缩放的比例 此处设置两个相邻的卡片的缩放比率为0.9f
        page.setScaleX((float) Math.pow(0.9f,position));
        page.setScaleY((float) Math.pow(0.9f,position));
		//设置每个卡片y方向偏移量，这样可以使卡片都完全叠加起来
		page.setTranslationY(-page.getHeight() * position);
    }
```

运行起来之后，证实了我们的想法：

![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/six.png?raw=true)

#### 第二步，实现卡片叠加的最终效果

分析上面的图片效果，可以发现，把第二张卡片往下移动一段距离之后，就可以形成一个卡片叠加的初步效果了，变成下面这样：

![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/seven.png?raw=true)

其他的卡片，道理一样，那么如何实现这个向下偏移的值呢，这个值如何以一个表达式表现出来呢，先看下面的A,B,C步骤的分析图：

![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/eight.png?raw=true)

显而易见，相隔两张卡片的偏移量为：`(H2-H1)+d1`,我们稍微改变一下`onTransform(View page, float position)`方法如下：

```
@Override
    protected void onTransform(View page, float position) {
        Log.e("onTransform", "position  ==>" + position);
        page.setAlpha(0.5f);
        page.setPivotX(page.getWidth() / 2f);
        page.setPivotY(page.getHeight() / 2f);
        page.setScaleX((float) Math.pow(0.9f, position));
        page.setScaleY((float) Math.pow(0.9f, position));
        //修改过的代码
        page.setTranslationY(-page.getHeight() * position + (page.getHeight() * 0.5f) * (1 - (float) Math.pow(0.9f, position)) + ScreenUtils.dp2px(context, 10));
    }
```

此时的效果图如下：

![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/nine.png?raw=true)

卡片半透明的时候，效果还不是特别的明显，把`page.setAlpha(0.5f)`改为`page.setAlpha(1.0f)`再试一次：

![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/ten.png?raw=true)

惊喜的发现这不就是卡片叠加效果嘛，虽然现在的效果细节还有点问题，我们不急，这个细节问题简单分析一下就会想到，是我们的缩放比例问题导致的，继续下一步的优化，我们将会解决这个问题。

#### 第三步，根据相邻卡片的间距值动态设置缩放值

上面的`onTransform(View page, float position)`方法中，我们的x，y缩放比例都是写的一个固定值`0.9f`,这个显然不能满足日常需求，我这里是设置上下两张卡片的宽度比来作为最终想要的缩放比例，修改`onTransform(View page, float position)`方法如下：

```
    @Override
    protected void onTransform(View page, float position) {
        Log.e("onTransform", "position  ==>" + position);
        float scale = (float) (page.getWidth() - ScreenUtils.dp2px(context, 20 * position)) / (float) (page.getWidth());
        page.setAlpha(1.0f);
        page.setPivotX(page.getWidth() / 2f);
        page.setPivotY(page.getHeight() / 2f);
        page.setScaleX(scale);
        page.setScaleY(scale);
        //修改过的代码
        page.setTranslationY(-page.getHeight() * position + (page.getHeight() * 0.5f) * (1 - scale) + ScreenUtils.dp2px(context, 10) * position);
    }
```

再跑一下程序，完美的卡片效果就出现了：

![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/eleven.png?raw=true)

#### 第四步，特殊到一般，实现最终的卡片滑动效果

此时，我们尝试一下滑动Viewpager，发现卡片的切换效果并没有如期的出现，通过多次尝试和分析，我发现，由于我们这里没有对当前滑动过去的那张卡片做特殊处理，这里的特殊处理指的是：为了实现卡片抽动的切换效果，当前滑动的卡片应该不用执行任何缩放和偏移的操作，修改为`page.setTranslationY(0f);`,具体代码如下：

>这里列出一篇博客：	[http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/0814/1650.html](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/0814/1650.html)，他主要讲了对`onTransform(View page, float position)`中position的理解

```
    @Override
    protected void onTransform(View page, float position) {
        Log.e("onTransform", "position  ==>" + position);
        if (position <= 0.0f) {
            page.setAlpha(1.0f);
            //出现卡片抽动效果的关键代码
            page.setTranslationY(0f);
        } else {
            float scale = (float) (page.getWidth() - ScreenUtils.dp2px(context, 20 * position)) / (float) (page.getWidth());
            page.setAlpha(1.0f);
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX(scale);
            page.setScaleY(scale);
            //修改过的代码
            page.setTranslationY(-page.getHeight() * position + (page.getHeight() * 0.5f) * (1 - scale) + ScreenUtils.dp2px(context, 10) * position);
        }
    }
```

至此，已经可以实现文章开头的动画效果了。回头想一下，我们一直在基于特殊的情况写代码，最后发现其实他就是所有一般情况中的一种，只不过特殊情况由于他的特殊性最容易进行分析总结，更有利于我们编写出易懂的代码。

最后补充下，在实际项目中，在每张卡片上可能还有有点击区域，更可能整张卡片都是一个点击区域，这个时候就会发现一个问题，当处于这种情况的时候：

![enter image description here](https://github.com/NateRobinson/CardStackViewpager/blob/master/img/eleven.png?raw=true)

我不但可以点到卡片1，也会点到卡片2，卡片3。。。这样肯定不行的，所以我们再次回到`onTransform(View page, float position)`方法，在里面加一个控制：

```
   @Override
    protected void onTransform(View page, float position) {
        Log.e("onTransform", "position  ==>" + position);
        if (position <= 0.0f) {
            //最上面的卡片可以点击
            page.setClickable(true);
            .......
        } else {
            //下面的卡片不可点击
            page.setClickable(false);
            ........
        }
    }
```

另外我们可能只需要4张卡片重叠的效果就行，这个时候改变一下判断条件即可：

```
   @Override
    protected void onTransform(View page, float position) {
        Log.e("onTransform", "position  ==>" + position);
        if (position <= 0.0f) {
            ......
        //控制显示几张卡片
        } else if(position <= 3.0f) {
		    ......
        }
    }
```

至此这边文章就要结束了，这是我的总结，希望能帮助大家对`onTransform(View page, float position)`方法有一个更深的理解。
