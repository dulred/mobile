
package com.example.ggb.service;


import com.example.ggb.controller.vo.MallShoppingCartItemVO;
import com.example.ggb.entity.MallUser;
import com.example.ggb.entity.MallUserAddress;

import java.util.List;

public interface MallOrderService {


    String saveOrder(MallUser loginMallUser, MallUserAddress address, List<MallShoppingCartItemVO> itemsForSave);


}
