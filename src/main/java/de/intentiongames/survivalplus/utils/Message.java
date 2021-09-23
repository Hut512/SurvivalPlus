package de.intentiongames.survivalplus.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Message {

    private static final ResourceBundle.Control CONTROL = new ResourceBundle.Control() {
        @Override
        public long getTimeToLive(String arg0, Locale arg1) {
            return 60000;
        }
    };

    public static TextComponent toComponent(String message) {
        return new TextComponent(TextComponent.fromLegacyText(message));
    }

    public static String parse(String message, Locale locale, boolean prefix, String... params) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("de.intentiongames.messages.ResourceBundle", locale, CONTROL);

        String pattern = resourceBundle.getString(message);

        MessageFormat messageFormat = new MessageFormat(pattern, locale);
        if(prefix) {
            return resourceBundle.getString("prefix") + " " + messageFormat.format(params);
        }else {
            return messageFormat.format(params);
        }
    }

    public static String parse(String message, CommandSender sender, boolean prefix, String... params) {
        Locale locale = null;
        if(sender instanceof Player)
            locale = ((Player) sender).locale();
        if(locale == null)
            locale = Locale.getDefault();

        return parse(message, locale, prefix, params);
    }

    public static void send(String message, @NotNull CommandSender sender, boolean prefixed, String onHover, ClickEvent onClick, String... params) {
        TextComponent msg = toComponent(parse(message, sender, prefixed, params));
        if(onHover != null)
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(onHover)));
        if(onClick != null)
            msg.setClickEvent(onClick);
        sender.sendMessage(msg);
    }

    public static void send(String message, @NotNull CommandSender sender, boolean prefixed, String... params) {
        sender.sendMessage(toComponent(parse(message, sender, prefixed, params)));
    }

    public static void send(String message, @NotNull CommandSender sender, String... params) {
        send(message, sender, false, params);
    }

    private Message() {}
}
