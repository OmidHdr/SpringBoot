package com.example.springboot.services;

import com.example.springboot.dto.ReguestJob;
import com.example.springboot.dto.expert.ExpertSet;
import com.example.springboot.dto.expert.dtoExpert;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.exeption.TasksException;
import com.example.springboot.mapper.ProductMapper;
import com.example.springboot.repository.ExpertRepository;
import com.example.springboot.utills.Utills;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@RequiredArgsConstructor
@Service
public class ExpertServiceimpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final SubTasksimpl subTasksService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    //section register
    @Override
    public Expert saveExpert(ExpertSet account) throws ExpertException, SubTasksException {
        if (account.getFirstName() == null || account.getPassword() == null || !Utills.validString(account.getFirstName()) || !Utills.validString(account.getLastName()))
            throw new ExpertException("wrong firstname or lastname !!");
        if (account.getPassword() == null || !Utills.validPassword(account.getPassword()))
            throw new ExpertException("password should have at least a capital Letter and a minimal Letter and 8 character");
        if (account.getEmail() == null || !Utills.validateEmail(account.getEmail()))
            throw new ExpertException("Email not valid");
        if (account.getSubTaskName() == null)
            throw new ExpertException("You didn't set subTask");
        SubTasks subtask = subTasksService.findByName(account.getSubTaskName());
        if (subtask == null)
            throw new ExpertException("This Subtask dose not exist please tell admin add it first");

        String link = UUID.randomUUID().toString();

        Expert expert = ProductMapper.INSTANCE.expertsetToExpert(account);
        expert.setPassword(passwordEncoder.encode(account.getPassword()));
        expert.setSubTasks(Collections.singletonList(subtask));
        expert.setToken(link);
        expert.setUserRole(UserRole.ROLE_EXPERT);
        expert.setDate(LocalDate.now());
        expert.setInventory(0L);
        expert.setScore(0);
        expert.setStatus(false);
        
        emailSender.sendEmail(account.getEmail(),"Confirm your Account",
                "Click on this link "+"http://localhost:8080/expert/verify?token="+link+" to confirm");
        try {
            return expertRepository.save(expert);
        } catch (Exception e) {
            throw new ExpertException("Failed to save expert ! ");
        }
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

    //section password
    @Override
    public void changePassword(String password,Expert expert) throws ExpertException {
        if(password == null ||!Utills.validPassword(password))
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

    //section verify
    @Override
    public Expert verify(String token) throws ExpertException {
        Expert expert = expertRepository.findByToken(token)
                .orElseThrow(() -> new ExpertException("wrong token"));
        expert.setStatus(true);
        return expertRepository.save(expert);
    }

    //section save
    public Expert save(Expert expert) {
        return expertRepository.save(expert);

    }



}
