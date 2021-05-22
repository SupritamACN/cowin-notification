package com.help.cowin.util;

import com.help.cowin.pojos.UserEntity;
import com.help.cowin.pojos.UserEntityUV;


public interface UserService {

    public UserEntity saveUser(UserEntity userEntity);
    public void deleteUser(UserEntity userEntity);
    public UserEntity updateUser(UserEntity userEntity);
    public UserEntity findUserByEmail(String email);
    public UserEntity findUserById(String id);

    public UserEntityUV saveUVUser(UserEntityUV userEntity);
    public UserEntityUV updateUVUser(UserEntityUV userEntity);
    public UserEntityUV findUVUserById(String id);
    public UserEntityUV findUVUserByMail(String mail);
    public UserEntityUV deleteUVUserByMail(String mail);

   

}
