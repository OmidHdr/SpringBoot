package com.example.springboot.services;

import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.TasksException;
import com.example.springboot.repository.TasksRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TasksServiceimplTest2 {

    @Autowired
    private TasksServiceimpl tasksService;

    @Test
    void saveTask() throws TasksException {
        Tasks tasks = Tasks.builder().name("house").build();
        final Tasks result = tasksService.saveTask(tasks);
        assertThat(result).isNotNull();
    }
}