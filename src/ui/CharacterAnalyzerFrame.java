package ui;

import model.AnalysisRequest;
import model.AnalysisResult;
import service.CharacterAnalysisService;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CharacterAnalyzerFrame extends JFrame {

    private final CharacterAnalysisService analysisService = new CharacterAnalysisService();

    private JTextField limitField;
    private JTextArea sentenceArea;
    private JCheckBox caseSensitiveCheckBox;
    private JTextField targetCharField;
    private JLabel resultLabel;
    private JLabel charCountLabel;

    private JLabel limitLabel;
    private JLabel titleLabel;
    private JLabel charLabel;
    private JButton calculateButton;
    private JToggleButton themeToggleBtn;

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel resultCard;
    private JScrollPane textScrollPane;

    private boolean isDarkMode = false;

    public CharacterAnalyzerFrame() {
        setTitle("Karakter Frekans Analiz Aracı");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(580, 530);
        setMinimumSize(new Dimension(480, 480));
        setLocationRelativeTo(null);

        // Ana Konteyner
        mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBorder(new EmptyBorder(16, 18, 16, 18));
        setContentPane(mainPanel);

        // --- 1. ÜST KISIM (KUZEY): Limit, Tema Butonu ve Sayaç ---
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        // Üst Bar: Sol taraf Limit Kutusu, Sağ taraf Gece/Gündüz Butonu
        JPanel topBar = new JPanel(new BorderLayout(8, 0));
        topBar.setOpaque(false);

        JPanel limitRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        limitRow.setOpaque(false);
        limitLabel = new JLabel("Maksimum Karakter Limiti: ");
        limitLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        limitRow.add(limitLabel);

        limitField = new JTextField("50", 6);
        limitField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        limitRow.add(limitField);
        topBar.add(limitRow, BorderLayout.WEST);

        themeToggleBtn = new JToggleButton("🌙 Gece Modu");
        themeToggleBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        themeToggleBtn.setFocusPainted(false);
        themeToggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        themeToggleBtn.addActionListener(e -> toggleTheme());
        topBar.add(themeToggleBtn, BorderLayout.EAST);

        topPanel.add(topBar);
        topPanel.add(Box.createVerticalStrut(10));

        // Başlık ve Sayaç Satırı
        JPanel textHeader = new JPanel(new BorderLayout());
        textHeader.setOpaque(false);
        titleLabel = new JLabel("Analiz Edilecek Cümle / Metin:");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        textHeader.add(titleLabel, BorderLayout.WEST);

        charCountLabel = new JLabel("Uzunluk: 0");
        charCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textHeader.add(charCountLabel, BorderLayout.EAST);
        topPanel.add(textHeader);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // --- 2. ORTA KISIM (CENTER): Metin Alanı ---
        sentenceArea = new JTextArea(6, 20);
        sentenceArea.setLineWrap(true);
        sentenceArea.setWrapStyleWord(true);
        sentenceArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sentenceArea.setBorder(new EmptyBorder(6, 6, 6, 6));

        sentenceArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateCharCount();
            }
        });

        textScrollPane = new JScrollPane(sentenceArea);
        mainPanel.add(textScrollPane, BorderLayout.CENTER);

        // --- 3. ALT KISIM (GÜNEY): Ayarlar, Buton ve Sonuç ---
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);

        JPanel optionsPanel = new JPanel(new BorderLayout(8, 0));
        optionsPanel.setOpaque(false);

        caseSensitiveCheckBox = new JCheckBox("Büyük / Küçük harf duyarlılığı aktif olsun");
        caseSensitiveCheckBox.setOpaque(false);
        caseSensitiveCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        optionsPanel.add(caseSensitiveCheckBox, BorderLayout.WEST);

        JPanel charPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        charPanel.setOpaque(false);
        charLabel = new JLabel("Aranacak Karakter: ");
        charLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        charPanel.add(charLabel);

        targetCharField = new JTextField(4);
        targetCharField.setFont(new Font("Segoe UI", Font.BOLD, 14));
        targetCharField.setHorizontalAlignment(JTextField.CENTER);
        charPanel.add(targetCharField);
        optionsPanel.add(charPanel, BorderLayout.EAST);

        bottomPanel.add(optionsPanel);
        bottomPanel.add(Box.createVerticalStrut(10));

        // Hesapla Butonu
        calculateButton = new JButton("Analiz Et ve Say");
        calculateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        calculateButton.setFocusPainted(false);
        calculateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        calculateButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        calculateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calculateButton.addActionListener(e -> performAnalysis());
        bottomPanel.add(calculateButton);

        bottomPanel.add(Box.createVerticalStrut(10));

        // Sonuç Kartı
        resultCard = new JPanel(new BorderLayout());
        resultCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        resultLabel = new JLabel("Sonuç: Henüz analiz yapılmadı.", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        resultCard.add(resultLabel, BorderLayout.CENTER);

        bottomPanel.add(resultCard);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Başlangıç temasını uygula (Gündüz modu - tüm yazılar siyah)
        applyTheme(false);
    }

    private void toggleTheme() {
        isDarkMode = !isDarkMode;
        applyTheme(isDarkMode);
    }

    private void applyTheme(boolean dark) {
        Color bg = dark ? new Color(24, 24, 27) : new Color(248, 250, 252);
        Color fg = dark ? Color.WHITE : Color.BLACK;
        Color inputBg = dark ? new Color(39, 39, 42) : Color.WHITE;
        Color inputFg = dark ? Color.WHITE : Color.BLACK;
        Color borderColor = dark ? new Color(63, 63, 70) : new Color(203, 213, 225);

        // Ana panel arka planı
        mainPanel.setBackground(bg);

        // Tüm etiket yazıları
        limitLabel.setForeground(fg);
        titleLabel.setForeground(fg);
        charLabel.setForeground(fg);
        caseSensitiveCheckBox.setForeground(fg);

        // Input kutuları
        JTextField[] fields = {limitField, targetCharField};
        for (JTextField tf : fields) {
            tf.setBackground(inputBg);
            tf.setForeground(inputFg);
            tf.setCaretColor(inputFg);
            tf.setBorder(new LineBorder(borderColor, 1));
        }

        // Metin alanı
        sentenceArea.setBackground(inputBg);
        sentenceArea.setForeground(inputFg);
        sentenceArea.setCaretColor(inputFg);
        textScrollPane.setBorder(new LineBorder(borderColor, 1, true));

        // Tema butonu
        themeToggleBtn.setText(dark ? "☀ Gündüz Modu" : "🌙 Gece Modu");
        themeToggleBtn.setSelected(dark);
        themeToggleBtn.setBackground(dark ? new Color(39, 39, 42) : new Color(241, 245, 249));
        themeToggleBtn.setForeground(Color.BLACK);

        // Analiz butonu
        calculateButton.setBackground(dark ? new Color(30, 64, 175) : new Color(191, 219, 254));
        calculateButton.setForeground(Color.BLACK);

        // Sonuç kartı
        resultCard.setBackground(dark ? new Color(39, 39, 42) : Color.WHITE);
        resultCard.setBorder(new CompoundBorder(
                new LineBorder(borderColor, 1, true),
                new EmptyBorder(10, 14, 10, 14)
        ));

        // Sonuç yazısı rengi (Eğer henüz sonuç üretilmediyse)
        if (resultLabel.getText().equals("Sonuç: Henüz analiz yapılmadı.")) {
            resultLabel.setForeground(dark ? new Color(203, 213, 225) : Color.BLACK);
        }

        updateCharCount();
        repaint();
    }

    private void updateCharCount() {
        int length = sentenceArea.getText().length();
        charCountLabel.setText("Uzunluk: " + length);

        try {
            int max = Integer.parseInt(limitField.getText().trim());
            if (length > max) {
                charCountLabel.setForeground(new Color(239, 68, 68)); // Aşım uyarısı kırmızı
            } else {
                charCountLabel.setForeground(isDarkMode ? new Color(161, 161, 170) : new Color(100, 116, 139));
            }
        } catch (NumberFormatException ignored) {}
    }

    private void performAnalysis() {
        try {
            AnalysisRequest request = analysisService.validateAndBuildRequest(
                    limitField.getText(),
                    sentenceArea.getText(),
                    targetCharField.getText(),
                    caseSensitiveCheckBox.isSelected()
            );

            AnalysisResult result = analysisService.analyze(request);

            resultLabel.setText(result.getFormattedResult());
            resultLabel.setForeground(isDarkMode ? new Color(74, 222, 128) : new Color(22, 101, 52)); // Canlı yeşil
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Uyarı", JOptionPane.WARNING_MESSAGE);
        }
    }
}