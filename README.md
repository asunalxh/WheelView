# WheelView



## Method

### 1. public WheelView setMinSelect(int minSelect) 

设置最小可选择项

### 2. public WheelView setMaxSelect(int maxSelect) 

设置最大可选择项目

### 3. public WheelView setStaticSelect(boolean b)

设置是否静止

### 4. public WheelView setSelectLineColor(int color)

设置选择框分割线颜色

### 5. public WheelView setSelectLineHeight(float height) 

设置选择框分割线宽度

### 6. public String getSelectString() 

获得选中的文字

### 7. public int getSelectIndex()

获得选中索引

### 8. public WheelView setSelectBackgroundColor(int color) 

设置选中框背景颜色

### 9. public WheelView setMinSpeed(float minSpeed)

设置快速滑动最小启动速度

### 10. public WheelView setMaxSpeed(float maxSpeed)

设置快速滑动最大速度

### 11. public WheelView setAcceleration(float acceleration) 

设置快速滑动减速的加速度

### 12. public WheelView setShowCount(int showCount) 

设置显示项数，必须为奇数

### 13. public WheelView setList(List<String> list)

设置选择项目

### 14. public WheelView setList(String[] list)

设置选择项目

### 15. public WheelView setTextSize(int textSize) 

设置文字大小

### 16. public WheelView setTextColor(int textColor) 

设置文字颜色

### 17. public WheelView setIsRelcyclable(boolean b) 

是否循环滑动

### 18. public WheelView setItemHeight(int height)

设置选项宽度，设置后整个控件高度根据选项宽度和显示数目决定

### 19. public WheelView setSelect(int selectIndex)

设置选中

### 20. public void setOnSelectChangeListener(onSelectChangeListener listener) 

设置当选择改变时的监听器


## How To Use

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

   

