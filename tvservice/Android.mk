LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-subdir-java-files) \
	src/com/amlogic/tvjni/Itvservice.aidl
LOCAL_PACKAGE_NAME := tvservice
LOCAL_CERTIFICATE := platform

PRODUCT_COPY_FILES += \
  $(LOCAL_PATH)/lib/libdreampanel.so:system/lib/libdreampanel.so
include $(BUILD_PACKAGE)
