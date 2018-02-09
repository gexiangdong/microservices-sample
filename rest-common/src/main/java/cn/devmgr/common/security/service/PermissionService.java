package cn.devmgr.common.security.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.security.RolesAllowed;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Service
@RequestMapping("/internal/")
public class PermissionService {
    private static final Log log = LogFactory.getLog(PermissionService.class);

    @Autowired
    private ApplicationContext appContext;

    private Set<String> permissions;

    private Pattern[] patterns = null;

    @RequestMapping(value = "/permissions/", method = RequestMethod.GET)
    public Collection<String> getInfos() {
        synchronized (this) {
            if (permissions == null) {
                initPermissions();
            }
        }
        return permissions;
    }

    private void initPermissions() {
        if (permissions != null) {
            log.warn("WARN call to initPermissions(), permissions is not null.");
            return;
        }
        permissions = new HashSet<String>();

        Class<?>[] serviceClasses = new Class<?>[] { Service.class, RestController.class, Controller.class,
                Repository.class, Component.class };
        for (Class cls : serviceClasses) {
            String[] ary = appContext.getBeanNamesForAnnotation(cls);
            for (int i = 0; i < ary.length; i++) {
                Object bean = appContext.getBean(ary[i]);
                Annotation[] annos = bean.getClass().getAnnotations();
                addPrivilegeFromAnnotations(annos);

                Method[] methods = bean.getClass().getMethods();
                for (Method m : methods) {
                    Annotation[] mas = m.getAnnotations();
                    addPrivilegeFromAnnotations(mas);
                }
            }
        }
    }

    private void addPrivilegeFromAnnotations(Annotation[] annotations) {
        for (Annotation a : annotations) {
            addPrivilegeFromAnnotation(a);
        }
    }

    private void addPrivilegeFromAnnotation(Annotation annotation) {
        if (annotation instanceof RolesAllowed) {
            RolesAllowed rolesAnnotation = (RolesAllowed) annotation;
            permissions.addAll(Arrays.asList(rolesAnnotation.value()));
        } else if (annotation instanceof Secured) {
            Secured secured = (Secured) annotation;
            permissions.addAll(Arrays.asList(secured.value()));
        } else if (annotation instanceof PreAuthorize) {
            addPrivilegesFromSpringEL(((PreAuthorize) annotation).value());
        } else if (annotation instanceof PostAuthorize) {
            addPrivilegesFromSpringEL(((PostAuthorize) annotation).value());
        }
    }

    private void addPrivilegesFromSpringEL(String el) {
        if (patterns == null) {
            patterns = new Pattern[4];
            patterns[0] = Pattern.compile("hasRole\\((.*)\\)");
            patterns[1] = Pattern.compile("hasAnyRole\\((.*)\\)");
            patterns[2] = Pattern.compile("hasAuthority\\((.*)\\)");
            patterns[3] = Pattern.compile("hasAnyAuthority\\((.*)\\)");
        }

        for (Pattern p : patterns) {
            Matcher matcher = p.matcher(el);
            if (matcher.find()) {
                String roles = matcher.group(1);
                String[] ary = roles.split("\\s*,\\s*");
                for (String s : ary) {
                    String s2 = s.substring(1, s.length() - 2);
                    if (s2.startsWith("ROLE_")) {
                        s2 = s2.substring(5);
                    }
                    permissions.add(s2);
                }
            }
        }
    }

}
