package pe.edu.upc.aaw.lawdingo_g4.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import pe.edu.upc.aaw.lawdingo_g4.dtos.CategoryDTO;
import pe.edu.upc.aaw.lawdingo_g4.dtos.ConsultationByCategoryDTO;
import pe.edu.upc.aaw.lawdingo_g4.entities.Category;
import pe.edu.upc.aaw.lawdingo_g4.serviceinterfaces.ICategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ICategoryService aS;

    @PostMapping
    public void insert(@RequestBody CategoryDTO dto) {
        ModelMapper m = new ModelMapper();
        Category a = m.map(dto, Category.class);
        aS.insert(a);
    }
    @PutMapping()
    public void goUpdate(@RequestBody CategoryDTO dto){
        ModelMapper m=new ModelMapper();
        Category u=m.map(dto,Category.class);
        aS.insert(u);
    }


    @GetMapping
    public List<CategoryDTO> list() {
        return aS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x, CategoryDTO.class);

        }).collect(Collectors.toList());
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id){
        aS.delete(id);
    }

    @GetMapping("/cantidadconsultasporcategoria")
    public List<ConsultationByCategoryDTO> cantidadconsultasporcategoria(){
        List<String[]> lista = aS.querieCategory();
        List<ConsultationByCategoryDTO> listDTO = new ArrayList<>();
        for(String[] data : lista){
            ConsultationByCategoryDTO dto = new ConsultationByCategoryDTO();
            dto.setNameCategory(data[0]);
            dto.setCountConsultation(Integer.parseInt(data[1]));
            listDTO.add(dto);

        }
        return listDTO;
    }

    @GetMapping("/{id}")
    public CategoryDTO ListId(@PathVariable("id")Integer id){
        ModelMapper m = new ModelMapper();
        CategoryDTO dto = m.map(aS.ListId(id), CategoryDTO.class);
        return dto;
    }
}
