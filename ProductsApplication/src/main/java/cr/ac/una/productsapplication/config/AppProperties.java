package cr.ac.una.productsapplication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String name;
    private String defaultCurrency;
    private double taxRate;

    public String getName() {
        return name;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }
}
