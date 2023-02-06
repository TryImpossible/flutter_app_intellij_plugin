package com.barry.flutter_app_intellij_plugin.generator;

import com.barry.flutter_app_intellij_plugin.action.BusinessDataModel;
import com.barry.flutter_app_intellij_plugin.common.NotificationTools;
import com.google.common.base.CaseFormat;
import com.google.common.io.CharStreams;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import kotlin.text.Charsets;
import org.apache.commons.lang.text.StrSubstitutor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class BusinessGenerator {
    private static final String TEMPLATE_BUSINESS_PASCAL_CASE = "business_pascal_case";
    private static final String TEMPLATE_BUSINESS_SNAKE_CASE = "business_snake_case";
    private final Map<String, String> templateValues = new HashMap<>();

    final protected Project project;
    final protected PsiDirectory directory;
    final protected BusinessDataModel data;

    public BusinessGenerator(Project project, PsiDirectory directory, BusinessDataModel data) {
        this.project = project;
        this.directory = directory;
        this.data = data;
        templateValues.put(TEMPLATE_BUSINESS_SNAKE_CASE, snakeCase());
        templateValues.put(TEMPLATE_BUSINESS_PASCAL_CASE, pascalCase());
    }

    public void generate() {
        PsiDirectory businessDir = directory.findSubdirectory(snakeCase());
        if (businessDir != null) {
            NotificationTools.showError(project, snakeCase() + " business template already exists");
            return;
        }

        businessDir = createDirectory(directory, snakeCase());
        for (String template : provideTemplateFilesTree()) {
            final Path p = Path.of(template);

            PsiDirectory dir = businessDir;
            for (int i = 0; i < p.getNameCount(); i++) {
                final String name = p.getName(i).toString();
                if (!name.contains(".")) {
                    // it's directory
                    final PsiDirectory subDir = dir.findSubdirectory(name);
                    if (subDir == null) {
                        dir = createDirectory(dir, name);
                    } else {
                        dir = subDir;
                    }
                } else {
                    // it's file
                    createFile(dir, template);
                }
            }
        }
        NotificationTools.showInformation(project, snakeCase() + " business template generate successful");
    }

    private PsiDirectory createDirectory(final PsiDirectory parentDirectory, final String templateDirectory) {
        return parentDirectory.createSubdirectory(templateDirectory);
    }

    private PsiFile createFile(final PsiDirectory parentDirectory, final String templateFile) {
        try {
            final String path = "/templates/" + provideTemplateFolder() + "/" + templateFile;
            final InputStream inputStream = this.getClass().getResourceAsStream(path);
            if (inputStream == null) {
                throw new RuntimeException("get resource as stream exception");
            }
            String templateContents = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
            templateContents = new StrSubstitutor(templateValues, "{{", "}}", '\\').replace(templateContents);

            final String filename = Path.of(templateFile).getFileName().toString()
                    .replace("business", snakeCase())
                    .replace(".template", "");
            final PsiFile psiFile = PsiFileFactory.getInstance(project)
                    .createFileFromText(filename, PlainTextLanguage.INSTANCE, templateContents);
            parentDirectory.add(psiFile);
            return psiFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    abstract protected String provideTemplateFolder();

    abstract protected List<String> provideTemplateFilesTree();

    private String pascalCase() {
        final String businessName = data.getBusinessName();
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, businessName);
    }

    private String snakeCase() {
        final String businessName = data.getBusinessName();
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, businessName);
    }
}
