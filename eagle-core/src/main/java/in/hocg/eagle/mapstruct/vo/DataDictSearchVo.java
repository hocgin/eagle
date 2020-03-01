package in.hocg.eagle.mapstruct.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.jackson.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hocgin on 2020/3/1.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class DataDictSearchVo implements Serializable {
    
    private Long id;
    private String title;
    private String code;
    private String remark;
    private Integer enabled;
    @Named(idFor = "enabled", type = NamedType.DataDict)
    private String enabledName;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdatedAt;
    
    private List<DataDictItemVo> items;
}
