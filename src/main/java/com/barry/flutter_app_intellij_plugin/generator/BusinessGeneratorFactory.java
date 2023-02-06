package com.barry.flutter_app_intellij_plugin.generator;

import com.barry.flutter_app_intellij_plugin.action.BusinessDataModel;
import com.barry.flutter_app_intellij_plugin.action.StateManagement;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;

public class BusinessGeneratorFactory {
    public static BusinessGenerator getGenerator(Project project, PsiDirectory directory, BusinessDataModel data) {
        try {
            if (data.getStateManagement() == StateManagement.BLOC) {
                return new BlocBusinessGenerator(project, directory, data);
            } else if (data.getStateManagement() == StateManagement.CUBE) {
                return new CubitBusinessGenerator(project, directory, data);
            }
            throw new Exception("unknown business generator");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
