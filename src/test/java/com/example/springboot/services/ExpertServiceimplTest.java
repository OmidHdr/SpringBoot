package com.example.springboot.services;

import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.repository.ExpertRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ExpertServiceimplTest {

    @Mock
    private ExpertRepository expertRepository;

    @InjectMocks
    private ExpertServiceimpl expertService;

    @Test
    void saveExpert() throws ExpertException {
        Tasks tasks = Tasks.builder().name("house").build();
        SubTasks subTasks = SubTasks.builder().name("cleaning house").task(tasks).description("clean whole house")
                .basePrice(120000L).build();
        Expert expert = Expert.builder().firstName("ali").lastName("alizadeh").email("ali@gmail.com")
                .password("Aa@123456").userRole(UserRole.EXPERT).username("ali@gmail.com")
                .services(tasks).subServices(subTasks).build();
        BDDMockito.given(expertRepository.save(expert)).willReturn(expert);
        Expert result = expertService.saveExpert(expert);
        assertNotNull(result);
    }

    

}