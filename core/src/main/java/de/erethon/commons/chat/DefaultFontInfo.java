/*
 * Written from 2015-2019 by Daniel Saukel
 *
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software
 * to the public domain worldwide.
 *
 * This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software. If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.erethon.commons.chat;

import java.util.ArrayList;
import java.util.Arrays;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

/**
 * @author SirSpoodles, Daniel Saukel
 */
public enum DefaultFontInfo {

    A('A', 5),
    a('a', 5),
    B('B', 5),
    b('b', 5),
    C('C', 5),
    c('c', 5),
    D('D', 5),
    d('d', 5),
    E('E', 5),
    e('e', 5),
    F('F', 5),
    f('f', 4),
    G('G', 5),
    g('g', 5),
    H('H', 5),
    h('h', 5),
    I('I', 3),
    i('i', 1),
    J('J', 5),
    j('j', 5),
    K('K', 5),
    k('k', 4),
    L('L', 5),
    l('l', 1),
    M('M', 5),
    m('m', 5),
    N('N', 5),
    n('n', 5),
    O('O', 5),
    o('o', 5),
    P('P', 5),
    p('p', 5),
    Q('Q', 5),
    q('q', 5),
    R('R', 5),
    r('r', 5),
    S('S', 5),
    s('s', 5),
    T('T', 5),
    t('t', 4),
    U('U', 5),
    u('u', 5),
    V('V', 5),
    v('v', 5),
    W('W', 5),
    w('w', 5),
    X('X', 5),
    x('x', 5),
    Y('Y', 5),
    y('y', 5),
    Z('Z', 5),
    z('z', 5),
    NUM_1('1', 5),
    NUM_2('2', 5),
    NUM_3('3', 5),
    NUM_4('4', 5),
    NUM_5('5', 5),
    NUM_6('6', 5),
    NUM_7('7', 5),
    NUM_8('8', 5),
    NUM_9('9', 5),
    NUM_0('0', 5),
    EXCLAMATION_POINT('!', 1),
    AT_SYMBOL('@', 6),
    NUM_SIGN('#', 5),
    DOLLAR_SIGN('$', 5),
    PERCENT('%', 5),
    UP_ARROW('^', 5),
    AMPERSAND('&', 5),
    ASTERISK('*', 5),
    LEFT_PARENTHESIS('(', 4),
    RIGHT_PERENTHESIS(')', 4),
    MINUS('-', 5),
    UNDERSCORE('_', 5),
    PLUS_SIGN('+', 5),
    EQUALS_SIGN('=', 5),
    LEFT_CURL_BRACE('{', 4),
    RIGHT_CURL_BRACE('}', 4),
    LEFT_BRACKET('[', 3),
    RIGHT_BRACKET(']', 3),
    COLON(':', 1),
    SEMI_COLON(';', 1),
    DOUBLE_QUOTE('"', 3),
    SINGLE_QUOTE('\'', 1),
    LEFT_ARROW('<', 4),
    RIGHT_ARROW('>', 4),
    QUESTION_MARK('?', 5),
    SLASH('/', 5),
    BACK_SLASH('\\', 5),
    LINE('|', 1),
    TILDE('~', 5),
    TICK('`', 2),
    PERIOD('.', 1),
    COMMA(',', 1),
    SPACE(' ', 3),
    DEFAULT('a', 4);

    private char character;
    private int length;

    DefaultFontInfo(char character, int length) {
        this.character = character;
        this.length = length;
    }

    /**
     * @return the char
     */
    public char getCharacter() {
        return character;
    }

    /**
     * @return the amount of pixels
     */
    public int getLength() {
        return length;
    }

    /**
     * @return the amount of pixels if the symbol is printed out boldly
     */
    public int getBoldLength() {
        if (this == DefaultFontInfo.SPACE) {
            return getLength();
        }
        return length + 1;
    }

    /* Statics */
    private final static int CENTER_PX = 154;

    /**
     * @param c the char
     * @return the pixel length of the char
     */
    public static DefaultFontInfo getDefaultFontInfo(char c) {
        for (DefaultFontInfo dFI : DefaultFontInfo.values()) {
            if (dFI.getCharacter() == c) {
                return dFI;
            }
        }
        return DefaultFontInfo.DEFAULT;
    }

    /**
     * Method to add spaces to a String to show up in the middle of the chat
     */
    public static String center(String message) {
        return getCenterSpaces(message) + ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Method to add spaces to BaseComponents to show up in the middle of the chat
     */
    public static BaseComponent[] center(BaseComponent... message) {
        String legacy = new String();
        for (BaseComponent component : message) {
            legacy += component.toLegacyText();
        }
        TextComponent spaces = new TextComponent(getCenterSpaces(legacy));
        ArrayList<BaseComponent> list = new ArrayList<>(Arrays.asList(message));
        list.add(0, spaces);
        return list.toArray(new BaseComponent[]{});
    }

    public static String getCenterSpaces(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }

        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '\u00A7') {
                previousCode = true;

            } else if (previousCode == true) {
                previousCode = false;
                if (c == 'l' || c == 'L') {
                    isBold = true;

                } else {
                    isBold = false;
                }

            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();

        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }

        return sb.toString();
    }

}
