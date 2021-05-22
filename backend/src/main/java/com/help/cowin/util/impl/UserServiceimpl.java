package com.help.cowin.util.impl;

import java.util.Optional;

import com.help.cowin.pojos.UserEntity;
import com.help.cowin.pojos.UserEntityUV;
import com.help.cowin.repo.CowinDbUserRepo;
import com.help.cowin.repo.UserEntityUVRepo;
import com.help.cowin.util.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceimpl implements UserService{

    @Autowired
    private CowinDbUserRepo cowinDbUserRepo;

    @Autowired
    private UserEntityUVRepo userEntityUVRepo;

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        var userExistsByMail = cowinDbUserRepo.findByEmail(userEntity.getEmail());
        if(!userExistsByMail.isEmpty()){
            return null;
        }
        return cowinDbUserRepo.save(userEntity);
    }

    @Override
    public void deleteUser(UserEntity userEntity) {
        cowinDbUserRepo.delete(userEntity);
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        Optional<UserEntity> user = cowinDbUserRepo.findByEmail(email);
        if(user.isPresent())
            return user.get();
        return null;
    }

    @Override
    public UserEntity findUserById(String id) {
        Optional<UserEntity> user = cowinDbUserRepo.findById(id);
        if(user.isPresent())
            return user.get();
        return null;
    }

    @Override
    public UserEntityUV saveUVUser(UserEntityUV userEntity) {
        Optional<UserEntityUV> userEntityUV = userEntityUVRepo.findByEmail(userEntity.getEmail());
        if(userEntityUV.isPresent())
            return null;
        return userEntityUVRepo.save(userEntity);
    }

    @Override
    public UserEntityUV updateUVUser(UserEntityUV userEntityUV) {
        return userEntityUVRepo.save(userEntityUV);
    }

    @Override
    public UserEntityUV findUVUserById(String id) {
        Optional<UserEntityUV> userUV = userEntityUVRepo.findById(id);
        if(userUV.isPresent())
            return userUV.get();
        return null;
    }

    @Override
    public UserEntityUV findUVUserByMail(String mail) {
        Optional<UserEntityUV> userExists = userEntityUVRepo.findByEmail(mail);
        if(userExists.isPresent())
            return userExists.get();
        return null;
    }

    @Override
    public UserEntityUV deleteUVUserByMail(String mail) {
        Optional<UserEntityUV> userExists = userEntityUVRepo.findByEmail(mail);
        if(userExists.isPresent())
            userEntityUVRepo.delete(userExists.get());
        return null;
    }


}
