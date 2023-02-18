package com.example.springboot.services;

import com.example.springboot.dto.expert.ExpertSet;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.exeption.TasksException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ExpertServiceimplTest {

    @Autowired
    private ExpertServiceimpl expertService;

    //section register
    @Test
    void saveExpert() throws ExpertException, SubTasksException, TasksException {
        Tasks tasks = Tasks.builder().name("house").build();
        SubTasks subTasks = SubTasks.builder().name("cleaning house").task(tasks).description("clean whole house")
                .basePrice(120000L).build();
        ExpertSet expert = ExpertSet.builder().firstName("ali").lastName("alizadeh").email("ali@gmail.com")
                .password("Aa@123456").username("ali@gmail.com")
                .build();
        Expert result = expertService.saveExpert(expert);
        assertNotNull(result);
    }

    //section confirm Expert
    @Test
    void confirmExpert() throws ExpertException {
        Expert expert = Expert.builder().firstName("ali").lastName("alizadeh").email("ali@gmail.com")
                .password("Aa@123456").userRole(UserRole.EXPERT).username("ali@gmail.com").status(false)
                .build();
        expertService.confirmExpert(expert);
    }

    //section find by user pass
//    @Test
//    void findByUsernameAndPassword() throws ExpertException {
//        Tasks tasks = Tasks.builder().name("house").build();
//        SubTasks subTasks = SubTasks.builder().name("cleaning house").task(tasks).description("clean whole house")
//                .basePrice(120000L).build();
//        Expert expert = Expert.builder().firstName("ali").lastName("alizadeh").email("ali@gmail.com")
//                .password("Aa@123456").userRole(UserRole.EXPERT).username("ali@gmail.com")
//                .tasks(tasks).subTasks(subTasks).build();
//        Expert result = expertService.findByUsernameAndPassword(expert);
//        assertThat(result).isNotNull();
//    }

    //section change password
//    @Test
//    void changePassword() throws ExpertException {
//        Expert expert = Expert.builder().username("ebi").password("Aa@123456").build();
//        final Expert newExpert = expertService.changePassword(expert, "Aa123456$");
//        assertThat(newExpert.getPassword()).isEqualTo("Aa123456$");
//    }

//    @Test
//    void removeSubServiceFromExpert() {
//        Tasks tasks = Tasks.builder().name("house").build();
//        SubTasks subTasks = SubTasks.builder().name("cleaning house").task(tasks).description("clean whole house")
//                .basePrice(120000L).build();
//        Expert expert = Expert.builder().firstName("ali").lastName("alizadeh").email("ali@gmail.com")
//                .password("Aa@123456").userRole(UserRole.EXPERT).username("ali@gmail.com")
//                .tasks(tasks).subTasks(subTasks).build();
//        final Expert result = expertService.removeSubServiceFromExpert(expert, subTasks);
//        assertThat(result).isNotNull();
//    }
//
//    @Test
//    void requestForNewJob() {
//        Tasks tasks = Tasks.builder().name("house").build();
//        SubTasks subTasks = SubTasks.builder().name("cleaning house").task(tasks).description("clean whole house")
//                .basePrice(120000L).build();
//        Expert expert = Expert.builder().firstName("ali").lastName("alizadeh").email("ali@gmail.com")
//                .password("Aa@123456").userRole(UserRole.EXPERT).username("ali@gmail.com")
//                .tasks(tasks).subTasks(subTasks).build();
//        final Expert result = expertService.removeSubServiceFromExpert(expert, subTasks);
//        assertThat(result).isNotNull();
//    }
//
}