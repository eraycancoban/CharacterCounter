package model;

public class AnalysisRequest {
    private final int maxLimit;
    private final String text;
    private final char targetChar;
    private final boolean caseSensitive;

    public AnalysisRequest(int maxLimit, String text, char targetChar, boolean caseSensitive) {
        this.maxLimit = maxLimit;
        this.text = text;
        this.targetChar = targetChar;
        this.caseSensitive = caseSensitive;
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    public String getText() {
        return text;
    }

    public char getTargetChar() {
        return targetChar;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }
}