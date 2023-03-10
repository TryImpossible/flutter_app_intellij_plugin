package com.barry.flutter_app_intellij_plugin.action;

import com.barry.flutter_app_intellij_plugin.generator.BusinessGeneratorFactory;
import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import org.jetbrains.annotations.NotNull;

public class GenerateBusinessAction extends AnAction {
    private final GenerateBusinessDialog.Listener listener = data -> {
//        System.out.println(data.toString());
        generateBusiness(data);
    };

    private DataContext context;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (e.getProject() == null) {
            return;
        }
        GenerateBusinessDialog dialog = new GenerateBusinessDialog(e.getProject(), listener);
        dialog.show();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        context = e.getDataContext();
        // control action ui visible
        e.getPresentation().setEnabled(e.getProject() != null);
    }

    private void generateBusiness(BusinessDataModel data) {
        final Project project = CommonDataKeys.PROJECT.getData(context);
        final IdeView ideView = LangDataKeys.IDE_VIEW.getData(context);
        final PsiDirectory directory = ideView.getOrChooseDirectory();

        ApplicationManager.getApplication().runWriteAction(() ->
                CommandProcessor.getInstance().executeCommand(project, () ->
                                BusinessGeneratorFactory.getGenerator(project, directory, data).generate(),
                        "Generate a new business template", null));
    }
}

