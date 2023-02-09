package com.example.springboot.services;

import com.example.springboot.entity.Tasks;
import com.example.springboot.repository.TasksRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TasksServiceimplTest {

    @Mock
    private TasksRepository tasksRepository;

    @InjectMocks
    private TasksServiceimpl tasksService;

    @Test
    void saveTask() {
        Tasks tasks = Tasks.builder().name("house").build();
        BDDMockito.given(tasksRepository.save(tasks)).willReturn(tasks);
        final Tasks result = tasksService.saveTask(tasks);
        assertThat(result).isNotNull();
    }
}