package org.example.queingmodel;

public class Model {
    private String resultText;
    private String valueText;

    public Model(String resultText, String valueText) {
        this.resultText = resultText;
        this.valueText = valueText;
    }

    public String getResultText() {
        return resultText;
    }

    public String getValueText() {
        return valueText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    @Override
    public String toString() {
        return "Model{" +
                "resultText='" + resultText + '\'' +
                ", valueText='" + valueText + '\'' +
                '}';
    }
}
