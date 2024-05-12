package com.example.gamezoneproject.mail;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class ThymeleafServiceImpl implements ThymeleafService {
    private final TemplateEngine templateEngine;

    {
        templateEngine = emailTemplateEngine();
    }

    /**
     * Method builds email message source.
     * @return ResourceBundleMessage source object with base name.
     */
    private ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("mail/MailMessages");
        return messageSource;
    }

    /**
     * Method sets template engine and set resolver and message source for it.
     * @return Template Engine with params.
     */
    private TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(htmlTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    /**
     * Method builds htmlTemplateResolver.
     * @return ITemplate resolver with set params.
     */
    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    /**
     * Method set's email template and variables.
     * @param template name of html/thymeleaf email template.
     * @param variables variables to be read by thymeleaf.
     * @return String email text.
     */
    public String createContent(String template, Map<String, Object> variables) {
        final Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(template, context);
    }
}
