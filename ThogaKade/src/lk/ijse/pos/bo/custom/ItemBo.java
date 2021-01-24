package lk.ijse.pos.bo.custom;

import lk.ijse.pos.dto.ItemDTO;
import java.util.List;

public interface ItemBo {
    public boolean add(ItemDTO dto)throws Exception;
    public boolean update(ItemDTO dto)throws Exception;
    public boolean delete(String id)throws Exception;
    public List<ItemDTO> findAll() throws Exception;
}
