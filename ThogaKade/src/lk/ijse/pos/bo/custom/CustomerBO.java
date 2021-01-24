package lk.ijse.pos.bo.custom;

import lk.ijse.pos.dto.CustomerDTO;
import java.util.List;

public interface CustomerBO {
    public boolean add(CustomerDTO dto)throws Exception;
    public boolean update(CustomerDTO dto)throws Exception;
    public boolean delete(String id)throws Exception;
    public List<CustomerDTO> findAll() throws Exception;

}
