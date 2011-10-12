LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_CERTIFICATE := platform
LOCAL_SRC_FILES := $(call all-subdir-java-files)
LOCAL_JAVA_LIBRARIES := SkyworthMenu
LOCAL_PACKAGE_NAME := TestMenu

LOCAL_PROGUARD_ENABLED := full

include $(BUILD_PACKAGE)
