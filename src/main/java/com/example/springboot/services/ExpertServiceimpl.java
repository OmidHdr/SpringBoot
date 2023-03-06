package com.example.springboot.services;

import com.example.springboot.dto.ReguestJob;
import com.example.springboot.dto.expert.ExpertSet;
import com.example.springboot.dto.expert.dtoExpert;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.exeption.TasksException;
import com.example.springboot.mapper.ProductMapper;
import com.example.springboot.repository.ExpertRepository;
import com.example.springboot.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ExpertServiceimpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final TasksServiceimpl tasksService;
    private final SubTasksimpl subTasksService;
    private final BCryptPasswordEncoder passwordEncoder;

    //section register
    @Override
    public Expert saveExpert(ExpertSet account) throws ExpertException, TasksException, SubTasksException {
        if (account.getFirstName() == null || account.getPassword() == null || !Validation.validString(account.getFirstName()) || !Validation.validString(account.getLastName()))
            throw new ExpertException("wrong firstname or lastname !!");
        if (account.getPassword() == null || !Validation.validPassword(account.getPassword()))
            throw new ExpertException("password should have at least a capital Letter and a minimal Letter and 8 character");
        if (account.getEmail() == null || !Validation.validateEmail(account.getEmail()))
            throw new ExpertException("Email not valid");
        if (account.getSubTaskName() == null)
            throw new ExpertException("You didn't set subTask");
        SubTasks subtask = subTasksService.findByName(account.getSubTaskName());
        if (subtask == null)
            throw new ExpertException("This Subtask dose not exist please tell admin add it first");
        Expert expert = Expert.builder().firstName(account.getFirstName()).lastName(account.getLastName())
                .email(account.getEmail()).date(LocalDate.now()).username(account.getUsername())
                .password(passwordEncoder.encode(account.getPassword())).userRole(UserRole.ROLE_EXPERT).inventory(0L)
                .status(false).subTasks(Collections.singletonList(subtask)).score(10).build();
        try {
            return expertRepository.save(expert);
        } catch (Exception e) {
            throw new ExpertException("Failed to save expert ! ");
        }
    }

    //section login
    @Override
    public Expert findByUsernameAndPassword(String username, String password) throws ExpertException {
        final Expert expert = expertRepository.findByUsernameAndPassword(username, password);
        if (expert == null)
            throw new ExpertException("wrong username or password");
        if (!expert.getStatus())
            throw new ExpertException("this expert is deactivate first admin should confirm");
        List<SubTasks> listSub = new ArrayList<>();
        for (int i = 0; i < expert.getSubTasks().size(); i++) {
            Tasks task = Tasks.builder().id(expert.getSubTasks().get(i).getTask().getId())
                    .name(expert.getSubTasks().get(i).getTask().getName()).build();
            SubTasks subTasks = SubTasks.builder().id(expert.getSubTasks().get(i).getId())
                    .name(expert.getSubTasks().get(i).getName()).task(task)
                    .basePrice(expert.getSubTasks().get(i).getBasePrice())
                    .description(expert.getSubTasks().get(i).getDescription()).build();
            listSub.add(subTasks);
        }
        Expert result = Expert.builder().id(expert.getId()).firstName(expert.getFirstName()).lastName(expert.getLastName())
                .email(expert.getEmail()).inventory(expert.getInventory()).subTasks(listSub)
                .date(expert.getDate()).username(expert.getUsername()).password(expert.getPassword())
                .userRole(expert.getUserRole()).status(expert.getStatus()).build();
        return result;
    }

    //section confirm
    @Override
    public void confirmExpert(String username) throws ExpertException {
        final Optional<Expert> byId = expertRepository.findByUsername(username);
        if (byId.isEmpty())
            throw new ExpertException("the id is wrong");
        Expert expert = byId.get();
        expert.setStatus(true);
        expert.setScore(0);
        expertRepository.save(expert);
    }

    //section reguest job
    @Override
    public Expert requestForNewJob(ReguestJob job,Expert byUserPass) throws ExpertException, SubTasksException {
        if (job.getTaskName() == null)
            throw new ExpertException("you didn't set task");
        if (job.getSubTaskName() == null)
            throw new ExpertException("you didn't set subtask");
        if (!byUserPass.getStatus())
            throw new ExpertException("this byUserPass is deactivate please tell admin to active it first");
        final SubTasks subTask = subTasksService.findByName(job.getSubTaskName());
        final List<SubTasks> subTasks = byUserPass.getSubTasks();
        subTasks.forEach(subTasks1 -> {
            if (subTasks1.getName().equals(job.getSubTaskName()))
                try {
                    throw new ExpertException("Expert Already have this job");
                } catch (ExpertException e) {
                    throw new RuntimeException(e);
                }
        });
        subTasks.add(subTask);
        byUserPass.setSubTasks(subTasks);
        byUserPass.setStatus(false);
        return expertRepository.save(byUserPass);
    }

    //    section password
    @Override
    public void changePassword(String password,Expert expert) throws ExpertException {
        if(password == null ||!Validation.validPassword(password))
            throw new ExpertException("password should have at least a Capital letter and a number and a character");
        expert.setPassword(passwordEncoder.encode(password));
        expertRepository.save(expert);
    }

    //section showUnconfirmed
    @Override
    public List<dtoExpert> showUnconfirmExpert() throws ExpertException {
        final List<Expert> allByStatus = expertRepository.findAllByStatus(false);
        if (allByStatus.size() < 1)
            throw new ExpertException("there is no expert to confirm");
        List<dtoExpert> result = new ArrayList<>();
        for (int i = 0; i < allByStatus.size(); i++) {
            result.add(ProductMapper.INSTANCE.expertToDto(allByStatus.get(i)));
        }
//        allByStatus.forEach(expert -> result.add(ProductMapper.INSTANCE.expertToDto(expert)));
        return result;
    }

    //section removeSubService
    @Override
    public void removeExpertFromSubtask(String subTaskName , String usernameExpert) throws ExpertException, SubTasksException, TasksException {
        SubTasks subtask = subTasksService.findByName(subTaskName);
        if (subtask == null)
            throw new ExpertException("this Subtask dose not exist");
        final Optional<Expert> byUsername = expertRepository.findByUsername(usernameExpert);
        if (byUsername.isEmpty())
            throw new ExpertException("wrong username");
        Expert expert = byUsername.get();
        List<SubTasks> subTasks = expert.getSubTasks();
        for (int i = 0; i < subTasks.size(); i++) {
            if (subTasks.get(i).getName().equals(subtask.getName())) {
                subTasks.remove(i);
            }
        }
        expert.setSubTasks(subTasks);
        expertRepository.save(expert);
    }

    //find by Id
    @Override
    public Expert findById(Long id) throws ExpertException {
        final Optional<Expert> byId = expertRepository.findById(id);
        if (byId.isEmpty())
            throw new ExpertException("can't find expert");
        return byId.get();
    }

    @Override
    public List<Expert> findBySubTask(SubTasks subtask) throws ExpertException {
        final List<Expert> bySubTasks = expertRepository.findBySubTasks(subtask);
        if (bySubTasks.size() < 1)
            throw new ExpertException("no Expert found");
        return bySubTasks;
    }

    public Expert save(Expert expert) {
        return expertRepository.save(expert);
    }

}
