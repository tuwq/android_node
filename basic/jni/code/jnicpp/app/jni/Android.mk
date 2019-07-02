LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := cpp
LOCAL_SRC_FILES := cpp.cpp

include $(BUILD_SHARED_LIBRARY)
