package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferImportDto;
import softuni.exam.models.dto.OfferImportRootDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static softuni.exam.models.Constants.*;

@Service
public class OfferServiceImpl implements OfferService {

    private static final String OFFERS_FILE_PATH = "src/main/resources/files/xml/offers.xml";
    private static final String AGENT = "Agent %s %s with offer №%d%n";
    private static final String APARTMENT_AREA = "-Apartment area: %s%n";
    private static final String TOWN = "--Town: %s%n";
    private static final String PRICE = "---Price: %s$%n";
    private final OfferRepository offerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;

    public OfferServiceImpl(OfferRepository offerRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil, AgentRepository agentRepository, ApartmentRepository apartmentRepository) {
        this.offerRepository = offerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder offerBuilder = new StringBuilder();
        List<OfferImportDto> offerImportDto = xmlParser.fromFile(OFFERS_FILE_PATH, OfferImportRootDto.class)
                .getOffers();

        for (OfferImportDto offer : offerImportDto) {

            offerBuilder.append(System.lineSeparator());

            if (agentRepository.findFirstByFirstName(offer.getAgent().getName()).isEmpty()
            || !validationUtil.isValid(offer)) {
                offerBuilder.append(String.format(INVALID_DATA, OFFER));
                continue;
            }
            Offer offerMap = modelMapper.map(offer, Offer.class);
            Optional<Apartment> apartment = apartmentRepository.findById(offer.getApartmentId().getId());
            Optional<Agent> agent = agentRepository.findFirstByFirstName(offer.getAgent().getName());
            offerMap.setApartment(apartment.get());
            offerMap.setAgent(agent.get());
            offerRepository.save(offerMap);

            offerBuilder.append(String.format(SUCCESSFUL_IMPORT + OFFER_IMPORT,
                    OFFER, offer.getPrice().toString()));
        }
        return offerBuilder.toString().trim();
    }

    @Override
    public String exportOffers() {
        StringBuilder exportOfferBuilder = new StringBuilder();

        List<Offer> offerByApartmentType = offerRepository.findOfferByApartmentType(ApartmentType.three_rooms);

        for (Offer offer : offerByApartmentType) {
            exportOfferBuilder.append(String.format(AGENT + APARTMENT_AREA + TOWN + PRICE,
                    offer.getAgent().getFirstName(), offer.getAgent().getLastName(), offer.getId(),
                    offer.getApartment().getArea().toString(),
                    offer.getApartment().getTown().getTownName(),
                    offer.getPrice().toString()));
        }
        return exportOfferBuilder.toString().trim();
    }
}
