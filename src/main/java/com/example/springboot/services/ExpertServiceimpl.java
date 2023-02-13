package com.example.springboot.services;

import com.example.springboot.dto.ReguestJob;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.exeption.TasksException;
import com.example.springboot.repository.ExpertRepository;
import com.example.springboot.validation.Validation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class ExpertServiceimpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final TasksServiceimpl tasksService;
    private final SubTasksimpl subTasksService;

    public ExpertServiceimpl(ExpertRepository expertRepository, TasksServiceimpl tasksService, SubTasksimpl subTasksService) {
        this.expertRepository = expertRepository;
        this.tasksService = tasksService;
        this.subTasksService = subTasksService;
    }

    //section register
    @Override
    public Expert saveExpert(Expert account) throws ExpertException, TasksException, SubTasksException {
        final String password = account.getPassword();
        final String email = account.getEmail();
        if (account.getFirstName() == null || account.getPassword() == null || !Validation.validString(account.getFirstName()) || !Validation.validString(account.getLastName()))
            throw new ExpertException("wrong firstname or lastname !!");
        if (account.getPassword() == null || !Validation.validPassword(password))
            throw new ExpertException("password should have at least a capital Letter and a minimal Letter and 8 character");
        if (account.getEmail() == null || !Validation.validateEmail(email))
            throw new ExpertException("Email not valid");
        if (account.getTasks().get(0).getName() == null)
            throw new ExpertException("You didn't set task");
        if (account.getSubTasks().get(0).getName() == null)
            throw new ExpertException("You didn't set subTask");
        final String taskName = account.getTasks().get(0).getName();
        final Tasks findTask = tasksService.findByName(taskName);
        if (findTask.getName() == null)
            throw new ExpertException("This task dose not exist please tell admin add it first");
        final String subTaskName = account.getSubTasks().get(0).getName();
        final SubTasks findSub = subTasksService.findByName(subTaskName);
        if (findSub.getName() == null)
            throw new ExpertException("This Subtask dose not exist please tell admin add it first");
        account.setInventory(0);
        account.setStatus(false);
        account.setDate(LocalDate.now());
        //todo add this
        account.setSubTasks(Collections.singletonList(findSub));
        account.setTasks(Collections.singletonList(findTask));
        try {
            return expertRepository.save(account);
        } catch (Exception e) {
            throw new ExpertException("Failed to save expert ! ");
        }
    }

    //section login
    @Override
    public Expert findByUsernameAndPassword(Expert e) throws ExpertException {
        final Expert expert = expertRepository.findByUsernameAndPassword(e.getUsername(), e.getPassword());
        if (expert == null)
            throw new ExpertException("Expert not found");
        if (!expert.getStatus())
            throw new ExpertException("this expert is deactivate first admin should confirm");
        System.out.println(expert.getStatus());
        return expert;
    }

    //section confirm
    @Override
    public Expert confirmExpert(Expert expert) throws ExpertException {
        final Expert byUsername = expertRepository.findByUsername(expert.getUsername());
        if (byUsername == null)
            throw new ExpertException("dear admin please give expert username");
        byUsername.setStatus(true);
        return expertRepository.save(byUsername);
    }

    //section reguest job
    @Override
    public Expert requestForNewJob(ReguestJob job) throws ExpertException, TasksException, SubTasksException {
        if (job.getTaskName() == null)
            throw new ExpertException("you didn't set task");
        if (job.getSubTaskName() == null)
            throw new ExpertException("you didn't set subtask");
        if (job.getUsername() == null || job.getPassword()== null)
            throw new ExpertException("you didn't set username or password");
        final Expert byUserPass = expertRepository.findByUsernameAndPassword(job.getUsername(), job.getPassword());
        if (byUserPass == null)
            throw new ExpertException("wrong username or password");
        final Tasks task = tasksService.findByName(job.getTaskName());
        final SubTasks subTask = subTasksService.findByName(job.getSubTaskName());
        final List<Tasks> tasks = byUserPass.getTasks();
        final List<SubTasks> subTasks = byUserPass.getSubTasks();
        tasks.add(task);
        subTasks.add(subTask);
        return expertRepository.save(byUserPass);
    }

    //    section password
    @Override
    public Expert changePassword(Expert e, String newPassword) throws ExpertException {
        if (newPassword == null)
            throw new ExpertException("New password is Null");
        if (!Validation.validPassword(newPassword))
            throw new ExpertException("password should have at least a capital Letter and a minimal Letter and 8 character");
        final Expert byUsername = findByUsernameAndPassword(e);
        byUsername.setPassword(newPassword);
        return expertRepository.save(byUsername);
    }

    //section removeSubService
//    @Override
//    public void removeSubServiceFromExpert(Expert expert, SubTasks sub) {
//        Expert newExpert = Expert.builder().firstName(expert.getFirstName()).lastName(expert.getLastName())
//                .email(expert.getEmail()).password(expert.getPassword()).userRole(UserRole.EXPERT)
//                .status(true).tasks(expert.getTasks()).inventory(expert.getInventory())
//                .subTasks(sub).build();
//        expertRepository.delete(newExpert);
//    }

    //section showUnconfirmed
    public List<Expert> showUnconfirmExpert() throws ExpertException {
        final List<Expert> allByStatus = expertRepository.findAllByStatus(false);
        if (allByStatus.size() < 1)
            throw new ExpertException("there is no expert to confirm");
        return allByStatus;
    }
}
