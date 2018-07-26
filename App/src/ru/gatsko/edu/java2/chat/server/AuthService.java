package ru.gatsko.edu.java2.chat.server;

import java.util.ArrayList;

public final class AuthService{
    private final class Entry {
        private String login;
        private String  pass;
        private String nick;
        public Entry(final String login, final String pass, final String nick) {
            this.login = login;
            this.pass = pass;
            this.nick = nick;
        }
    }
    private ArrayList<Entry> entries;
    public final void start(){}
    public final String getNickByLoginPass(final String login, final String pass) {
        for (final Entry o : entries) {
            final Boolean isLoginAndPasswordFits = o.login.equals(login) && o.pass.equals(pass);
            if (isLoginAndPasswordFits) return o.nick;
        }
        return null;
    }

    public final String getNickIfEmpty(String login) {
        for (final Entry o : entries) {
            final Boolean isNickUsed = o.login.equals(login) || o.nick.equals(login);
            if (isNickUsed) return null;
        }
        return login;
    }

    public final void stop() {}
    public AuthService() {
        entries = new ArrayList<>();
        entries.add(new Entry("login","pass","nick"));
        entries.add(new Entry("login1","pass1","nick1"));
        entries.add(new Entry("login2","pass2","nick2"));
        entries.add(new Entry("login3","pass3","nick3"));
    }

}
