package com.ruoyi.ims.mapper;

import java.util.List;
import java.math.BigDecimal;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.ims.domain.ImsInventory;

/**
 * 库存(现存量)Mapper接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface ImsInventoryMapper 
{
    /**
     * 查询库存(现存量)
     * 
     * @param id 库存(现存量)主键
     * @return 库存(现存量)
     */
    public ImsInventory selectImsInventoryById(Long id);

    /**
     * 查询库存(现存量)列表
     * 
     * @param imsInventory 库存(现存量)
     * @return 库存(现存量)集合
     */
    public List<ImsInventory> selectImsInventoryList(ImsInventory imsInventory);

    /**
     * 查询指定产品在指定仓库的可用库存
     *
     * @param enterpriseId 企业ID
     * @param warehouseId 仓库ID
     * @param productId 产品ID
     * @return 可用库存
     */
    public BigDecimal selectAvailableQty(@Param("enterpriseId") Long enterpriseId,
                                          @Param("warehouseId") Long warehouseId,
                                          @Param("productId") Long productId);

    public List<ImsInventory> selectInventoryRowsForUpdate(@Param("enterpriseId") Long enterpriseId,
                                                           @Param("warehouseId") Long warehouseId,
                                                           @Param("productId") Long productId);

    public int updateInventoryQtyById(@Param("id") Long id,
                                      @Param("changeQty") BigDecimal changeQty,
                                      @Param("updateBy") String updateBy);

    /**
     * 新增库存(现存量)
     * 
     * @param imsInventory 库存(现存量)
     * @return 结果
     */
    public int insertImsInventory(ImsInventory imsInventory);

    /**
     * 修改库存(现存量)
     * 
     * @param imsInventory 库存(现存量)
     * @return 结果
     */
    public int updateImsInventory(ImsInventory imsInventory);

    /**
     * 删除库存(现存量)
     * 
     * @param id 库存(现存量)主键
     * @return 结果
     */
    public int deleteImsInventoryById(Long id);

    /**
     * 批量删除库存(现存量)
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsInventoryByIds(Long[] ids);
}
