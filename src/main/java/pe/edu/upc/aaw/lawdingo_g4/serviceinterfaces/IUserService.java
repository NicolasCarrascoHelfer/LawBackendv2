package pe.edu.upc.aaw.lawdingo_g4.serviceinterfaces;

import pe.edu.upc.aaw.lawdingo_g4.entities.Users;

import java.util.List;

public interface IUserService {
    public Integer insert(Users users);

    List<Users> list();

    public List <Users> getUsersWhoseNameStartsWith(String startsWith);
    public List <Users> list(String name);
    public void  delete (int idUser);
    public Users ListId(int id);

    //agregado
    public Users listUsername(String name);


}
