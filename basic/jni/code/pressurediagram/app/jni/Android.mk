LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := pressure
LOCAL_SRC_FILES := pressure.c

include $(BUILD_SHARED_LIBRARY)
