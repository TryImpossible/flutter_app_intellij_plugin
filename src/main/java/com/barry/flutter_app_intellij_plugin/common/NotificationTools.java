package com.barry.flutter_app_intellij_plugin.common;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

public class NotificationTools {
    private static void show(Project project, String content, NotificationType type) {
        NotificationGroupManager.getInstance().getNotificationGroup("GenerateBusinessTemplateNotificationId")
                .createNotification(content, type)
                .notify(project);
    }

    public static void showInformation(Project project, String content) {
        show(project, content, NotificationType.INFORMATION);
    }

    public static void showWarning(Project project, String content) {
        show(project, content, NotificationType.WARNING);
    }

    public static void showError(Project project, String content) {
        show(project, content, NotificationType.ERROR);
    }
}
