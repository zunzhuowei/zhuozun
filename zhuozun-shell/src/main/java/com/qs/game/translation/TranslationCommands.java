package com.qs.game.translation;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Locale;

/**
 * Created by zun.wei on 2019/6/2.
 */
@ShellComponent
public class TranslationCommands {


    /*
    shell:>translate "hello world!" --from en_US --to fr_FR
    hello world!:en_US:fr_FR
    shell:>
     */

    @ShellMethod("Translate text from one language to another.")
    public String translate(
            @ShellOption String text,
            @ShellOption( defaultValue = "en_US") Locale from,
            @ShellOption() String to) {

        // invoke service
        //return service.translate(text, from, to);

        return text + ":" + from + ":" + to;
    }

}
