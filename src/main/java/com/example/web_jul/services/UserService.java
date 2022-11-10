package com.example.web_jul.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.web_jul.entities.User;
import com.example.web_jul.repositories.user.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created on 09.07.2022. by Andrija inside package com.example.web_jul.services.
 */
public class UserService {
    @Inject
    UserRepository userRepository;

    public String[] login(String email, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);

        User user = this.userRepository.findUserByEmail(email);
        if (user == null || !user.getHashedPassword().equals(hashedPassword)) {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000); // One day

        Algorithm algorithm = Algorithm.HMAC256("secret");

        // JWT-om mozete bezbedono poslati informacije na FE
        // Tako sto sve sto zelite da posaljete zapakujete u claims mapu
        String[] pairResult = new String[2];
        pairResult[0] =  JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(email)
                .withClaim("first_name",user.getFirstName())
                .withClaim("last_name",user.getLastName())
                .withClaim("type", user.getType())
                .withClaim("active",user.getActive())
                .sign(algorithm);
        pairResult[1] = user.getType();
        return pairResult;
    }


    public List<User> getAllByPage(Integer page){
        return userRepository.getUsers(page);
    }
    public User create(User user)
    {
        String hashedPassword = DigestUtils.sha256Hex(user.getHashedPassword());
        user.setHashedPassword(hashedPassword);

        return userRepository.insertUser(user);

    }
    public User findUserById(Integer id){
        return userRepository.findUserById(id);
    }

    public Boolean updateUser(User user){
            return userRepository.updateUser(user);
    }

    public boolean deleteUser(Integer id)
    {
        return userRepository.deleteUser(id);

    }

    public boolean isAuthorized(String token,String requestedUserType){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();
        String type = jwt.getClaim("type").asString();
        Boolean active = jwt.getClaim("active").asBoolean();

        User user = this.userRepository.findUserByEmail(email);

        if (user == null){
            return false;
        }
        else if(active.equals(true)){
            if(requestedUserType.equals("admin") && type.equals(requestedUserType)){
                return true;
            }
            else if(requestedUserType.equals("content_creator")){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }

    }

    public User getUserFromToken(String token){

        if(token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
        }

        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();

        return this.userRepository.findUserByEmail(email);

//        return user.getUserId();
    }

//    public boolean authorizeRequestedType(String token, String requestedUserType) {
//        boolean authorized;
//        Algorithm algorithm = Algorithm.HMAC256("secret");
//        JWTVerifier verifier = JWT.require(algorithm).build();
//        token = token.replace("Bearer ", "");
//        DecodedJWT jwt = verifier.verify(token);
//
//        String type = jwt.getClaim("type").asString();
//        authorized = type.equals(requestedUserType);
//
//        return authorized;
//    }
}
