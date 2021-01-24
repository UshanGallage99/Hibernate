package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.ItemBo;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.DAOType;
import lk.ijse.pos.dao.custom.impl.ItemDAOImpl;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBo {
    ItemDAOImpl itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);

    @Override
    public boolean add(ItemDTO dto) throws Exception {
        return itemDAO.add(new Item(
                dto.getCode(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getQtyOnHand()
        ));
    }

    @Override
    public boolean update(ItemDTO dto) throws Exception {
        return itemDAO.update(new Item(
                dto.getCode(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getQtyOnHand()
        ));
    }

    @Override
    public boolean delete(String id) throws Exception {
        return itemDAO.delete(id);
    }

    @Override
    public List<ItemDTO> findAll() throws Exception {
        List<Item> all = itemDAO.findAll();
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : all) {
            itemDTOS.add(new ItemDTO(
                    item.getCode(),
                    item.getDescription(),
                    item.getPrice(),
                    item.getQtyOnHand()
            ));
        }
        return itemDTOS;
    }
}
