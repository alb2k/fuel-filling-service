package hackathon.microstream.service.rest.resource;

import hackathon.microstream.domain.entities.Filling;
import hackathon.microstream.service.provider.FillingService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.UUID;

@Path("/fillings/extinfo")
@RequestScoped
public class FillingInfoResource {

    @Inject
    private FillingService fillingService;


    @Path("/{id}")
    @GET
    @Operation(summary = "Gets a filling (enriched with more information) by id")
    @APIResponses(
            value = {
                    @APIResponse(description = "The filling", responseCode = "200"),
                    @APIResponse(description = "Filling could not be found", responseCode = "404")
            }
    )
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") UUID id) {
        Filling dbFilling = this.fillingService.getById(id);

        return Response
                .status(Response.Status.OK)
                .entity(new FillingExtInfo(dbFilling))
                .build();
    }

    public class FillingExtInfo extends Filling
    {
        private double units;
        private double eurosPer100Km;
        private double unitPer100Km;

        public FillingExtInfo() {
        }

        public FillingExtInfo(Filling filling) {
            this.setCompleteFilling(filling.isCompleteFilling());
            this.setFillingDate(filling.getFillingDate());
            this.setDrivenKm(filling.getDrivenKm());
            this.setFuelType(filling.getFuelType());
            this.setTotalPrice(filling.getTotalPrice());
            this.setEurosPerUnit(filling.getEurosPerUnit());

            this.calc();
        }

        public double getUnits() {
            return units;
        }

        public void setUnits(double units) {
            this.units = units;
        }

        public double getEurosPer100Km() {
            return eurosPer100Km;
        }

        public void setEurosPer100Km(double eurosPer100Km) {
            this.eurosPer100Km = eurosPer100Km;
        }

        public double getUnitPer100Km() {
            return unitPer100Km;
        }

        public void setUnitPer100Km(double unitPer100Km) {
            this.unitPer100Km = unitPer100Km;
        }

        public void calc() {
            this.setUnits(
                    BigDecimal.valueOf(this.getTotalPrice())
                            .divide(BigDecimal.valueOf(this.getEurosPerUnit()), MathContext.DECIMAL128)
                            .doubleValue());

            this.setEurosPer100Km(
                    BigDecimal.valueOf(this.getTotalPrice())
                            .divide(BigDecimal.valueOf(this.getDrivenKm()), MathContext.DECIMAL128)
                            .multiply(BigDecimal.valueOf(100))
                            .doubleValue());

            this.setUnitPer100Km(
                    BigDecimal.valueOf(this.getUnits())
                            .divide(BigDecimal.valueOf(this.getDrivenKm()), MathContext.DECIMAL128)
                            .multiply(BigDecimal.valueOf(100))
                            .doubleValue());
        }
    }
}
