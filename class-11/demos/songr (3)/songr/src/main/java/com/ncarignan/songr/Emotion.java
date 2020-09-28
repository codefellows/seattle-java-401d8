package com.ncarignan.songr;

public class Emotion {
    // public private and protected
    // public anyone
    // private this class
    // protected this package (com.ncarignan.songr) (folder)
    public String feeling;
    boolean causesUsToLaugh;
    String personHavingIt;
    int level;
    String reason;

    public Emotion(String feeling, boolean causesUsToLaugh, String personHavingIt, int level, String reason) {
        this.feeling = feeling;
        this.causesUsToLaugh = causesUsToLaugh;
        this.personHavingIt = personHavingIt;
        this.level = level;
        this.reason = reason;
    }

    public boolean isCausesUsToLaugh() {
        return causesUsToLaugh;
    }

    public String getPersonHavingIt() {
        return personHavingIt;
    }

    public int getLevel() {
        return level;
    }

    public String getReason() {
        return reason;
    }
}
