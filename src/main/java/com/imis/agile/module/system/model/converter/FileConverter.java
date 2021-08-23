package com.imis.agile.module.system.model.converter;

import com.imis.agile.module.api.model.vo.FileVO;
import com.imis.agile.module.system.model.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * <p>
 * 文件存放 Converter 转换器
 * </p>
 *
 * @author XinLau
 * @since 2020-03-27
 */
@Mapper
public interface FileConverter {

    /**
     * 文件存放 转换器实例
     */
    FileConverter INSTANCE = Mappers.getMapper(FileConverter.class);

    /**
     * 文件存放 PO转VO
     *
     * @param entity
     * @return SysFileVO
     */
    @Mappings({})
    FileVO getReturnValue(File entity);

    /**
     * 文件存放 PO转VO
     *
     * @param entity
     * @return SysFileVO
     */
    @Mappings({})
    List<FileVO> getReturnValue(List<File> entity);

}
