## listView
### listView中convertView
	可以重用convertView,如果convertView不为空,那么可以重复使用convertView

### listView宽度和高度
	listView不要使用wrap_content包裹内容,高度使用包裹内容可能会导致getView方法调用多次,宽度使用包裹内容可能对布局产生影响


### adapter
	arrayadapter
		每个条目中需要修改的数据十分简单,只有一个textView的内容需要修改,这时可以使用arrayadapter
	simpleadapter
		封装后的adapter,可完成一般性的功能