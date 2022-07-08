package com.sportynote.server.service;

import com.sportynote.server.domain.Gym;
import com.sportynote.server.repository.GymRepository;
import com.sportynote.server.repository.query.GymDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GymService {

    private final GymRepository gymRepository;

    public void save(GymDto gymDto) {
        Gym gym = new Gym();

        gym.setName(gymDto.getName());
        gym.setLatitude(gymDto.getLatitude());
        gym.setLongitude(gymDto.getLongitude());

        gym.setSi(gymDto.getSi());
        gym.setGu(gymDto.getGu());
        gym.setDong(gymDto.getDong());

        gymRepository.save(gym);
    }
}
