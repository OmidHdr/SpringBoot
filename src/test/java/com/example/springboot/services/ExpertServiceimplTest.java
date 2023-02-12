package com.example.springboot.services;

import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.repository.ExpertRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ExpertServiceimplTest {

    @Mock
    private ExpertRepository expertRepository;

    @InjectMocks
    private ExpertServiceimpl expertService;

    //section register
    @Test
    @Order(1)
    void saveExpert() throws ExpertException {
        Tasks tasks = Tasks.builder().name("house").build();
        SubTasks subTasks = SubTasks.builder().name("cleaning house").task(tasks).description("clean whole house")
                .basePrice(120000L).build();
        Expert expert = Expert.builder().firstName("ali").lastName("alizadeh").email("ali@gmail.com")
                .password("Aa@123456").userRole(UserRole.EXPERT).username("ali@gmail.com")
                .tasks(tasks).subTasks(subTasks).build();
        BDDMockito.given(expertRepository.save(expert)).willReturn(expert);
        Expert result = expertService.saveExpert(expert);
        assertNotNull(result);
    }

    //section confirm Expert
    @Test
    void confirmExpert() {
        Expert expert = Expert.builder().firstName("ali").lastName("alizadeh").email("ali@gmail.com")
                .password("Aa@123456").userRole(UserRole.EXPERT).username("ali@gmail.com").status(false)
                .build();
        BDDMockito.given(expertRepository.save(expert)).willReturn(expert);
        final Expert result = expertService.confirmExpert(expert);
        assertThat(result.getStatus()).isEqualTo(true);
    }

    //section find by user pass
    @Test
    void findByUsernameAndPassword() throws ExpertException {
        Tasks tasks = Tasks.builder().name("house").build();
        SubTasks subTasks = SubTasks.builder().name("cleaning house").task(tasks).description("clean whole house")
                .basePrice(120000L).build();
        Expert expert = Expert.builder().firstName("ali").lastName("alizadeh").email("ali@gmail.com")
                .password("Aa@123456").userRole(UserRole.EXPERT).username("ali@gmail.com")
                .tasks(tasks).subTasks(subTasks).build();

        BDDMockito.given(expertRepository.findByUsernameAndPassword(expert.getUsername(),expert.getPassword())).willReturn(expert);
        Expert result = expertService.findByUsernameAndPassword(expert.getUsername(),expert.getPassword());
        assertThat(result).isNotNull();
    }

    @Test
    void changePassword() {
        Expert expert = Expert.builder().username("ali@gmail.com").password("Aa@123456").build();
        BDDMockito.given(expertRepository.save(expert)).willReturn(expert);
        final Expert newExpert = expertService.changePassword(expert, "Aa123456@");
        assertThat(newExpert.getPassword()).isEqualTo("Aa123456@");
    }

    @Test
    void removeSubServiceFromExpert() {
        Tasks tasks = Tasks.builder().name("house").build();
        SubTasks subTasks = SubTasks.builder().name("cleaning house").task(tasks).description("clean whole house")
                .basePrice(120000L).build();
        Expert expert = Expert.builder().firstName("ali").lastName("alizadeh").email("ali@gmail.com")
                .password("Aa@123456").userRole(UserRole.EXPERT).username("ali@gmail.com")
                .tasks(tasks).subTasks(subTasks).build();
        BDDMockito.given(expertRepository.save(expert)).willReturn(expert);
//        final Expert result = expertService.removeSubServiceFromExpert(expert, subTasks);
//        assertThat(result).isNotNull();
    }

    @Test
    void requestForNewJob() {
        Tasks tasks = Tasks.builder().name("house").build();
        SubTasks subTasks = SubTasks.builder().name("cleaning house").task(tasks).description("clean whole house")
                .basePrice(120000L).build();
        Expert expert = Expert.builder().firstName("ali").lastName("alizadeh").email("ali@gmail.com")
                .password("Aa@123456").userRole(UserRole.EXPERT).username("ali@gmail.com")
                .tasks(tasks).subTasks(subTasks).build();
        BDDMockito.given(expertRepository.save(expert)).willReturn(expert);
//        final Expert result = expertService.removeSubServiceFromExpert(expert, subTasks);
//        assertThat(result).isNotNull();
    }

}