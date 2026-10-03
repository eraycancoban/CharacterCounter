package model;

public class AnalysisResult {
    private final char targetChar;
    private final int count;
    private final boolean caseSensitive;

    public AnalysisResult(char targetChar, int count, boolean caseSensitive) {
        this.targetChar = targetChar;
        this.count = count;
        this.caseSensitive = caseSensitive;
    }

    public char getTargetChar() {
        return targetChar;
    }

    public int getCount() {
        return count;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public String getFormattedResult() {
        String modeText = caseSensitive ? "(Büyük/küçük harfe duyarlı)" : "(Büyük/küçük harfe duyarsız)";
        return String.format("Sonuç: '%c' harfi toplamda %d defa geçmektedir. %s", targetChar, count, modeText);
    }
}