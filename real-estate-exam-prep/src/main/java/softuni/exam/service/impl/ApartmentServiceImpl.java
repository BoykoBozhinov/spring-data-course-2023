package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentImportDto;
import softuni.exam.models.dto.ApartmentImportRootDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static softuni.exam.models.Constants.*;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private static final String APARTMENT_FILE_PATH = "src/main/resources/files/xml/apartments.xml";
    private final ApartmentRepository apartmentRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil, TownRepository townRepository) {
        this.apartmentRepository = apartmentRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENT_FILE_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder apartmentBuilder = new StringBuilder();
        List<ApartmentImportDto> apartmentImportDto = xmlParser.fromFile(APARTMENT_FILE_PATH, ApartmentImportRootDto.class)
                .getApartments();

        for (ApartmentImportDto apartment : apartmentImportDto) {

            apartmentBuilder.append(System.lineSeparator());

            if (apartmentRepository.findAllByAreaAndTown_TownName(apartment.getArea(), apartment.getTown()).isPresent()
            || !validationUtil.isValid(apartment)) {
                apartmentBuilder.append(String.format(INVALID_DATA, APARTMENT));
                continue;
            }

            Apartment apartmentMap = modelMapper.map(apartment, Apartment.class);
            Optional<Town> town = townRepository.findFirstByTownName(apartment.getTown());
            town.ifPresent(apartmentMap::setTown);
            apartmentRepository.save(apartmentMap);

            apartmentBuilder.append(String.format(SUCCESSFUL_IMPORT + APARTMENT_IMPORT,
                    APARTMENT, apartment.getApartmentType(), apartment.getArea().toString()));
        }
        return apartmentBuilder.toString().trim();
    }
}
