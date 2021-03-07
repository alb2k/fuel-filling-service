package hackathon.microstream.storage.entities;

import hackathon.microstream.domain.entities.Filling;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class DBFilling extends Filling implements DBEntity {

    public DBFilling() {
    }

    public DBFilling(@NotNull LocalDate fillingDate, @NotNull @NotBlank String fuelType, @DecimalMin(value = "0", inclusive = false) double drivenKm, @DecimalMin(value = "0", inclusive = false) double eurosPerLitre, @DecimalMin(value = "0", inclusive = false) double totalPrice, boolean completeFilling) {
        super(fillingDate, fuelType, drivenKm, eurosPerLitre, totalPrice, completeFilling);
    }

    public DBFilling(@NotNull LocalDate fillingDate, @NotNull @NotBlank String fuelType, @DecimalMin(value = "0", inclusive = false) double drivenKm, @DecimalMin(value = "0", inclusive = false) double eurosPerUnit, @DecimalMin(value = "0", inclusive = false) double totalPrice) {
        super(fillingDate, fuelType, drivenKm, eurosPerUnit, totalPrice);
    }

    private UUID id = UUID.randomUUID();

    @Override
    public UUID getId() {
        return id;
    }



}
