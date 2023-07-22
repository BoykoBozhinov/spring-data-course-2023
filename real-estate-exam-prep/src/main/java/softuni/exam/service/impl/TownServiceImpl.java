package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownImportDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.models.Constants.*;

@Service
public class TownServiceImpl implements TownService {
    private static final String TOWNS_FILE_PATH = "src/main/resources/files/json/towns.json";
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder townsBuilder = new StringBuilder();
        TownImportDto[] townImportDto = gson.fromJson(readTownsFileContent(), TownImportDto[].class);

        for (TownImportDto town : townImportDto) {
            townsBuilder.append(System.lineSeparator());

            if (!validationUtil.isValid(town)) {
                townsBuilder.append(String.format(INVALID_DATA, TOWN));
                continue;
            }
            townRepository.save(modelMapper.map(town, Town.class));
            townsBuilder.append(String.format(SUCCESSFUL_IMPORT + TOWN_IMPORT,
                    TOWN, town.getTownName(), town.getPopulation()));
        }
        return townsBuilder.toString().trim();
    }
}
