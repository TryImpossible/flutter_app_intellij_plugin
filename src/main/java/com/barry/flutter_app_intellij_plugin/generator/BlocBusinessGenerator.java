package com.barry.flutter_app_intellij_plugin.generator;

import com.barry.flutter_app_intellij_plugin.action.BusinessCategory;
import com.barry.flutter_app_intellij_plugin.action.BusinessDataModel;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;

import java.util.Arrays;
import java.util.List;

public class BlocBusinessGenerator extends BusinessGenerator {
    public BlocBusinessGenerator(Project project, PsiDirectory directory, BusinessDataModel data) {
        super(project, directory, data);
    }

    protected String provideTemplateFolder() {
        if (data.getBusinessCategory() == BusinessCategory.SCREEN) {
            return "bloc_screen_business";
        }
        if (data.getBusinessCategory() == BusinessCategory.WIDGET) {
            return "bloc_widget_business";
        }
        throw new RuntimeException("unknown business category");
    }

    protected List<String> provideTemplateFilesTree() {
        if (data.getBusinessCategory() == BusinessCategory.SCREEN) {
            return Arrays.asList(
                    "bloc",
                    "bloc/business_bloc.dart.template",
                    "bloc/business_event.dart.template",
                    "bloc/business_state.dart.template",
                    "view",
                    "view/business_screen.dart.template",
                    "view/business_view.dart.template",
                    "business.dart.template"
            );
        }
        if (data.getBusinessCategory() == BusinessCategory.WIDGET) {
            return Arrays.asList(
                    "bloc",
                    "bloc/business_bloc.dart.template",
                    "bloc/business_event.dart.template",
                    "bloc/business_state.dart.template",
                    "view",
                    "view/business_view.dart.template",
                    "view/business_widget.dart.template",
                    "business.dart.template"
            );
        }
        throw new RuntimeException("unknown business category");
    }
}
