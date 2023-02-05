package com.barry.flutter_app_intellij_plugin.action;

import com.barry.flutter_app_intellij_plugin.common.NotificationTools;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GenerateBusinessDialog extends DialogWrapper {

    public interface Listener {
        void onGenerateBusinessClicked(BusinessDataModel data);
    }

    private final Project project;
    private final Listener listener;
    private JPanel contentPane;
    private JTextField businessNameTextField;
    private JRadioButton screenRadioButton;
    private JRadioButton widgetRadioButton;
    private JRadioButton blocRadioButton;
    private JRadioButton cubeRadioButton;

    public GenerateBusinessDialog(Project project, Listener listener) {
        super(project);
        this.project = project;
        this.listener = listener;
        init();
        setTitle("Generate App Business Template");
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return contentPane;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return businessNameTextField;
    }

    @Override
    protected void doOKAction() {
        final String businessName = businessNameTextField.getText();
        if (businessName.isEmpty()) {
            NotificationTools.showError(project, "Business name can't be empty");
            return;
        }

        super.doOKAction();
        BusinessCategory businessCategory = BusinessCategory.SCREEN;
        if (screenRadioButton.isSelected()) {
            businessCategory = BusinessCategory.SCREEN;
        }
        if (widgetRadioButton.isSelected()) {
            businessCategory = BusinessCategory.WIDGET;
        }

        StateManagement stateManagement = StateManagement.BLOC;
        if (blocRadioButton.isSelected()) {
            stateManagement = StateManagement.BLOC;
        }
        if (cubeRadioButton.isSelected()) {
            stateManagement = StateManagement.CUBE;
        }

        listener.onGenerateBusinessClicked(new BusinessDataModel(businessName, businessCategory, stateManagement));
    }
}
