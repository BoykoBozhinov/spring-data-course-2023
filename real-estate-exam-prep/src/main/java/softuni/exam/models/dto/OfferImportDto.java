package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Date;


@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDto {

    @XmlElement
    @Positive
    private BigDecimal price;
    @XmlElement
    private AgentName agent;
    @XmlElement(name = "apartment")
    private ApartmentId apartmentId;
    @XmlElement(name = "publishedOn")
    private String publishedOn;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public AgentName getAgent() {
        return agent;
    }

    public void setAgent(AgentName agent) {
        this.agent = agent;
    }

    public ApartmentId getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(ApartmentId apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }
}
