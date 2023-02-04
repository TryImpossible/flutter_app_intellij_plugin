package com.barry.flutter_app_intellij_plugin.action;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GenerateBusinessDialog extends DialogWrapper {

    public interface Listener {
        void onGenerateBusinessClicked(String businessName, BusinessCategory businessCategory, BlocStyle blocStyle);
    }

    private final Listener listener;

    private JPanel contentPane;
    private JTextField businessNameTextField;
    private JRadioButton screenRadioButton;
    private JRadioButton widgetRadioButton;
    private JRadioButton blocRadioButton;
    private JRadioButton cubeRadioButton;

    public GenerateBusinessDialog(Project project, Listener listener) {
        super(project);
        init();
        setTitle("Generate App Business Template");
        this.listener = listener;
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return contentPane;
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
        final String businessName = businessNameTextField.getText();

        BusinessCategory businessCategory = BusinessCategory.SCREEN;
        if (screenRadioButton.isSelected()) {
            businessCategory = BusinessCategory.SCREEN;
        }
        if (widgetRadioButton.isSelected()) {
            businessCategory = BusinessCategory.WIDGET;
        }

        BlocStyle blocStyle = BlocStyle.BLOC;
        if (blocRadioButton.isSelected()) {
            blocStyle = BlocStyle.BLOC;
        }
        if (cubeRadioButton.isSelected()) {
            blocStyle = BlocStyle.CUBE;
        }

        listener.onGenerateBusinessClicked(businessName, businessCategory, blocStyle);
    }
}
