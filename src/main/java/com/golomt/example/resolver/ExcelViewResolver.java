package com.golomt.example.resolver;

import com.golomt.example.view.ExcelView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

public class ExcelViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String s, Locale locale) {
        ExcelView view = new ExcelView();
        return view;
    }

}
