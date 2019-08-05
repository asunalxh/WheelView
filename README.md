# WheelView



## Method

### 1. WheelView setList(List<String> list)

设置显示的内容，并返回当前对象 

### 2.  WheelView setTextSize(int textSize) 

设置字体大小，并返回当前对象

### 3.  WheelView setTextColor(int textColor) 

设置字体颜色，并返回当前对象

### 4.  WheelView setIsRecyclable(boolean b) 

设置是否循环滚动，并返回当前对象

### 5.  WheelView setShowCount(int showCount) 

设置显示个数，必须为奇数，并返回当前对象

### 6. WheelView setSelectBackgroundColor(int color) 

设置选择框背景颜色，设为0不显示，并返回当前对象

### 7. WheelView setSelectLineColor(int color) 

设置选择框分割线颜色，设为0不显示，并返回当前对象

### 8. WheelView setSelectLineHeight(float contentHeight)

设置选择框分割线宽度，并返回当前对象

### 9. WheelView setMinSelect(int minSelect) 

设置最小选择项，并返回当前对象

### 10. WheelView setMaxSelect(int maxSelect) 

设置最大选择项，并返回当前对象

### 11. WheelView setMinSpeed(float minSpeed) 

设置快速滑动判定速度（/10ms），并返回当前对象

### 12.  WheelView setMaxSpeed(float maxSpeed) 

设置快速滑动最高速度（/10ms），并返回当前对象

### 13. WheelView setAcceleration(float acceleration) 

设置快速滑动减速的加速度，并返回当前对象

### 14. String getSelectString() 

获得当前选择的内容



## 如何使用

1. 在根目录的build.grade添加

   ```
   allprojects {
   		repositories {
   			...
   			maven { url 'https://jitpack.io' }
   		}
   	}
   ```

2. 添加依赖

   ```
   dependencies {
   	        implementation 'com.github.asunalxh:wheelview:1.1'
   	}
   ```

   

