## 内容提供者
	contentprovider
		authorities: com.tuwq.provider
		exportd: true
		URLMatcher
		getContext().getContentResolver().notifyChange(uri, null);
## 内容解析者
	contentresolver
		query
		insert
		update
		delete
## 内容观察者
	contentobserver