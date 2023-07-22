package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentImportDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static softuni.exam.models.Constants.*;

@Service
public class AgentServiceImpl implements AgentService {

    private static final String AGENT_FILE_PATH = "src/main/resources/files/json/agents.json";
    private final AgentRepository agentRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;

    public AgentServiceImpl(AgentRepository agentRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, TownRepository townRepository) {
        this.agentRepository = agentRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(AGENT_FILE_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder agentBuilder = new StringBuilder();
        AgentImportDto[] agentImportDto = gson.fromJson(readAgentsFromFile(), AgentImportDto[].class);

        for (AgentImportDto agent: agentImportDto) {

            agentBuilder.append(System.lineSeparator());

            if (agentRepository.findFirstByFirstName(agent.getFirstName()).isPresent() ||
                    !validationUtil.isValid(agent)) {
                agentBuilder.append(String.format(INVALID_DATA, AGENT));
                continue;
            }
            Agent agentMap = modelMapper.map(agent, Agent.class);
            Optional<Town> town = townRepository.findFirstByTownName(agent.getTown());
            agentMap.setTown(town.get());

            agentRepository.save(agentMap);

            agentBuilder.append(String.format(SUCCESSFUL_IMPORT + AGENT_IMPORT,
                    TOWN, agent.getFirstName(), agent.getLastName()));
        }
        return agentBuilder.toString().trim();
    }
}
