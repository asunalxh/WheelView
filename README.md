# WheelView

## Method

| 函数                            | 作用           |
| ------------------------------- | -------------- |
| WheelView setList(List<String> list) | 设置显示的内容，并返回当前对象 |
| WheelView setTextSize(int textSize) |设置字体大小，并返回当前对象|
| WheelView setTextColor(int textColor) |设置字体颜色，并返回当前对象|
| WheelView setIsRecyclable(boolean b) |设置是否循环滚动，，并返回当前对象|
| WheelView setShowCount(int showCount) |设置显示个数，必须为奇数，并返回当前对象|
| WheelView setSelectBackgroundColor(int color) |设置选择框背景颜色，设为0不显示，并返回当前对象|
| WheelView setSelectLineColor(int color) |设置选择框分割线颜色，设为0不现实，并返回当前对象|
| WheelView setSelectLineHeight(float height) |设置选择框分割线宽度，并返回当前对象|
| WheelView setMinSelect(int minSelect) |设置最小选择项，并返回当前对象|
| WheelView setMaxSelect(int maxSelect) |设置最大选择项，并返回当前对象|
| WheelView setMinSpeed(float minSpeed) |设置快速滑动判定速度（/10ms），并返回当前对象|
| WheelView setMaxSpeed(float maxSpeed) |设置快速滑动最高速度（/10ms），并返回当前对象|
| WheelView setAcceleration(float acceleration) |设置快速滑动减速的加速度，并返回当前对象|
| String getSelectString() |获得当前选择的内容|
