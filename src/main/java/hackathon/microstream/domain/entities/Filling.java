package hackathon.microstream.domain.entities;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Filling {

    /**
     * When the filling took place
     */
    @NotNull
    private LocalDate fillingDate;

    /**
     * Type of fuel, e.g. Petrol E5, E10, Diesel, Plutonium, ...
     */
    @NotNull
    @NotBlank
    private String fuelType;

    /**
     * Driven km
     */
    @DecimalMin(value = "0", inclusive = false)
    private double drivenKm;

    /**
     * Euros per fuel unit (very likely litre)
     */
    @DecimalMin(value = "0", inclusive = false)
    private double eurosPerUnit;

    /**
     * total price for fueling
     */
    @DecimalMin(value = "0", inclusive = false)
    private double totalPrice;

    /**
     * Sometimes you fuel up the car not completely
     */
    private boolean completeFilling = true;

    public Filling() {
    }

    public Filling(@NotNull LocalDate fillingDate, @NotNull @NotBlank String fuelType, @DecimalMin(value = "0", inclusive = false) double drivenKm, @DecimalMin(value = "0", inclusive = false) double eurosPerLitre, @DecimalMin(value = "0", inclusive = false) double totalPrice, boolean completeFilling) {
        this(fillingDate, fuelType, drivenKm, eurosPerLitre, totalPrice);

        this.completeFilling = completeFilling;
    }

    public Filling(@NotNull LocalDate fillingDate, @NotNull @NotBlank String fuelType, @DecimalMin(value = "0", inclusive = false) double drivenKm, @DecimalMin(value = "0", inclusive = false) double eurosPerUnit, @DecimalMin(value = "0", inclusive = false) double totalPrice) {
        this.fillingDate = fillingDate;
        this.fuelType = fuelType;
        this.drivenKm = drivenKm;
        this.eurosPerUnit = eurosPerUnit;
        this.totalPrice = totalPrice;
    }

    public LocalDate getFillingDate() {
        return fillingDate;
    }

    public void setFillingDate(LocalDate fillingDate) {
        this.fillingDate = fillingDate;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getDrivenKm() {
        return drivenKm;
    }

    public void setDrivenKm(double drivenKm) {
        this.drivenKm = drivenKm;
    }

    public double getEurosPerUnit() {
        return eurosPerUnit;
    }

    public void setEurosPerUnit(double eurosPerUnit) {
        this.eurosPerUnit = eurosPerUnit;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isCompleteFilling() {
        return completeFilling;
    }

    public void setCompleteFilling(boolean completeFilling) {
        this.completeFilling = completeFilling;
    }
}
